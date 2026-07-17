package com.sumino.toastly.ui

import com.sumino.toastly.Toastly
import com.sumino.toastly.controller.DefaultToastController
import com.sumino.toastly.model.ToastPosition
import com.sumino.toastly.model.toMs
import com.sumino.toastly.state.ToastEntry
import com.sumino.toastly.state.ToastState
import com.sumino.toastly.state.rememberToastState

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Sumino Apps — Toastly.
 *
 * Root-level overlay that renders whatever is shown through a [DefaultToastController].
 */

// Maximum number of toasts drawn in the stack at once.
private const val MAX_VISIBLE = 3

// Per-depth stack styling, ordered back -> front (index 0 = furthest back, last = front).
// The furthest-back toasts are smaller, more transparent, and peek out behind the front one.
private val STACK_SCALE = listOf(0.90f, 0.95f, 1.00f)
private val STACK_ALPHA = listOf(0.50f, 0.75f, 1.00f)
private val STACK_PEEK = listOf(14.dp, 7.dp, 0.dp)

// Fraction of the toast width a horizontal swipe must cross to dismiss.
private const val SWIPE_DISMISS_FRACTION = 0.4f

/**
 * Place once at the root of your UI, above the nav host, to render toasts.
 *
 * The overlay is purely state-driven — there is no polling loop. It binds to [controller],
 * registers itself as the active observer while its host is resumed, and drains newly enqueued
 * toasts into an on-screen [ToastState]. Each toast auto-dismisses on its own timer and can be
 * swiped away horizontally.
 *
 * Only one observer shows toasts at a time: whichever one is currently resumed. This prevents a
 * multi-Activity app from showing the same toast twice.
 *
 * @param modifier applied to the overlay container.
 * @param controller source of toasts to render; defaults to the global [Toastly] controller.
 * @param observerId stable id used for active-observer tracking; generated if not supplied.
 * @param state on-screen stack holder; hoist your own to control [ToastState.maxStack].
 */
@Composable
fun ToastObserver(
    modifier: Modifier = Modifier,
    controller: DefaultToastController = Toastly.controller,
    observerId: String? = null,
    state: ToastState = rememberToastState(maxStack = MAX_VISIBLE),
) {
    val resolvedId = remember(observerId) { observerId ?: UUID.randomUUID().toString() }
    val lifecycleOwner = LocalLifecycleOwner.current

    // Mark this observer active only while its host is in the foreground.
    DisposableEffect(lifecycleOwner, controller, resolvedId) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> controller.setActive(resolvedId)
                Lifecycle.Event.ON_PAUSE -> controller.setInactive(resolvedId)
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            controller.setInactive(resolvedId)
        }
    }

    // Drain the controller's queue into the visible stack whenever it signals new work.
    LaunchedEffect(controller, resolvedId) {
        controller.signal.collect {
            if (!controller.isActive(resolvedId)) return@collect
            while (true) {
                val config = controller.poll() ?: break
                state.push(config)
            }
        }
    }

    // Respond to programmatic dismissAll().
    LaunchedEffect(controller) {
        controller.dismissAllSignal.collect { state.dismissAll() }
    }

    ToastStack(state = state, modifier = modifier)
}

@Composable
internal fun ToastStack(
    state: ToastState,
    modifier: Modifier = Modifier,
) {
    val stack = state.stack
    if (stack.isEmpty()) return

    // The newest toast decides where the whole stack anchors.
    val position = stack.last().config.position
    val alignment =
        if (position == ToastPosition.BOTTOM) Alignment.BottomCenter else Alignment.TopCenter

    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 16.dp)
            .padding(
                top = if (position == ToastPosition.TOP) 16.dp else 0.dp,
                bottom = if (position == ToastPosition.BOTTOM) 16.dp else 0.dp,
            ),
        contentAlignment = alignment,
    ) {
        stack.forEachIndexed { index, entry ->
            key(entry.id) {
                // depth 0 = front (newest), larger = further back.
                val depth = stack.size - 1 - index
                ToastStackItem(
                    entry = entry,
                    position = position,
                    depth = depth,
                    isFront = depth == 0,
                    onRemoved = state::dismiss,
                )
            }
        }
    }
}

@Composable
private fun ToastStackItem(
    entry: ToastEntry,
    position: ToastPosition,
    depth: Int,
    isFront: Boolean,
    onRemoved: (String) -> Unit,
) {
    // Enter hidden; the reveal (after an optional delay) drives the enter animation.
    val visibleState = remember { MutableTransitionState(false) }
    var hasShown by remember { mutableStateOf(false) }

    // Reveal — honouring showDelayMs so the toast actually appears late, not just dismisses late.
    LaunchedEffect(entry.id) {
        if (entry.config.showDelayMs > 0L) delay(entry.config.showDelayMs)
        visibleState.targetState = true
    }
    LaunchedEffect(visibleState.currentState) {
        if (visibleState.currentState) hasShown = true
    }

    // Auto-dismiss — starts counting once the toast is actually on screen.
    val durationMs = entry.config.duration.toMs()
    LaunchedEffect(entry.id, visibleState.currentState) {
        if (visibleState.currentState && durationMs != null) {
            delay(durationMs)
            visibleState.targetState = false
        }
    }

    // Remove from the backing stack only after the exit animation has fully played out.
    LaunchedEffect(visibleState.isIdle, visibleState.currentState) {
        if (hasShown && visibleState.isIdle && !visibleState.currentState) {
            entry.config.onDismiss?.invoke()
            onRemoved(entry.id)
        }
    }

    val requestDismiss = { visibleState.targetState = false }

    // Animated depth transforms so existing toasts settle back when a new one arrives.
    val tokenIndex = (STACK_SCALE.lastIndex - depth).coerceIn(0, STACK_SCALE.lastIndex)
    val scale by animateFloatAsState(STACK_SCALE[tokenIndex], label = "scale_${entry.id}")
    val layerAlpha by animateFloatAsState(STACK_ALPHA[tokenIndex], label = "alpha_${entry.id}")
    val peekTarget = STACK_PEEK[tokenIndex].let { if (position == ToastPosition.BOTTOM) -it else it }
    val peek by animateDpAsState(
        targetValue = peekTarget,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
        label = "peek_${entry.id}",
    )

    val enter = slideInVertically(
        initialOffsetY = { if (position == ToastPosition.BOTTOM) it else -it },
        animationSpec = tween(300),
    ) + fadeIn(tween(220))
    val exit = slideOutVertically(
        targetOffsetY = { if (position == ToastPosition.BOTTOM) it else -it },
        animationSpec = tween(260),
    ) + fadeOut(tween(180))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = peek)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                alpha = layerAlpha
            },
    ) {
        AnimatedVisibility(
            visibleState = visibleState,
            enter = enter,
            exit = exit,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .swipeToDismiss(enabled = isFront, onDismiss = requestDismiss),
            ) {
                ToastLayout(
                    config = entry.config,
                    onDismiss = requestDismiss,
                )
            }
        }
    }
}

/**
 * Horizontal swipe-to-dismiss. Dragging past [SWIPE_DISMISS_FRACTION] of the width flings the
 * toast off-screen and requests dismissal; a shorter drag springs back. When [enabled] is
 * `false` the gesture is inert and the content stays put — composition shape is unchanged either
 * way, so this is safe to toggle as a toast moves between front and back of the stack.
 */
@Composable
private fun Modifier.swipeToDismiss(
    enabled: Boolean,
    onDismiss: () -> Unit,
): Modifier {
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // Reset the drag if this toast is no longer the swipeable front one.
    LaunchedEffect(enabled) {
        if (!enabled && offsetX.value != 0f) offsetX.animateTo(0f, spring())
    }

    return this
        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
        .pointerInput(enabled) {
            if (!enabled) return@pointerInput
            val threshold = size.width * SWIPE_DISMISS_FRACTION
            val width = size.width.toFloat()
            detectHorizontalDragGestures(
                onDragEnd = {
                    scope.launch {
                        if (abs(offsetX.value) > threshold) {
                            val target = if (offsetX.value > 0) width else -width
                            offsetX.animateTo(target, tween(200))
                            onDismiss()
                        } else {
                            offsetX.animateTo(0f, spring())
                        }
                    }
                },
                onHorizontalDrag = { change, dragAmount ->
                    change.consume()
                    scope.launch { offsetX.snapTo(offsetX.value + dragAmount) }
                },
            )
        }
}

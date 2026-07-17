package com.sumino.toastly.state

import com.sumino.toastly.model.ToastConfig

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.util.UUID

/**
 * Holds the toasts currently on screen for a single [com.sumino.toastly.ui.ToastObserver].
 *
 * The stack is ordered oldest-first: index `0` is the oldest (rendered furthest back), and the
 * last element is the newest (rendered in front). When a push would exceed [maxStack] the oldest
 * entries are dropped so only the most recent [maxStack] remain visible.
 *
 * Create one with [rememberToastState] and hoist it to your screen root. Mutation is internal —
 * toasts enter via the observer draining the controller queue, and leave when their timer,
 * swipe, or close action fires.
 *
 * @property maxStack maximum number of toasts shown at once.
 */
@Stable
class ToastState(val maxStack: Int = DEFAULT_MAX_STACK) {

    // Visible stack — index 0 = oldest (back), last = newest (front).
    internal var stack by mutableStateOf<List<ToastEntry>>(emptyList())
        private set

    /** Appends [config] as a new entry, trimming the oldest beyond [maxStack]. */
    internal fun push(config: ToastConfig) {
        val entry = ToastEntry(config = config)
        stack = (stack + entry).takeLast(maxStack)
    }

    /** Removes the entry with the given [id], if present. */
    internal fun dismiss(id: String) {
        stack = stack.filterNot { it.id == id }
    }

    /** Removes every visible entry at once. */
    internal fun dismissAll() {
        stack = emptyList()
    }

    companion object {
        /** Default number of toasts shown at once. */
        const val DEFAULT_MAX_STACK = 3
    }
}

/**
 * Remembers a [ToastState] that survives recomposition.
 *
 * @param maxStack maximum number of toasts visible at once.
 */
@Composable
fun rememberToastState(maxStack: Int = ToastState.DEFAULT_MAX_STACK): ToastState =
    remember(maxStack) { ToastState(maxStack) }

/**
 * One live toast: a stable [id] used as an animation/list key plus its [config].
 */
internal data class ToastEntry(
    val id: String = UUID.randomUUID().toString(),
    val config: ToastConfig,
)

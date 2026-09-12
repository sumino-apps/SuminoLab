/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.ui.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Clickable modifier without default ripple effect.
 * Useful for custom buttons, containers, and interactive elements.
 */
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) {
        onClick()
    }
}

/**
 * Clickable modifier with built-in debounce protection to prevent multiple rapid taps.
 *
 * @param debounceIntervalMs Minimum time between recognized clicks (defaults to 500ms).
 */
fun Modifier.debounceClickable(
    debounceIntervalMs: Long = 500L,
    onClick: () -> Unit
): Modifier = composed {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    this.clickable {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= debounceIntervalMs) {
            lastClickTime = currentTime
            onClick()
        }
    }
}

/**
 * Adds an animated shimmering gradient effect to simulate skeleton loading state.
 */
fun Modifier.shimmerLoading(
    durationMillis: Int = 1200,
    shimmerColors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslateAnim"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim, y = translateAnim)
    )

    this.background(brush)
}

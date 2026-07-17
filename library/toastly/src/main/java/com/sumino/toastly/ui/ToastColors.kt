package com.sumino.toastly.ui

import com.sumino.toastly.model.ToastType

import androidx.compose.ui.graphics.Color



/**
 * Sumino Apps — Toastly.
 *
 * Semantic accent palette for a [ToastType]. [primary] drives the icon, action button, and
 * accent bar; the remaining values are the light-tint reference colours a style may draw from.
 */
internal data class ToastColors(
    val background: Color,
    val border: Color,
    val text: Color,
    val primary: Color,
)

/** Accent colours for this toast type. */
internal fun ToastType.colors(): ToastColors = when (this) {


    ToastType.SUCCESS -> ToastColors(
        background = Color(0xFFE8F7EF),
        border = Color(0xFF22C55E),
        text = Color(0xFF14532D),
        primary = Color(0xFF22C55E)
    )

    ToastType.ERROR -> ToastColors(
        background = Color(0xFFFEECEC),
        border = Color(0xFFEF4444),
        text = Color(0xFF7F1D1D),
        primary = Color(0xFFEF4444)
    )

    ToastType.INFO -> ToastColors(
        background = Color(0xFFEFF6FF),
        border = Color(0xFF3B82F6),
        text = Color(0xFF1E293B),
        primary = Color(0xFF3B82F6)
    )

    ToastType.WARNING -> ToastColors(
        background = Color(0xFFFFF8E1),
        border = Color(0xFFFACC15),
        text = Color(0xFF78350F),
        primary = Color(0xFFEAB308)
    )


}
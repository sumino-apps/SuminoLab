package com.sumino.designsystem.theme

/**
 * Opacity tokens for state layers and disabled content, following the Material 3
 * state-layer system. These are constants rather than themed values — the same
 * interaction opacities apply regardless of brand, so they are read directly as
 * `Theme.alpha.Disabled` etc.
 */
object Alpha {
    /** Content (text/icon) in a disabled component. */
    const val Disabled = 0.38f

    /** Container/background of a disabled component. */
    const val DisabledContainer = 0.12f

    /** Hover state layer. */
    const val Hover = 0.08f

    /** Focus state layer. */
    const val Focus = 0.10f

    /** Pressed state layer. */
    const val Pressed = 0.10f

    /** Dragged state layer. */
    const val Dragged = 0.16f

    /** Divider / subtle separator lines. */
    const val Divider = 0.12f

    /** Scrim behind modal surfaces (sheets, dialogs, drawers). */
    const val Scrim = 0.32f
}

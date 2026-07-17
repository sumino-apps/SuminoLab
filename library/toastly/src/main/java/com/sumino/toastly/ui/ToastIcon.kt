package com.sumino.toastly.ui

import com.sumino.toastly.R
import com.sumino.toastly.model.ToastType

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * Sumino Apps — Toastly.
 *
 * Leading type icon rendered inside a faint circular chip.
 */

/** Drawable resource for this toast type's icon. */
internal fun ToastType.icon(): Int = when (this) {
    ToastType.INFO -> R.drawable.toastly_info_circle
    ToastType.SUCCESS -> R.drawable.toastly_check_circle
    ToastType.ERROR -> R.drawable.toastly_error_circle
    ToastType.WARNING -> R.drawable.toastly_warning_triangle
}

/**
 * Renders the [type] icon tinted with [tint] on a subtle circular background.
 *
 * The icon is decorative (`contentDescription = null`): the toast container is a live region
 * that already announces the message text to accessibility services, so labelling the icon too
 * would double-announce.
 *
 * @param type toast type selecting the icon.
 * @param tint icon colour, usually the style's accent.
 * @param modifier applied to the icon chip.
 */
@Composable
internal fun ToastIcon(
    type: ToastType,
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(28.dp)
            .background(color = tint.copy(alpha = 0.1f), shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(type.icon()),
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(16.dp),
        )
    }
}

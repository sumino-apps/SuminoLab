package com.sumino.toastly.ui

import com.sumino.toastly.R
import com.sumino.toastly.model.ToastConfig
import com.sumino.toastly.model.ToastStyle
import com.sumino.toastly.model.ToastType

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

/**
 * Sumino Apps — Toastly.
 *
 * Renders a single toast for a given [ToastConfig]. Visuals are theme-aware and adapt to light
 * and dark themes via [MaterialTheme.colorScheme]; only the semantic accent (icon / button /
 * accent bar) is driven by the toast [ToastType].
 */

private val ToastShape = RoundedCornerShape(15.dp)
private val ToastAccentBarWidth = 8.dp

/**
 * Draws [config] using the correct layout for its [ToastStyle].
 *
 * @param config the toast to render.
 * @param onDismiss invoked when the user taps the close button.
 * @param modifier applied to the toast container.
 */
@Composable
internal fun ToastLayout(
    config: ToastConfig,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tokens = resolveStyleTokens(config.style, config.type.colors())

    when (config.style) {
        ToastStyle.LEFT_ACCENT -> LeftAccentToast(config, tokens, onDismiss, modifier)
        else -> StandardToast(config, tokens, onDismiss, modifier)
    }
}

/** Resolved, theme-aware colours for one [ToastStyle] + [ToastType] combination. */
internal data class StyleTokens(
    val background: Color,
    val border: Color,
    val text: Color,
    val primary: Color,
)

@Composable
internal fun resolveStyleTokens(style: ToastStyle, c: ToastColors): StyleTokens {
    val scheme = MaterialTheme.colorScheme
    return when (style) {
        // Style 1 — neutral raised surface pill (adapts to dark theme).
        ToastStyle.DEFAULT -> StyleTokens(
            background = scheme.surfaceContainerHighest,
            border = scheme.outlineVariant.copy(alpha = 0.5f),
            text = scheme.onSurface,
            primary = c.primary,
        )
        // Style 2 — surface background, coloured border.
        ToastStyle.OUTLINED -> StyleTokens(
            background = scheme.surface,
            border = c.primary,
            text = scheme.onSurface,
            primary = c.primary,
        )
        // Style 3 — accent tint composited over the surface so it stays opaque in both themes.
        ToastStyle.TINTED -> StyleTokens(
            background = c.primary.copy(alpha = 0.12f).compositeOver(scheme.surface),
            border = c.primary.copy(alpha = 0.45f),
            text = scheme.onSurface,
            primary = c.primary,
        )
        // Style 4 — surface background with a coloured left accent bar.
        ToastStyle.LEFT_ACCENT -> StyleTokens(
            background = scheme.surfaceContainerHighest,
            border = scheme.outlineVariant.copy(alpha = 0.5f),
            text = scheme.onSurface,
            primary = c.primary,
        )
        // Style 5 — fully solid brand colour, white content.
        ToastStyle.FILLED_COLOR -> StyleTokens(
            background = c.primary,
            border = Color.Transparent,
            text = Color.White,
            primary = Color.White,
        )
    }
}

// ─── Standard Toast (Styles 1, 2, 3, 5) ───────────────────────────────────────

@Composable
private fun StandardToast(
    config: ToastConfig,
    tokens: StyleTokens,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(ToastShape)
            .background(tokens.background)
            .border(width = 1.dp, color = tokens.border, shape = ToastShape)
            .semantics { liveRegion = LiveRegionMode.Polite }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        ToastContent(config = config, tokens = tokens, onDismiss = onDismiss)
    }
}

// ─── Left Accent Toast (Style 4) ──────────────────────────────────────────────

@Composable
private fun LeftAccentToast(
    config: ToastConfig,
    tokens: StyleTokens,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(ToastShape)
            .background(tokens.background)
            .border(width = 1.dp, color = tokens.border, shape = ToastShape)
            .semantics { liveRegion = LiveRegionMode.Polite },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Coloured left accent bar.
        Box(
            modifier = Modifier
                .width(ToastAccentBarWidth)
                .height(48.dp)
                .background(tokens.primary),
        )
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            ToastContent(config = config, tokens = tokens, onDismiss = onDismiss)
        }
    }
}

// ─── Shared inner content: icon + message + button + close ─────────────────────

@Composable
private fun RowScope.ToastContent(
    config: ToastConfig,
    tokens: StyleTokens,
    onDismiss: () -> Unit,
) {
    if (config.showIcon) {
        ToastIcon(type = config.type, tint = tokens.primary)
    }

    Text(
        text = config.message,
        color = tokens.text,
        style = MaterialTheme.typography.bodyMedium,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
        modifier = Modifier.weight(1f),
    )

    if (config.showButton && config.buttonText.isNotBlank()) {
        TextButton(
            onClick = { config.onButtonClick?.invoke() },
            modifier = Modifier.height(32.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
        ) {
            Text(
                text = config.buttonText,
                color = tokens.primary,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
            )
        }
    }

    if (config.showCloseButton) {
        // Default IconButton size keeps a 48dp touch target for accessibility.
        IconButton(onClick = onDismiss) {
            Icon(
                painter = painterResource(R.drawable.clear_icon),
                contentDescription = stringResource(R.string.toastly_dismiss),
                tint = tokens.primary,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}

// ─── Preview ───────────────────────────────────────────────────────────────────

@PreviewLightDark
@Composable
private fun ToastStylesPreview() {
    MaterialTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                ToastStyle.entries.forEach { style ->
                    ToastType.entries.forEach { type ->
                        ToastLayout(
                            config = ToastConfig(
                                message = "${type.name.lowercase().replaceFirstChar { it.uppercase() }} message",
                                type = type,
                                style = style,
                                showIcon = true,
                                showButton = true,
                                showCloseButton = true,
                                buttonText = "UNDO",
                            ),
                            onDismiss = {},
                        )
                    }
                }
            }
        }
    }
}

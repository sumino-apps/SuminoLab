package com.sumino.designsystem.preview

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 27-03-2026 at 3:26 PM
 *
 * Reusable multi-preview annotations. Annotate a preview composable with one of
 * these to render it across several devices / font scales at once.
 */
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f,
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f,
)
annotation class FontScalePreviews

@Preview(
    name = "Phone - Portrait",
    device = Devices.PIXEL_4,
    showBackground = true
)
@Preview(
    name = "Phone - Landscape",
    device = Devices.PIXEL_4,
    showBackground = true,
    widthDp = 891,
    heightDp = 411
)
@Preview(
    name = "Tablet",
    device = Devices.TABLET,
    showBackground = true
)
@Preview(
    name = "Foldable",
    device = Devices.FOLDABLE,
    showBackground = true
)
annotation class MultiDevicePreview


@Preview(
    name = "Oppo A57",
    device = Devices.PIXEL_4,
    showBackground = true,
    widthDp = 360
)
annotation class DevicePreview

package com.sumino.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * SuminoLab type scale.
 *
 * Only the most-used roles are overridden here; every unspecified role falls back
 * to the Material 3 default. To brand the whole app, swap [FontFamily] in one place.
 */
private val systemFontFamily = FontFamily.Default

val baseline = Typography()

val Typography = baseline

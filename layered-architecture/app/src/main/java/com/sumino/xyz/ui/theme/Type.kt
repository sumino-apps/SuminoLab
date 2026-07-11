package com.sumino.xyz.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.sumino.xyz.R


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val robotoFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Roboto"), fontProvider = provider, weight = FontWeight.Thin
    ), Font(
        googleFont = GoogleFont("Roboto"), fontProvider = provider, weight = FontWeight.ExtraLight
    ), Font(
        googleFont = GoogleFont("Roboto"), fontProvider = provider, weight = FontWeight.Light
    ), Font(
        googleFont = GoogleFont("Roboto"), fontProvider = provider, weight = FontWeight.Normal
    ), Font(
        googleFont = GoogleFont("Roboto"), fontProvider = provider, weight = FontWeight.Medium
    ), Font(
        googleFont = GoogleFont("Roboto"), fontProvider = provider, weight = FontWeight.SemiBold
    ), Font(
        googleFont = GoogleFont("Roboto"), fontProvider = provider, weight = FontWeight.Bold
    )
)

val merriweatherFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Merriweather"), fontProvider = provider, weight = FontWeight.Light
    ), Font(
        googleFont = GoogleFont("Merriweather"), fontProvider = provider, weight = FontWeight.Normal
    ), Font(
        googleFont = GoogleFont("Merriweather"), fontProvider = provider, weight = FontWeight.Bold
    ), Font(
        googleFont = GoogleFont("Merriweather"), fontProvider = provider, weight = FontWeight.Black
    )
)


val baseline = Typography()

val AppTypography = Typography(
    // Display styles - Merriweather (Serif for impact)
    displayLarge = baseline.displayLarge.copy( // fontSize = 57.sp
        fontFamily = merriweatherFontFamily,
        fontWeight = FontWeight.Black
    ),
    displayMedium = baseline.displayMedium.copy( // fontSize = 45.sp
        fontFamily = merriweatherFontFamily,
        fontWeight = FontWeight.Bold
    ),
    displaySmall = baseline.displaySmall.copy( // fontSize = 36.sp
        fontFamily = merriweatherFontFamily,
        fontWeight = FontWeight.Bold
    ),

    // Headline styles - Merriweather (Serif for headings)
    headlineLarge = baseline.headlineLarge.copy( // fontSize = 32.sp
        fontFamily = merriweatherFontFamily,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = baseline.headlineMedium.copy( // fontSize = 28.sp
        fontFamily = merriweatherFontFamily,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = baseline.headlineSmall.copy( // fontSize = 24.sp
        fontFamily = merriweatherFontFamily,
        fontWeight = FontWeight.Normal
    ),

    // Title styles - Roboto (Sans for better readability)
    titleLarge = baseline.titleLarge.copy( // fontSize = 22.sp
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = baseline.titleMedium.copy( // fontSize = 16.sp
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Medium
    ),
    titleSmall = baseline.titleSmall.copy( // fontSize = 14.sp
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Medium
    ),

    // Body styles - Roboto (Sans for body text)
    bodyLarge = baseline.bodyLarge.copy( // fontSize = 16.sp
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = baseline.bodyMedium.copy( // fontSize = 14.sp
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = baseline.bodySmall.copy( // fontSize = 12.sp
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Light
    ),

    // Label styles - Roboto (Sans for UI elements)
    labelLarge = baseline.labelLarge.copy( // fontSize = 14.sp
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Medium
    ),
    labelMedium = baseline.labelMedium.copy( // fontSize = 12.sp
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Medium
    ),
    labelSmall = baseline.labelSmall.copy( // fontSize = 11.sp
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Medium
    ),
)

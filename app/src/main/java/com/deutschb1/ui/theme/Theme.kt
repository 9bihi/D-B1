package com.deutschb1.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = IosBlue,
    onPrimary = Color.White,
    secondary = IosGreen,
    onSecondary = Color.White,
    tertiary = IosOrange,
    background = SystemGroupedBackground,
    onBackground = LabelPrimary,
    surface = SystemBackground,
    onSurface = LabelPrimary,
    surfaceVariant = SystemGray5,
    onSurfaceVariant = SystemGray,
    error = IosRed,
    onError = Color.White,
    outline = SystemGray3,
)

private val DarkColorScheme = darkColorScheme(
    primary = IosBlue,
    onPrimary = Color.White,
    secondary = IosGreen,
    onSecondary = Color.White,
    tertiary = IosOrange,
    background = DarkBackground,
    onBackground = Color.White,
    surface = DarkSecondaryBackground,
    onSurface = Color.White,
    surfaceVariant = DarkTertiaryBackground,
    onSurfaceVariant = SystemGray,
    error = IosRed,
    onError = Color.White,
    outline = Color(0xFF38383A),
)

@Composable
fun DeutschB1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = DeutschTypography,
        content = content
    )
}

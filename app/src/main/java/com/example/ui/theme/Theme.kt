package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MindoraDarkColorScheme = darkColorScheme(
    primary = AuraViolet,
    onPrimary = Color.White,
    primaryContainer = MindoraSurfaceElevated,
    onPrimaryContainer = AuraVioletLight,
    secondary = AuraIndigo,
    onSecondary = Color.White,
    secondaryContainer = MindoraSurfaceHighlight,
    onSecondaryContainer = Color.White,
    tertiary = AuraCyan,
    onTertiary = Color.Black,
    background = MindoraDarkBg,
    onBackground = TextPrimary,
    surface = MindoraSurface,
    onSurface = TextPrimary,
    surfaceVariant = MindoraSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    error = AuraRose
)

private val MindoraLightColorScheme = lightColorScheme(
    primary = Color(0xFF6366F1),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE9FE),
    onPrimaryContainer = Color(0xFF4338CA),
    secondary = Color(0xFF7C3AED),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF3E8FF),
    onSecondaryContainer = Color(0xFF581C87),
    tertiary = Color(0xFF0891B2),
    onTertiary = Color.White,
    background = Color(0xFF0F1123), // Keep calming deep aura atmosphere as signature identity
    onBackground = TextPrimary,
    surface = MindoraSurface,
    onSurface = TextPrimary,
    surfaceVariant = MindoraSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    error = AuraRose
)

@Composable
fun MindoraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Mindora retains its signature immersive dark aura atmosphere by default
    val colorScheme = if (darkTheme) MindoraDarkColorScheme else MindoraDarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MindoraTypography,
        content = content
    )
}

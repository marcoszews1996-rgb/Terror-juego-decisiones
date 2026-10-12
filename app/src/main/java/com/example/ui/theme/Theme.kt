package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val HorrorColorScheme = darkColorScheme(
    primary = BloodRed,
    onPrimary = MutedBone,
    primaryContainer = BloodRedDark,
    onPrimaryContainer = MutedBone,
    secondary = PhosphorGreen,
    onSecondary = HorrorBlack,
    secondaryContainer = HorrorDarkSurface,
    onSecondaryContainer = PhosphorGreen,
    tertiary = ColdCyan,
    onTertiary = HorrorBlack,
    background = HorrorBlack,
    onBackground = MutedBone,
    surface = HorrorDarkSurface,
    onSurface = MutedBone,
    surfaceVariant = HorrorCardSurface,
    onSurfaceVariant = SubduedGray,
    outline = HorrorBorder,
    error = BloodRed,
    onError = MutedBone
)

@Composable
fun NoContestesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = HorrorColorScheme,
        typography = Typography,
        content = content
    )
}

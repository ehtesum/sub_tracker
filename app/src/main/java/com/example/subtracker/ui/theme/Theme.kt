package com.example.subtracker.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val TealPrimary = Color(0xFF0D9488)
val TealAccent = Color(0xFF2DD4BF)
val BackgroundDark = Color(0xFF0F172A)
val SurfaceDark = Color(0xFF1E293B)
val TextLight = Color(0xFFF8FAFC)
val TextMuted = Color(0xFF94A3B8)
val AlertWarning = Color(0xFFF59E0B)
val AlertCritical = Color(0xFFEF4444)

val DarkColors = darkColorScheme(
    primary = TealAccent,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = Color.Black,
    onBackground = TextLight,
    onSurface = TextLight
)

val LightColors = lightColorScheme(
    primary = TealPrimary,
    background = Color(0xFFF8FAFC),
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color(0xFF0F172A),
    onSurface = Color(0xFF0F172A)
)

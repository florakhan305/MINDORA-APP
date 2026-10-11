package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// MINDORA Core Palette - Mind + Aura
val MindoraDarkBg = Color(0xFF090A14)
val MindoraSurface = Color(0xFF121426)
val MindoraSurfaceElevated = Color(0xFF1B1E38)
val MindoraSurfaceHighlight = Color(0xFF262A4E)

val AuraViolet = Color(0xFF8B5CF6)
val AuraVioletLight = Color(0xFFA78BFA)
val AuraIndigo = Color(0xFF6366F1)
val AuraTeal = Color(0xFF14B8A6)
val AuraCyan = Color(0xFF06B6D4)
val AuraRose = Color(0xFFF43F5E)
val AuraAmber = Color(0xFFF59E0B)
val AuraEmerald = Color(0xFF10B981)

val TextPrimary = Color(0xFFF1F5F9)
val TextSecondary = Color(0xFF94A3B8)
val TextMuted = Color(0xFF64748B)

val AnonymousAura = Color(0xFF6D28D9)
val BorderSubtle = Color(0xFF2E335B)

// Mood Colors & Aura Gradients
val MoodHappy = Color(0xFFF59E0B)
val MoodSad = Color(0xFF64748B)
val MoodAngry = Color(0xFFEF4444)
val MoodTired = Color(0xFF8B5CF6)
val MoodLost = Color(0xFF06B6D4)
val MoodLoved = Color(0xFFEC4899)
val MoodMotivated = Color(0xFFF97316)
val MoodLonely = Color(0xFF6366F1)

val AuraMainBrush = Brush.linearGradient(
    colors = listOf(AuraViolet, AuraIndigo, AuraCyan)
)

val AuraAnonymousBrush = Brush.linearGradient(
    colors = listOf(Color(0xFF4C1D95), Color(0xFF2E1065), Color(0xFF1E1B4B))
)

val AuraWarmBrush = Brush.linearGradient(
    colors = listOf(AuraRose, AuraAmber)
)

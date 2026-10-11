package com.example.mindora.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AuraCyan
import com.example.ui.theme.AuraIndigo
import com.example.ui.theme.AuraViolet
import com.example.ui.theme.AuraVioletLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.MindoraDarkBg
import com.example.ui.theme.MindoraSurface
import com.example.ui.theme.MindoraSurfaceElevated
import com.example.ui.theme.MindoraSurfaceHighlight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AiAssistantScreen(
    prompt: String,
    result: String,
    isLoading: Boolean,
    activeMode: String,
    onPromptChange: (String) -> Unit,
    onModeChange: (String) -> Unit,
    onGenerate: () -> Unit,
    onShareAsPost: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val samplePrompts = listOf(
        "I am sad because my friend left me.",
        "I feel disconnected from everyone lately.",
        "Write a midnight mystery story beginning.",
        "Help me write a Hindi/Urdu shayari on separation."
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MindoraDarkBg)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // AI Hero Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF3B1568),
                            Color(0xFF191436),
                            Color(0xFF0F0E24)
                        )
                    )
                )
                .border(
                    1.2.dp,
                    Brush.linearGradient(listOf(AuraViolet, AuraCyan)),
                    RoundedCornerShape(20.dp)
                )
                .padding(18.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(AuraViolet, AuraCyan))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Mindora AI",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "MINDORA AI Studio",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary
                        )
                        Text(
                            text = "Assisting your creativity, not replacing it.",
                            style = MaterialTheme.typography.labelSmall,
                            color = AuraVioletLight
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Transform raw feelings into resonant prose, poetry, and empathetic clarity.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFDDD6FE)
                )
            }
        }

        // Mode Selector: Poetic Alchemist | Empathetic Companion | Story Spark
        Text(
            text = "Select Mindora AI Lens",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AiModeButton(
                title = "✨ Poetic Alchemist",
                subtitle = "Refines thoughts into verse",
                isSelected = activeMode == "POETIC",
                onClick = { onModeChange("POETIC") },
                modifier = Modifier.weight(1f)
            )

            AiModeButton(
                title = "🕊️ Empathetic Mind",
                subtitle = "Gentle validation & solace",
                isSelected = activeMode == "REFLECT",
                onClick = { onModeChange("REFLECT") },
                modifier = Modifier.weight(1f)
            )

            AiModeButton(
                title = "✍️ Story Spark",
                subtitle = "Narrative ideas & hooks",
                isSelected = activeMode == "STORY",
                onClick = { onModeChange("STORY") },
                modifier = Modifier.weight(1f)
            )
        }

        // Sample Prompt Quick Pills
        Text(
            text = "Try a sample raw thought:",
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary
        )

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            samplePrompts.forEach { sample ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(MindoraSurfaceElevated)
                        .border(0.8.dp, BorderSubtle, RoundedCornerShape(10.dp))
                        .clickable { onPromptChange(sample) }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "💬 \"$sample\"",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Text Input Field
        OutlinedTextField(
            value = prompt,
            onValueChange = onPromptChange,
            placeholder = {
                Text(
                    "Express what is in your mind here... E.g. \"I am sad because my friend left me.\"",
                    color = TextMuted,
                    fontSize = 14.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .testTag("ai_prompt_input"),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AuraViolet,
                unfocusedBorderColor = BorderSubtle,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = AuraViolet,
                focusedContainerColor = MindoraSurfaceElevated,
                unfocusedContainerColor = MindoraSurfaceElevated
            )
        )

        // Generate CTA
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    if (prompt.isNotBlank() && !isLoading) {
                        Brush.linearGradient(listOf(AuraViolet, AuraIndigo, AuraCyan))
                    } else {
                        Brush.linearGradient(listOf(MindoraSurfaceHighlight, MindoraSurfaceHighlight))
                    }
                )
                .clickable(enabled = prompt.isNotBlank() && !isLoading) {
                    onGenerate()
                }
                .testTag("invoke_ai_button"),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Synthesizing Mind Resonance...",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Generate",
                        tint = if (prompt.isNotBlank()) Color.White else TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Refine with Mindora AI ✨",
                        color = if (prompt.isNotBlank()) Color.White else TextMuted,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Result Card
        AnimatedVisibility(visible = result.isNotBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF161533))
                    .border(1.2.dp, AuraViolet, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🌌", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "MINDORA Reflection",
                                style = MaterialTheme.typography.titleMedium,
                                color = AuraVioletLight
                            )
                        }

                        // Copy button
                        IconButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Mindora AI Reflection", result)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = TextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = result,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontSize = 16.sp,
                            lineHeight = 25.sp
                        ),
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action: Post directly as thought
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(AuraViolet)
                            .clickable { onShareAsPost(result) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "Post",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Use this in New Post ✍️",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AiModeButton(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Color(0xFF2E1A47) else MindoraSurfaceElevated)
            .border(1.dp, if (isSelected) AuraViolet else BorderSubtle, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                color = if (isSelected) AuraVioletLight else TextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 9.sp,
                color = TextMuted
            )
        }
    }
}

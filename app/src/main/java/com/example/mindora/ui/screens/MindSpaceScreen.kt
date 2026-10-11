package com.example.mindora.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindora.data.model.MoodType
import com.example.mindora.data.model.PostEntity
import com.example.mindora.ui.components.PostCard
import com.example.ui.theme.AuraCyan
import com.example.ui.theme.AuraIndigo
import com.example.ui.theme.AuraViolet
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.MindoraDarkBg
import com.example.ui.theme.MindoraSurface
import com.example.ui.theme.MindoraSurfaceElevated
import com.example.ui.theme.MindoraSurfaceHighlight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MindSpaceScreen(
    currentMood: MoodType,
    allPosts: List<PostEntity>,
    onSelectMood: (MoodType) -> Unit,
    onOpenCreatePost: () -> Unit,
    onLikePost: (PostEntity) -> Unit,
    onCommentPost: (PostEntity) -> Unit,
    onSavePost: (PostEntity) -> Unit,
    onFollowPost: (PostEntity) -> Unit,
    onVotePoll: (PostEntity, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val moodColor = Color(currentMood.colorHex)
    val animatedAuraColor by animateColorAsState(targetValue = moodColor, label = "aura_color")

    // Filter posts that match this mood or are general supportive
    val resonantPosts = allPosts.filter {
        it.moodTag.equals(currentMood.id, ignoreCase = true) || it.moodTag == null
    }

    val simulatedMindsCount = when (currentMood) {
        MoodType.LONELY -> 1420
        MoodType.TIRED -> 2310
        MoodType.LOST -> 1890
        MoodType.HAPPY -> 3450
        MoodType.MOTIVATED -> 1670
        MoodType.LOVED -> 2100
        MoodType.SAD -> 1980
        MoodType.ANGRY -> 890
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MindoraDarkBg),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // MindSpace Signature Aura Header Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                animatedAuraColor.copy(alpha = 0.35f),
                                Color(0xFF13152C),
                                Color(0xFF0B0D1A)
                            )
                        )
                    )
                    .border(
                        1.2.dp,
                        Brush.linearGradient(
                            listOf(animatedAuraColor.copy(alpha = 0.8f), BorderSubtle)
                        ),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🧠", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "MindSpace",
                                style = MaterialTheme.typography.titleLarge,
                                color = TextPrimary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(animatedAuraColor.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Aura Resonance",
                                color = animatedAuraColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "How is your mind feeling right now?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Choose your aura to discover relatable minds and grounding thoughts.",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Mood selector chips grid
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MoodType.entries.forEach { mood ->
                            val isSelected = currentMood == mood
                            val chipColor = Color(mood.colorHex)

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(
                                        if (isSelected) chipColor.copy(alpha = 0.3f) else MindoraSurfaceElevated
                                    )
                                    .border(
                                        1.dp,
                                        if (isSelected) chipColor else BorderSubtle,
                                        RoundedCornerShape(14.dp)
                                    )
                                    .clickable { onSelectMood(mood) }
                                    .padding(horizontal = 10.dp, vertical = 7.dp)
                                    .testTag("mindspace_mood_${mood.id}")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = mood.emoji, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = mood.label,
                                        color = if (isSelected) Color.White else TextSecondary,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Live Resonance Counter Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MindoraSurfaceElevated)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(animatedAuraColor.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.GraphicEq,
                                contentDescription = "Resonance",
                                tint = animatedAuraColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "$simulatedMindsCount minds feeling ${currentMood.label} right now",
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 13.sp,
                                color = TextPrimary
                            )
                            Text(
                                text = "${currentMood.tagline} • You are not alone.",
                                style = MaterialTheme.typography.labelSmall,
                                color = animatedAuraColor
                            )
                        }
                    }

                    // Express Thought CTA
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.linearGradient(listOf(AuraViolet, AuraIndigo))
                            )
                            .clickable { onOpenCreatePost() }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .testTag("express_mood_thought_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "Express",
                                tint = Color.White,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Express",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // Gentle Sanctuary Note
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF14172B))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Spa,
                    contentDescription = "Mindfulness",
                    tint = AuraCyan,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "MINDORA does not diagnose or judge. We simply give your mind space to breathe, write, and find stillness.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }

        // Section Title: Relatable Thoughts in this Aura
        item {
            Text(
                text = "Resonant Thoughts (${currentMood.label} Aura)",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
        }

        // Posts matching the mood
        if (resonantPosts.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Be the first to share a reflection in the ${currentMood.label} aura.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted
                    )
                }
            }
        } else {
            items(resonantPosts, key = { it.id }) { post ->
                PostCard(
                    post = post,
                    onLikeClick = { onLikePost(post) },
                    onCommentClick = { onCommentPost(post) },
                    onSaveClick = { onSavePost(post) },
                    onFollowClick = { onFollowPost(post) },
                    onVotePoll = { optionIndex -> onVotePoll(post, optionIndex) }
                )
            }
        }
    }
}

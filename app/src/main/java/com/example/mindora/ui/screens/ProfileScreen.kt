package com.example.mindora.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindora.data.model.PostEntity
import com.example.mindora.data.model.StoryEntity
import com.example.mindora.data.repository.UserProfileState
import com.example.mindora.ui.components.PostCard
import com.example.ui.theme.AuraAmber
import com.example.ui.theme.AuraCyan
import com.example.ui.theme.AuraIndigo
import com.example.ui.theme.AuraRose
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

enum class ProfileTab {
    POSTS,
    STORIES,
    SAVED,
    ABOUT
}

@Composable
fun ProfileScreen(
    profile: UserProfileState,
    userPosts: List<PostEntity>,
    savedPosts: List<PostEntity>,
    stories: List<StoryEntity>,
    onLikePost: (PostEntity) -> Unit,
    onCommentPost: (PostEntity) -> Unit,
    onSavePost: (PostEntity) -> Unit,
    onFollowPost: (PostEntity) -> Unit,
    onVotePoll: (PostEntity, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf(ProfileTab.POSTS) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MindoraDarkBg),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Profile Header Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MindoraSurface)
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(68.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.sweepGradient(listOf(AuraViolet, AuraCyan, AuraRose, AuraViolet))
                                    )
                                    .border(2.dp, Color(0xFFE9D5FF), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = profile.name.take(1),
                                    color = Color.White,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = profile.name,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = TextPrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Verified Aura",
                                        tint = AuraVioletLight,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                Text(
                                    text = profile.handle,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextMuted
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                // Current Mood Aura chip
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(profile.currentMood.colorHex).copy(alpha = 0.2f))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "${profile.currentMood.emoji} Aura: ${profile.currentMood.label}",
                                        color = Color(profile.currentMood.colorHex),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = profile.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.sp,
                        color = Color(0xFFDDD6FE)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = profile.bio,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Metrics: Posts | Followers | Following
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MindoraSurfaceElevated)
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ProfileMetricItem("${profile.postsCount}", "Posts")
                        ProfileMetricItem("2.4K", "Followers")
                        ProfileMetricItem("${profile.followingCount}", "Following")
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Reputation / MindScore Card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.linearGradient(listOf(Color(0xFF281347), Color(0xFF131735)))
                            )
                            .border(1.dp, AuraViolet, RoundedCornerShape(14.dp))
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(AuraViolet),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MilitaryTech,
                                        contentDescription = "MindScore",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${profile.mindScore} MindScore",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "⭐ ${profile.xp} XP",
                                            color = AuraAmber,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                    Text(
                                        text = "${profile.levelName} • Positive contributor",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AuraVioletLight
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Profile Navigation Tabs: Posts | Stories | Saved | About
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MindoraSurface)
                    .border(1.dp, BorderSubtle)
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ProfileTabButton(
                    label = "Posts",
                    isSelected = activeTab == ProfileTab.POSTS,
                    onClick = { activeTab = ProfileTab.POSTS },
                    tag = "profile_tab_posts"
                )
                ProfileTabButton(
                    label = "Stories",
                    isSelected = activeTab == ProfileTab.STORIES,
                    onClick = { activeTab = ProfileTab.STORIES },
                    tag = "profile_tab_stories"
                )
                ProfileTabButton(
                    label = "Saved",
                    isSelected = activeTab == ProfileTab.SAVED,
                    onClick = { activeTab = ProfileTab.SAVED },
                    tag = "profile_tab_saved"
                )
                ProfileTabButton(
                    label = "About MINDORA",
                    isSelected = activeTab == ProfileTab.ABOUT,
                    onClick = { activeTab = ProfileTab.ABOUT },
                    tag = "profile_tab_about"
                )
            }
        }

        // Tab Content
        when (activeTab) {
            ProfileTab.POSTS -> {
                val myPosts = userPosts.filter { it.authorName == profile.name }
                if (myPosts.isEmpty()) {
                    item {
                        EmptyTabPlaceholder("No posts authored yet. Express what's in your mind!")
                    }
                } else {
                    items(myPosts, key = { it.id }) { post ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                            PostCard(
                                post = post,
                                onLikeClick = { onLikePost(post) },
                                onCommentClick = { onCommentPost(post) },
                                onSaveClick = { onSavePost(post) },
                                onFollowClick = { onFollowPost(post) },
                                onVotePoll = { onVotePoll(post, it) }
                            )
                        }
                    }
                }
            }
            ProfileTab.STORIES -> {
                items(stories, key = { it.id }) { story ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(MindoraSurfaceElevated)
                            .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
                            .padding(14.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = story.title,
                                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif),
                                    color = TextPrimary
                                )
                                Text(
                                    text = story.genre,
                                    color = AuraVioletLight,
                                    fontSize = 11.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = story.summary,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
            ProfileTab.SAVED -> {
                if (savedPosts.isEmpty()) {
                    item {
                        EmptyTabPlaceholder("No bookmarked reflections yet.")
                    }
                } else {
                    items(savedPosts, key = { it.id }) { post ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                            PostCard(
                                post = post,
                                onLikeClick = { onLikePost(post) },
                                onCommentClick = { onCommentPost(post) },
                                onSaveClick = { onSavePost(post) },
                                onFollowClick = { onFollowPost(post) },
                                onVotePoll = { onVotePoll(post, it) }
                            )
                        }
                    }
                }
            }
            ProfileTab.ABOUT -> {
                item {
                    AboutMindoraSection()
                }
            }
        }
    }
}

@Composable
private fun AboutMindoraSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Founder Tribute
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.radialGradient(
                        listOf(Color(0xFF38155C), Color(0xFF1B163B), Color(0xFF0F1024))
                    )
                )
                .border(1.2.dp, AuraViolet, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "👑", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Founded by Forida",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFFE9D5FF)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "“A safe digital place where you can express what’s in your mind without feeling judged.”",
                    style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Serif),
                    color = Color.White,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "MINDORA = Mind + Aura. Designed to move social media away from showing off and toward emotional honesty, literature, and supportive human connection.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFC4B5FD),
                    fontSize = 12.sp
                )
            }
        }

        // Privacy & Safety Safeguards
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(MindoraSurfaceElevated)
                .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Safety",
                        tint = AuraCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "12. Privacy & Safety Manifesto",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                }

                Text(
                    text = "• Anonymous Post Safeguards: Identity is strictly decoupled.\n• Automated & Human Moderation for hate speech, harassment, and spam.\n• Safe Direct Messaging: Message requests and instant block/mute.\n• Non-judgmental mental wellness support (without replacing medical care).",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }

        // MINDORA Roadmap
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(MindoraSurfaceElevated)
                .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "🌐 Development Journey & Vision",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Text(
                    text = "Phase 1: Safe Prototype & Anonymous Expression ✅\nPhase 2: Real accounts, persistent thoughts, and follow network ✅\nPhase 3: Communities, AI tools, and 7-Day Challenges ✅\nPhase 4: Creator monetization and global sanctuary.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun ProfileMetricItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TextMuted
        )
    }
}

@Composable
private fun ProfileTabButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    tag: String
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .testTag(tag)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = if (isSelected) AuraVioletLight else TextMuted,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(16.dp)
                    .height(2.dp)
                    .background(if (isSelected) AuraViolet else Color.Transparent)
            )
        }
    }
}

@Composable
private fun EmptyTabPlaceholder(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyMedium, color = TextMuted)
    }
}

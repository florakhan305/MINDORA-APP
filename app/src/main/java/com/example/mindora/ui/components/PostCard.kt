package com.example.mindora.ui.components

import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.mindora.data.model.MoodType
import com.example.mindora.data.model.PostEntity
import com.example.ui.theme.AuraAnonymousBrush
import com.example.ui.theme.AuraCyan
import com.example.ui.theme.AuraIndigo
import com.example.ui.theme.AuraRose
import com.example.ui.theme.AuraViolet
import com.example.ui.theme.AuraVioletLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.MindoraSurfaceElevated
import com.example.ui.theme.MindoraSurfaceHighlight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun PostCard(
    post: PostEntity,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onSaveClick: () -> Unit,
    onFollowClick: () -> Unit,
    onVotePoll: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isAnonymous = post.isAnonymous
    val mood = MoodType.fromId(post.moodTag)

    val cardBorderBrush = if (isAnonymous) {
        Brush.linearGradient(listOf(Color(0xFF8B5CF6), Color(0xFF4C1D95), Color(0xFF1E1B4B)))
    } else {
        Brush.linearGradient(listOf(BorderSubtle, BorderSubtle))
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isAnonymous) Color(0xFF13112B) else MindoraSurfaceElevated
            )
            .border(1.dp, cardBorderBrush, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .testTag("post_card_${post.id}")
    ) {
        Column {
            // Header: Author Avatar & Name & Tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    if (isAnonymous) {
                        // Anonymous Mind Avatar
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(AuraAnonymousBrush)
                                .border(1.5.dp, AuraVioletLight, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = "Anonymous Mind",
                                tint = Color(0xFFE9D5FF),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    } else {
                        // User Avatar
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(listOf(AuraViolet, AuraCyan))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = post.authorName.take(1),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = post.authorName,
                                style = MaterialTheme.typography.titleMedium,
                                color = if (isAnonymous) Color(0xFFD8B4FE) else TextPrimary
                            )
                            if (post.authorName.contains("Founder")) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFF4C1D95))
                                        .padding(horizontal = 5.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "Founder",
                                        color = Color(0xFFE9D5FF),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = post.authorHandle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextMuted,
                                fontSize = 12.sp
                            )
                            Text(text = "•", color = TextMuted, fontSize = 10.sp)
                            Text(
                                text = "${post.category}",
                                style = MaterialTheme.typography.labelSmall,
                                color = AuraCyan,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                // Mood Chip / Follow action
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (post.moodTag != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(mood.colorHex).copy(alpha = 0.15f))
                                .border(0.8.dp, Color(mood.colorHex).copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "${mood.emoji} ${mood.label}",
                                color = Color(mood.colorHex),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    if (!isAnonymous && post.authorName != "Akira") {
                        val followColor by animateColorAsState(
                            targetValue = if (post.isFollowing) AuraViolet else TextSecondary,
                            label = "follow_color"
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    if (post.isFollowing) Color(0xFF2E1065) else MindoraSurfaceHighlight
                                )
                                .clickable { onFollowClick() }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (post.isFollowing) "Following" else "+ Follow",
                                color = followColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Anonymous Safe Space Header Banner
            if (isAnonymous) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF1E1538))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Safe Non-judgmental Space",
                        tint = Color(0xFFA78BFA),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Anonymous Mind Sanctuary • Responding with kindness only",
                        color = Color(0xFFC4B5FD),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Post Content Text
            Text(
                text = post.content,
                style = if (post.category == "Quotes" || post.category == "Poetry" || post.category == "Shayari") {
                    MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )
                } else {
                    MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                },
                color = TextPrimary
            )

            // Optional Interactive Poll
            if (post.pollQuestion != null && post.pollOptionsJson != null && post.pollVotesJson != null) {
                Spacer(modifier = Modifier.height(14.dp))
                val options = post.pollOptionsJson.split("||")
                val votes = post.pollVotesJson.split("||").map { it.toIntOrNull() ?: 0 }
                val totalVotes = votes.sum().coerceAtLeast(1)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF181B34))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "📊 ${post.pollQuestion}",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontSize = 14.sp
                    )

                    options.forEachIndexed { index, option ->
                        val voteCount = votes.getOrElse(index) { 0 }
                        val percentage = (voteCount.toFloat() / totalVotes.toFloat())
                        val isVoted = post.userPollVotedIndex == index

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (isVoted) Color(0xFF2E1A47) else MindoraSurfaceHighlight
                                )
                                .border(
                                    1.dp,
                                    if (isVoted) AuraViolet else Color.Transparent,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onVotePoll(index) }
                                .padding(10.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        if (isVoted) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = "Selected",
                                                tint = AuraVioletLight,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                        }
                                        Text(
                                            text = option,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = if (isVoted) Color.White else TextPrimary,
                                            fontSize = 13.sp
                                        )
                                    }

                                    Text(
                                        text = "${(percentage * 100).toInt()}% ($voteCount)",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextSecondary
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                LinearProgressIndicator(
                                    progress = { percentage },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(2.dp)),
                                    color = if (isVoted) AuraViolet else AuraCyan,
                                    trackColor = Color(0xFF101224)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Row: Like, Comment, Share, Save
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Like button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onLikeClick() }
                        .padding(4.dp)
                        .testTag("post_like_button_${post.id}")
                ) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (post.isLiked) AuraRose else TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${post.likesCount}",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (post.isLiked) AuraRose else TextSecondary
                    )
                }

                // Comment button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onCommentClick() }
                        .padding(4.dp)
                        .testTag("post_comment_button_${post.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Comments",
                        tint = TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${post.commentsCount}",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextSecondary
                    )
                }

                // Share button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "From MINDORA (Mind + Aura):\n\n\"${post.content}\"\n\n— ${post.authorName}"
                                )
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "Share Mindora Thought")
                            context.startActivity(shareIntent)
                        }
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${post.sharesCount}",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextSecondary
                    )
                }

                // Save button
                IconButton(
                    onClick = onSaveClick,
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("post_save_button_${post.id}")
                ) {
                    Icon(
                        imageVector = if (post.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save / Bookmark",
                        tint = if (post.isSaved) AuraVioletLight else TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

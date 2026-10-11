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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindora.data.model.PostEntity
import com.example.mindora.ui.components.PostCard
import com.example.mindora.ui.viewmodel.FeedFilter
import com.example.ui.theme.AuraAnonymousBrush
import com.example.ui.theme.AuraCyan
import com.example.ui.theme.AuraViolet
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.MindoraDarkBg
import com.example.ui.theme.MindoraSurface
import com.example.ui.theme.MindoraSurfaceElevated
import com.example.ui.theme.MindoraSurfaceHighlight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun FeedScreen(
    posts: List<PostEntity>,
    currentFilter: FeedFilter,
    selectedCategory: String?,
    onFilterChange: (FeedFilter) -> Unit,
    onCategoryChange: (String?) -> Unit,
    onLikePost: (PostEntity) -> Unit,
    onCommentPost: (PostEntity) -> Unit,
    onSavePost: (PostEntity) -> Unit,
    onFollowPost: (PostEntity) -> Unit,
    onVotePoll: (PostEntity, Int) -> Unit,
    onOpenCreatePost: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "Thoughts", "Poetry", "Shayari", "Stories", "Quotes", "Questions", "Motivation")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MindoraDarkBg)
    ) {
        // Feed Filter Tabs: For You | Following | Explore | Anonymous
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MindoraSurface)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FeedTabItem(
                label = "For You",
                isSelected = currentFilter == FeedFilter.FOR_YOU,
                onClick = { onFilterChange(FeedFilter.FOR_YOU) },
                tag = "tab_for_you"
            )

            FeedTabItem(
                label = "Following",
                isSelected = currentFilter == FeedFilter.FOLLOWING,
                onClick = { onFilterChange(FeedFilter.FOLLOWING) },
                tag = "tab_following"
            )

            FeedTabItem(
                label = "Explore",
                isSelected = currentFilter == FeedFilter.EXPLORE,
                onClick = { onFilterChange(FeedFilter.EXPLORE) },
                tag = "tab_explore"
            )

            // Anonymous Tab (Special Highlight)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .then(
                        if (currentFilter == FeedFilter.ANONYMOUS) Modifier.background(AuraAnonymousBrush)
                        else Modifier.background(MindoraSurfaceElevated)
                    )
                    .border(
                        1.dp,
                        if (currentFilter == FeedFilter.ANONYMOUS) AuraViolet else Color.Transparent,
                        RoundedCornerShape(16.dp)
                    )
                    .clickable { onFilterChange(FeedFilter.ANONYMOUS) }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .testTag("tab_anonymous")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "Anonymous",
                        tint = if (currentFilter == FeedFilter.ANONYMOUS) Color(0xFFE9D5FF) else TextSecondary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Anonymous",
                        color = if (currentFilter == FeedFilter.ANONYMOUS) Color(0xFFE9D5FF) else TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Horizontal Category Filter Pills
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MindoraSurface)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { cat ->
                val isSelected = (selectedCategory == null && cat == "All") || (selectedCategory == cat)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isSelected) MindoraSurfaceHighlight else MindoraSurfaceElevated
                        )
                        .border(
                            1.dp,
                            if (isSelected) AuraViolet else BorderSubtle,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            onCategoryChange(if (cat == "All") null else cat)
                        }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = cat,
                        color = if (isSelected) Color.White else TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }

        // Posts List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Inspirational Banner for Anonymous Feed
            if (currentFilter == FeedFilter.ANONYMOUS) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.linearGradient(listOf(Color(0xFF3B1360), Color(0xFF1E1035)))
                            )
                            .border(1.dp, AuraViolet, RoundedCornerShape(14.dp))
                            .padding(14.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🌙", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Anonymous Mind Haven",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFFE9D5FF)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Every post here is shared without identity. Respond only with empathy, comfort, and kindness.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFDDD6FE),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            if (posts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "🌌", fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Quiet in this space for now.",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Be the first mind to leave a gentle ripple.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextMuted
                            )
                        }
                    }
                }
            } else {
                items(posts, key = { it.id }) { post ->
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
}

@Composable
private fun FeedTabItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    tag: String
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .testTag(tag)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 14.sp,
                color = if (isSelected) TextPrimary else TextMuted,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(2.dp)
                    .background(if (isSelected) AuraViolet else Color.Transparent)
            )
        }
    }
}

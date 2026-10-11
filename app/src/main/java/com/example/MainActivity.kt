package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mindora.data.model.MoodType
import com.example.mindora.ui.components.CommentSheet
import com.example.mindora.ui.components.CreatePostDialog
import com.example.mindora.ui.components.MindoraTopBar
import com.example.mindora.ui.screens.AiAssistantScreen
import com.example.mindora.ui.screens.ChallengesScreen
import com.example.mindora.ui.screens.CommunitiesScreen
import com.example.mindora.ui.screens.FeedScreen
import com.example.mindora.ui.screens.MessagesScreen
import com.example.mindora.ui.screens.MindSpaceScreen
import com.example.mindora.ui.screens.ProfileScreen
import com.example.mindora.ui.screens.WritingCornerScreen
import com.example.mindora.ui.viewmodel.MainTab
import com.example.mindora.ui.viewmodel.MindoraViewModel
import com.example.ui.theme.AuraCyan
import com.example.ui.theme.AuraIndigo
import com.example.ui.theme.AuraViolet
import com.example.ui.theme.AuraVioletLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.MindoraDarkBg
import com.example.ui.theme.MindoraSurface
import com.example.ui.theme.MindoraSurfaceElevated
import com.example.ui.theme.MindoraTheme
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindoraTheme {
                MindoraApp()
            }
        }
    }
}

@Composable
fun MindoraApp(
    viewModel: MindoraViewModel = viewModel()
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val filteredPosts by viewModel.filteredPosts.collectAsState()
    val feedFilter by viewModel.feedFilter.collectAsState()
    val categoryFilter by viewModel.categoryFilter.collectAsState()

    val allStories by viewModel.allStories.collectAsState()
    val allCommunities by viewModel.allCommunities.collectAsState()
    val allChallenges by viewModel.allChallenges.collectAsState()
    val allMessages by viewModel.allMessages.collectAsState()

    val activePostForComments by viewModel.activePostForComments.collectAsState()
    val commentsForActivePost by viewModel.commentsForActivePost.collectAsState()
    val activeStoryForReading by viewModel.activeStoryForReading.collectAsState()
    val activeChatPartner by viewModel.activeChatPartner.collectAsState()
    val showCreatePostDialog by viewModel.showCreatePostDialog.collectAsState()

    val aiPrompt by viewModel.aiPrompt.collectAsState()
    val aiResult by viewModel.aiResult.collectAsState()
    val aiLoading by viewModel.aiLoading.collectAsState()
    val aiMode by viewModel.aiMode.collectAsState()

    // Handle system back navigation if not on FEED
    if (currentTab != MainTab.FEED) {
        BackHandler {
            viewModel.setTab(MainTab.FEED)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MindoraDarkBg,
        topBar = {
            MindoraTopBar(
                currentMood = userProfile.currentMood,
                onMoodClick = { viewModel.setTab(MainTab.MINDSPACE) },
                onChatClick = { viewModel.setTab(MainTab.MESSAGES) },
                onCreatePostClick = { viewModel.openCreatePost() }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MindoraSurface)
                    .navigationBarsPadding()
            ) {
                // Secondary quick sub-navigation pills (Clubs, Challenges, Chat)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        QuickTabPill(
                            label = "Clubs 👥",
                            isSelected = currentTab == MainTab.COMMUNITIES,
                            onClick = { viewModel.setTab(MainTab.COMMUNITIES) },
                            tag = "nav_pill_communities"
                        )
                    }
                    item {
                        QuickTabPill(
                            label = "7-Day Challenge 🏆",
                            isSelected = currentTab == MainTab.CHALLENGES,
                            onClick = { viewModel.setTab(MainTab.CHALLENGES) },
                            tag = "nav_pill_challenges"
                        )
                    }
                    item {
                        QuickTabPill(
                            label = "Supportive Chat 💬",
                            isSelected = currentTab == MainTab.MESSAGES,
                            onClick = { viewModel.setTab(MainTab.MESSAGES) },
                            tag = "nav_pill_messages"
                        )
                    }
                }

                // Primary 5-Tab Navigation Bar
                NavigationBar(
                    containerColor = MindoraSurface,
                    contentColor = TextPrimary,
                    tonalElevation = 0.dp,
                    modifier = Modifier.height(60.dp)
                ) {
                    NavigationBarItem(
                        selected = currentTab == MainTab.FEED,
                        onClick = { viewModel.setTab(MainTab.FEED) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Feed") },
                        label = { Text("Feed", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = AuraVioletLight,
                            indicatorColor = AuraViolet,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_feed")
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.MINDSPACE,
                        onClick = { viewModel.setTab(MainTab.MINDSPACE) },
                        icon = { Icon(Icons.Default.Psychology, contentDescription = "MindSpace") },
                        label = { Text("MindSpace", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = AuraVioletLight,
                            indicatorColor = AuraViolet,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_mindspace")
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.WRITING,
                        onClick = { viewModel.setTab(MainTab.WRITING) },
                        icon = { Icon(Icons.Default.EditNote, contentDescription = "Writing Corner") },
                        label = { Text("Writing", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = AuraVioletLight,
                            indicatorColor = AuraViolet,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_writing")
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.AI_ASSIST,
                        onClick = { viewModel.setTab(MainTab.AI_ASSIST) },
                        icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "MINDORA AI") },
                        label = { Text("AI Studio", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = AuraVioletLight,
                            indicatorColor = AuraViolet,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_ai")
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.PROFILE,
                        onClick = { viewModel.setTab(MainTab.PROFILE) },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        label = { Text("Profile", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = AuraVioletLight,
                            indicatorColor = AuraViolet,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_profile")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                MainTab.FEED -> {
                    FeedScreen(
                        posts = filteredPosts,
                        currentFilter = feedFilter,
                        selectedCategory = categoryFilter,
                        onFilterChange = { viewModel.setFeedFilter(it) },
                        onCategoryChange = { viewModel.setCategoryFilter(it) },
                        onLikePost = { viewModel.toggleLike(it) },
                        onCommentPost = { viewModel.openComments(it) },
                        onSavePost = { viewModel.toggleSave(it) },
                        onFollowPost = { viewModel.toggleFollow(it) },
                        onVotePoll = { post, index -> viewModel.votePoll(post, index) },
                        onOpenCreatePost = { viewModel.openCreatePost() }
                    )
                }

                MainTab.MINDSPACE -> {
                    MindSpaceScreen(
                        currentMood = userProfile.currentMood,
                        allPosts = filteredPosts,
                        onSelectMood = { viewModel.setMood(it) },
                        onOpenCreatePost = { viewModel.openCreatePost() },
                        onLikePost = { viewModel.toggleLike(it) },
                        onCommentPost = { viewModel.openComments(it) },
                        onSavePost = { viewModel.toggleSave(it) },
                        onFollowPost = { viewModel.toggleFollow(it) },
                        onVotePoll = { post, index -> viewModel.votePoll(post, index) }
                    )
                }

                MainTab.WRITING -> {
                    WritingCornerScreen(
                        stories = allStories,
                        activeStory = activeStoryForReading,
                        onOpenStory = { viewModel.openStory(it) },
                        onCloseStory = { viewModel.closeStory() },
                        onCreateNewStory = { title, genre, summary, chaps ->
                            viewModel.createStory(title, genre, summary, chaps)
                        }
                    )
                }

                MainTab.AI_ASSIST -> {
                    AiAssistantScreen(
                        prompt = aiPrompt,
                        result = aiResult,
                        isLoading = aiLoading,
                        activeMode = aiMode,
                        onPromptChange = { viewModel.setAiPrompt(it) },
                        onModeChange = { viewModel.setAiMode(it) },
                        onGenerate = { viewModel.generateWithAi() },
                        onShareAsPost = { text ->
                            viewModel.publishPost(
                                content = text,
                                category = "Poetry",
                                moodTag = userProfile.currentMood.id,
                                isAnonymous = false
                            )
                            viewModel.setTab(MainTab.FEED)
                        }
                    )
                }

                MainTab.COMMUNITIES -> {
                    CommunitiesScreen(
                        communities = allCommunities,
                        onToggleJoin = { viewModel.toggleJoinCommunity(it) }
                    )
                }

                MainTab.CHALLENGES -> {
                    ChallengesScreen(
                        challenges = allChallenges,
                        userXp = userProfile.xp,
                        onSubmitChallenge = { challenge, text ->
                            viewModel.submitChallenge(challenge, text)
                        }
                    )
                }

                MainTab.MESSAGES -> {
                    MessagesScreen(
                        messages = allMessages,
                        activePartner = activeChatPartner,
                        onSelectPartner = { viewModel.selectChatPartner(it) },
                        onSendMessage = { viewModel.sendMessage(it) }
                    )
                }

                MainTab.PROFILE -> {
                    ProfileScreen(
                        profile = userProfile,
                        userPosts = filteredPosts,
                        savedPosts = filteredPosts.filter { it.isSaved },
                        stories = allStories,
                        onLikePost = { viewModel.toggleLike(it) },
                        onCommentPost = { viewModel.openComments(it) },
                        onSavePost = { viewModel.toggleSave(it) },
                        onFollowPost = { viewModel.toggleFollow(it) },
                        onVotePoll = { post, index -> viewModel.votePoll(post, index) }
                    )
                }
            }

            // Comments Bottom Sheet
            if (activePostForComments != null) {
                CommentSheet(
                    post = activePostForComments!!,
                    comments = commentsForActivePost,
                    onDismiss = { viewModel.closeComments() },
                    onAddComment = { text, isAnon -> viewModel.addComment(text, isAnon) }
                )
            }

            // Create Post Dialog
            if (showCreatePostDialog) {
                CreatePostDialog(
                    initialMood = userProfile.currentMood,
                    onDismiss = { viewModel.closeCreatePost() },
                    onPublish = { content, category, mood, isAnon, pollQ, pollOpts ->
                        viewModel.publishPost(
                            content = content,
                            category = category,
                            moodTag = mood,
                            isAnonymous = isAnon,
                            pollQuestion = pollQ,
                            pollOptions = pollOpts
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun QuickTabPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    tag: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Color(0xFF2E1A47) else MindoraSurfaceElevated)
            .border(1.dp, if (isSelected) AuraViolet else BorderSubtle, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .testTag(tag)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = if (isSelected) AuraVioletLight else TextSecondary,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

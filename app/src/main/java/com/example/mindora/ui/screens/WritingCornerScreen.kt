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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.mindora.data.model.StoryChapter
import com.example.mindora.data.model.StoryEntity
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritingCornerScreen(
    stories: List<StoryEntity>,
    activeStory: StoryEntity?,
    onOpenStory: (StoryEntity) -> Unit,
    onCloseStory: () -> Unit,
    onCreateNewStory: (title: String, genre: String, summary: String, chapters: List<Pair<String, String>>) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedGenre by remember { mutableStateOf("All") }
    var showCreateStorySheet by remember { mutableStateOf(false) }

    val genres = listOf(
        "All", "Poetry", "Shayari", "Short stories", "Romance", "Mystery",
        "Horror", "Motivation", "Life experiences", "Anime"
    )

    val filteredStories = if (selectedGenre == "All") {
        stories
    } else {
        stories.filter { it.genre.contains(selectedGenre, ignoreCase = true) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MindoraDarkBg)
    ) {
        // Top Banner / Writer's Portfolio Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MindoraSurface)
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "✍️", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Writing Corner",
                                style = MaterialTheme.typography.titleLarge,
                                color = TextPrimary
                            )
                        }
                        Text(
                            text = "A safe space for poets, novelists, and dreamers.",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary
                        )
                    }

                    // Write New Story Button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.linearGradient(listOf(AuraViolet, AuraIndigo))
                            )
                            .clickable { showCreateStorySheet = true }
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                            .testTag("create_new_story_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "New Story",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "New Story",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Writer Portfolio mini metrics
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MindoraSurfaceElevated)
                        .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WriterMetricItem("3 Stories", "Portfolio")
                    WriterMetricItem("7 Chapters", "Authored")
                    WriterMetricItem("5.2K", "Reads")
                    WriterMetricItem("Tier 1", "Monetization Ready")
                }
            }
        }

        // Genre Carousel
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MindoraSurface)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(genres) { genre ->
                val isSelected = selectedGenre == genre
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            if (isSelected) AuraViolet else MindoraSurfaceHighlight
                        )
                        .border(
                            1.dp,
                            if (isSelected) AuraVioletLight else BorderSubtle,
                            RoundedCornerShape(14.dp)
                        )
                        .clickable { selectedGenre = genre }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = genre,
                        color = if (isSelected) Color.White else TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }

        // Stories Feed
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(filteredStories, key = { it.id }) { story ->
                val chapters = parseChapters(story.chaptersJson)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MindoraSurfaceElevated)
                        .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
                        .clickable { onOpenStory(story) }
                        .padding(16.dp)
                        .testTag("story_card_${story.id}")
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF2E1A47))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = story.genre,
                                    color = AuraVioletLight,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = "Reads",
                                    tint = TextMuted,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${story.readCount}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextMuted
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = story.title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold
                            ),
                            color = TextPrimary
                        )

                        Text(
                            text = "by ${story.authorName}",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = story.summary,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            maxLines = 2
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Chapters preview chips
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                chapters.take(3).forEach { chap ->
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(MindoraSurfaceHighlight)
                                            .padding(horizontal = 6.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = "Ch. ${chap.chapterNumber}",
                                            fontSize = 10.sp,
                                            color = TextSecondary
                                        )
                                    }
                                }
                                if (chapters.size > 3) {
                                    Text(
                                        text = "+${chapters.size - 3} more",
                                        fontSize = 10.sp,
                                        color = TextMuted
                                    )
                                }
                            }

                            // Read button
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        Brush.linearGradient(listOf(AuraViolet, AuraCyan))
                                    )
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Read Chapters 📖",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Story Reader Modal
    if (activeStory != null) {
        StoryReaderModal(
            story = activeStory,
            onDismiss = onCloseStory
        )
    }

    // Create New Story Sheet
    if (showCreateStorySheet) {
        CreateStoryModal(
            onDismiss = { showCreateStorySheet = false },
            onCreate = { title, genre, summary, chaps ->
                onCreateNewStory(title, genre, summary, chaps)
                showCreateStorySheet = false
            }
        )
    }
}

@Composable
private fun WriterMetricItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AuraVioletLight
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TextMuted,
            fontSize = 10.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StoryReaderModal(
    story: StoryEntity,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val chapters = parseChapters(story.chaptersJson)
    var currentChapterIndex by remember { mutableIntStateOf(0) }
    val currentChapter = chapters.getOrElse(currentChapterIndex) { chapters.firstOrNull() }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MindoraSurface,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .navigationBarsPadding()
                .padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = story.title,
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Serif),
                        color = TextPrimary
                    )
                    Text(
                        text = "by ${story.authorName} • ${story.genre}",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Chapter Switcher Tabs
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(chapters.size) { idx ->
                    val chap = chapters[idx]
                    val isSelected = currentChapterIndex == idx
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) AuraViolet else MindoraSurfaceElevated)
                            .border(1.dp, if (isSelected) AuraVioletLight else BorderSubtle, RoundedCornerShape(12.dp))
                            .clickable { currentChapterIndex = idx }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Chapter ${chap.chapterNumber}",
                            color = if (isSelected) Color.White else TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Chapter Content Reader View
            if (currentChapter != null) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0F1020))
                        .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = currentChapter.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily.Serif,
                            color = AuraVioletLight
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${currentChapter.readTimeMinutes} min read",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = currentChapter.content,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontSize = 17.sp,
                            lineHeight = 28.sp
                        ),
                        color = TextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Navigation between chapters
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (currentChapterIndex > 0) MindoraSurfaceHighlight else Color.Transparent)
                        .clickable(enabled = currentChapterIndex > 0) {
                            currentChapterIndex--
                        }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "← Previous",
                        color = if (currentChapterIndex > 0) TextPrimary else TextMuted,
                        fontSize = 12.sp
                    )
                }

                Text(
                    text = "${currentChapterIndex + 1} of ${chapters.size}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (currentChapterIndex < chapters.size - 1) AuraViolet else Color.Transparent)
                        .clickable(enabled = currentChapterIndex < chapters.size - 1) {
                            currentChapterIndex++
                        }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Next →",
                        color = if (currentChapterIndex < chapters.size - 1) Color.White else TextMuted,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateStoryModal(
    onDismiss: () -> Unit,
    onCreate: (title: String, genre: String, summary: String, chapters: List<Pair<String, String>>) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var title by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("Poetry") }
    var summary by remember { mutableStateOf("") }
    var chapter1Title by remember { mutableStateOf("Chapter 1: The First Thought") }
    var chapter1Content by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MindoraSurface,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .navigationBarsPadding()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Craft a New Story / Chapter",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Story Title", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = genre,
                onValueChange = { genre = it },
                label = { Text("Genre (Poetry, Romance, Mystery, Shayari...)", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = summary,
                onValueChange = { summary = it },
                label = { Text("Short Summary / Premise", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = chapter1Title,
                onValueChange = { chapter1Title = it },
                label = { Text("First Chapter Title", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = chapter1Content,
                onValueChange = { chapter1Content = it },
                label = { Text("Chapter Words & Verse...", color = TextSecondary) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(23.dp))
                    .background(
                        if (title.isNotBlank() && chapter1Content.isNotBlank()) {
                            Brush.linearGradient(listOf(AuraViolet, AuraCyan))
                        } else {
                            Brush.linearGradient(listOf(MindoraSurfaceHighlight, MindoraSurfaceHighlight))
                        }
                    )
                    .clickable(enabled = title.isNotBlank() && chapter1Content.isNotBlank()) {
                        onCreate(
                            title.trim(),
                            genre.trim(),
                            summary.trim().ifBlank { "A contemplative creative work on MINDORA." },
                            listOf(chapter1Title.trim() to chapter1Content.trim())
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Publish to Writing Corner ✨",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun parseChapters(encoded: String): List<StoryChapter> {
    return encoded.split("##").mapIndexedNotNull { index, raw ->
        val parts = raw.split("::")
        if (parts.size >= 2) {
            StoryChapter(
                chapterNumber = index + 1,
                title = parts[0],
                content = parts[1],
                readTimeMinutes = parts.getOrNull(2)?.toIntOrNull() ?: 4
            )
        } else null
    }
}

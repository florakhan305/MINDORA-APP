package com.example.mindora.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.mindora.data.remote.GeminiApiClient
import com.example.ui.theme.AuraAnonymousBrush
import com.example.ui.theme.AuraCyan
import com.example.ui.theme.AuraIndigo
import com.example.ui.theme.AuraViolet
import com.example.ui.theme.AuraVioletLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.MindoraSurface
import com.example.ui.theme.MindoraSurfaceElevated
import com.example.ui.theme.MindoraSurfaceHighlight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreatePostDialog(
    initialMood: MoodType,
    onDismiss: () -> Unit,
    onPublish: (
        content: String,
        category: String,
        moodTag: String?,
        isAnonymous: Boolean,
        pollQuestion: String?,
        pollOptions: List<String>?
    ) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    var content by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Thoughts") }
    var selectedMood by remember { mutableStateOf<MoodType?>(initialMood) }
    var isAnonymous by remember { mutableStateOf(false) }

    // Poll options
    var isPollEnabled by remember { mutableStateOf(false) }
    var pollQuestion by remember { mutableStateOf("") }
    var option1 by remember { mutableStateOf("") }
    var option2 by remember { mutableStateOf("") }

    // AI refinement
    var isAiRefining by remember { mutableStateOf(false) }
    var aiRefinedText by remember { mutableStateOf<String?>(null) }

    val categories = listOf("Thoughts", "Poetry", "Shayari", "Stories", "Quotes", "Questions", "Motivation")

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
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Express What's in Your Mind",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel",
                        tint = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Anonymous Mode Toggle Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .then(
                        if (isAnonymous) Modifier.background(AuraAnonymousBrush)
                        else Modifier.background(MindoraSurfaceElevated)
                    )
                    .border(
                        1.dp,
                        if (isAnonymous) AuraVioletLight else BorderSubtle,
                        RoundedCornerShape(14.dp)
                    )
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(if (isAnonymous) Color(0xFF581C87) else MindoraSurfaceHighlight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isAnonymous) Icons.Default.VisibilityOff else Icons.Default.Shield,
                                contentDescription = "Mode",
                                tint = if (isAnonymous) Color(0xFFDDD6FE) else AuraCyan,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = if (isAnonymous) "Anonymous Mode Activated" else "Posting as Akira",
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 14.sp,
                                color = if (isAnonymous) Color.White else TextPrimary
                            )
                            Text(
                                text = if (isAnonymous) "Displays as 'Anonymous Mind #XXXX' • Identity shielded" else "Your profile & bio will be visible",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isAnonymous) Color(0xFFC4B5FD) else TextMuted
                            )
                        }
                    }

                    Switch(
                        checked = isAnonymous,
                        onCheckedChange = { isAnonymous = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = AuraViolet,
                            uncheckedThumbColor = TextMuted,
                            uncheckedTrackColor = MindoraSurfaceHighlight
                        ),
                        modifier = Modifier.testTag("anonymous_mode_switch")
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Category Picker
            Text(
                text = "Expression Category",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) AuraViolet else MindoraSurfaceElevated
                            )
                            .border(
                                1.dp,
                                if (isSelected) AuraVioletLight else BorderSubtle,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) Color.White else TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Mood Selector
            Text(
                text = "Current Mind Aura (Mood)",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                MoodType.entries.forEach { mood ->
                    val isSelected = selectedMood == mood
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) Color(mood.colorHex).copy(alpha = 0.25f) else MindoraSurfaceElevated
                            )
                            .border(
                                1.dp,
                                if (isSelected) Color(mood.colorHex) else BorderSubtle,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { selectedMood = mood }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${mood.emoji} ${mood.label}",
                            color = if (isSelected) Color(mood.colorHex) else TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Content Text Field
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                placeholder = {
                    Text(
                        "What is in your mind right now? Share unfiltered, in verse, or in quiet honesty...",
                        color = TextMuted,
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .testTag("create_post_content_field"),
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

            // AI Writing Assistant Button
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.linearGradient(listOf(Color(0xFF3B1768), Color(0xFF1E1B4B)))
                        )
                        .border(1.dp, AuraViolet, RoundedCornerShape(20.dp))
                        .clickable(enabled = !isAiRefining && content.isNotBlank()) {
                            isAiRefining = true
                            coroutineScope.launch {
                                val result = GeminiApiClient.generateAiContent(
                                    prompt = "Transform this thought into beautiful, evocative poetry/prose for MINDORA:\n\"$content\"",
                                    systemInstruction = "You are MINDORA's Poetic AI. Turn the user's thought into an emotionally resonant, gentle 2-3 line quote or verse."
                                )
                                isAiRefining = false
                                aiRefinedText = result.getOrNull()
                            }
                        }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .testTag("ai_refine_button")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (isAiRefining) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(14.dp),
                                color = AuraVioletLight,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "AI Refine",
                                tint = AuraVioletLight,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isAiRefining) "Refining with Mindora AI..." else "✨ Refine with Mindora AI",
                            color = AuraVioletLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Poll Toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { isPollEnabled = !isPollEnabled }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Poll,
                        contentDescription = "Poll",
                        tint = if (isPollEnabled) AuraCyan else TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isPollEnabled) "Poll Added" else "+ Add Poll",
                        color = if (isPollEnabled) AuraCyan else TextMuted,
                        fontSize = 12.sp
                    )
                }
            }

            // AI Suggestion Preview Card
            AnimatedVisibility(visible = aiRefinedText != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1E173C))
                        .border(1.dp, AuraViolet, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "✨ Mindora AI Suggestion:",
                            style = MaterialTheme.typography.labelSmall,
                            color = AuraVioletLight,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(AuraViolet)
                                .clickable {
                                    content = aiRefinedText ?: content
                                    aiRefinedText = null
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Use This",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = aiRefinedText.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        fontSize = 13.sp
                    )
                }
            }

            // Optional Poll Fields
            AnimatedVisibility(visible = isPollEnabled) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MindoraSurfaceElevated)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Poll Question & Options",
                        style = MaterialTheme.typography.labelSmall,
                        color = AuraCyan
                    )
                    OutlinedTextField(
                        value = pollQuestion,
                        onValueChange = { pollQuestion = it },
                        placeholder = { Text("Ask the community...", color = TextMuted, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    OutlinedTextField(
                        value = option1,
                        onValueChange = { option1 = it },
                        placeholder = { Text("Option 1", color = TextMuted, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    OutlinedTextField(
                        value = option2,
                        onValueChange = { option2 = it },
                        placeholder = { Text("Option 2", color = TextMuted, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Publish Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        if (content.isNotBlank()) {
                            Brush.linearGradient(listOf(AuraViolet, AuraIndigo, AuraCyan))
                        } else {
                            Brush.linearGradient(listOf(MindoraSurfaceHighlight, MindoraSurfaceHighlight))
                        }
                    )
                    .clickable(enabled = content.isNotBlank()) {
                        val pollOpts = if (isPollEnabled && option1.isNotBlank() && option2.isNotBlank()) {
                            listOf(option1.trim(), option2.trim())
                        } else null

                        val finalPollQ = if (pollOpts != null) pollQuestion.ifBlank { "Community Poll" } else null

                        onPublish(
                            content.trim(),
                            selectedCategory,
                            selectedMood?.id,
                            isAnonymous,
                            finalPollQ,
                            pollOpts
                        )
                    }
                    .testTag("publish_post_button"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isAnonymous) "Publish Anonymously 🌙" else "Share to MINDORA ✨",
                    color = if (content.isNotBlank()) Color.White else TextMuted,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

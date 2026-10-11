package com.example.mindora.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindora.data.model.MessageEntity
import com.example.ui.theme.AuraAnonymousBrush
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
fun MessagesScreen(
    messages: List<MessageEntity>,
    activePartner: String?,
    onSelectPartner: (String) -> Unit,
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var inputMessage by remember { mutableStateOf("") }
    var showSafetyMenu by remember { mutableStateOf(false) }

    val partners = listOf(
        "Forida (Founder)",
        "Anonymous Mind #4821",
        "Elysia (Safe Support)"
    )

    val currentPartner = activePartner ?: partners.first()
    val partnerMessages = messages.filter { it.conversationPartner == currentPartner }
        .sortedBy { it.timestamp }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MindoraDarkBg)
    ) {
        // Chat Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MindoraSurface)
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(
                                if (currentPartner.contains("Anonymous")) AuraAnonymousBrush
                                else Brush.linearGradient(listOf(AuraViolet, AuraCyan))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (currentPartner.contains("Anonymous")) {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = "Anon",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        } else {
                            Text(
                                text = currentPartner.take(1),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = currentPartner,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = if (currentPartner.contains("Founder")) "Founder • Direct Haven" else "Safe 1-on-1 Sanctuary",
                            style = MaterialTheme.typography.labelSmall,
                            color = AuraVioletLight
                        )
                    }
                }

                // Safety Options Menu
                Box {
                    IconButton(onClick = { showSafetyMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Safety Controls",
                            tint = TextSecondary
                        )
                    }

                    DropdownMenu(
                        expanded = showSafetyMenu,
                        onDismissRequest = { showSafetyMenu = false },
                        modifier = Modifier.background(MindoraSurfaceElevated)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Mute Notifications", color = TextPrimary, fontSize = 13.sp) },
                            leadingIcon = { Icon(Icons.Default.VolumeMute, null, tint = TextSecondary, modifier = Modifier.size(16.dp)) },
                            onClick = {
                                showSafetyMenu = false
                                Toast.makeText(context, "Conversation muted", Toast.LENGTH_SHORT).show()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Block User", color = TextPrimary, fontSize = 13.sp) },
                            leadingIcon = { Icon(Icons.Default.Block, null, tint = Color(0xFFEF4444), modifier = Modifier.size(16.dp)) },
                            onClick = {
                                showSafetyMenu = false
                                Toast.makeText(context, "User blocked for safety", Toast.LENGTH_SHORT).show()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Report for Support", color = TextPrimary, fontSize = 13.sp) },
                            leadingIcon = { Icon(Icons.Default.Flag, null, tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp)) },
                            onClick = {
                                showSafetyMenu = false
                                Toast.makeText(context, "Report received. Safety moderators notified.", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }

        // Conversation Selector Pills
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MindoraSurface)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            partners.forEach { partner ->
                val isSelected = currentPartner == partner
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) MindoraSurfaceHighlight else MindoraSurfaceElevated)
                        .border(1.dp, if (isSelected) AuraViolet else BorderSubtle, RoundedCornerShape(12.dp))
                        .clickable { onSelectPartner(partner) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = partner.split(" ").first(),
                        color = if (isSelected) Color.White else TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }

        // Safety Notice Banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF14162B))
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Safe",
                tint = AuraCyan,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "End-to-end respectful communication. Harassment is strictly filtered.",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFFA5F3FC),
                fontSize = 11.sp
            )
        }

        // Messages List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(partnerMessages, key = { it.id }) { msg ->
                val isMe = msg.senderIsMe
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.78f)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 14.dp,
                                    topEnd = 14.dp,
                                    bottomStart = if (isMe) 14.dp else 2.dp,
                                    bottomEnd = if (isMe) 2.dp else 14.dp
                                )
                            )
                            .background(
                                if (isMe) Brush.linearGradient(listOf(AuraViolet, AuraIndigo))
                                else Brush.linearGradient(listOf(MindoraSurfaceElevated, MindoraSurfaceElevated))
                            )
                            .border(
                                1.dp,
                                if (isMe) Color.Transparent else BorderSubtle,
                                RoundedCornerShape(14.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = msg.text,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Supportive Mind",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isMe) Color(0xFFDDD6FE) else TextMuted,
                                fontSize = 9.sp
                            )
                        }
                    }
                }
            }
        }

        // Input Field Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MindoraSurfaceElevated)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputMessage,
                onValueChange = { inputMessage = it },
                placeholder = {
                    Text(
                        "Send a supportive message...",
                        color = TextMuted,
                        fontSize = 13.sp
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input_field"),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AuraViolet,
                    unfocusedBorderColor = BorderSubtle,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = AuraViolet,
                    focusedContainerColor = MindoraSurface,
                    unfocusedContainerColor = MindoraSurface
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (inputMessage.isNotBlank()) {
                        onSendMessage(inputMessage.trim())
                        inputMessage = ""
                    }
                },
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(listOf(AuraViolet, AuraCyan))
                    )
                    .testTag("send_chat_message_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

package com.example.mindora.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class MoodType(
    val id: String,
    val emoji: String,
    val label: String,
    val tagline: String,
    val colorHex: Long
) {
    HAPPY("HAPPY", "😊", "Happy", "Basking in light & gratitude", 0xFFF59E0BL),
    SAD("SAD", "😔", "Sad", "Seeking quiet comfort", 0xFF64748BL),
    ANGRY("ANGRY", "😡", "Angry", "Releasing heavy tension", 0xFFEF4444L),
    TIRED("TIRED", "😴", "Tired", "Resting an exhausted soul", 0xFF8B5CF6L),
    LOST("LOST", "😶", "Lost", "Looking for my north star", 0xFF06B6D4L),
    LOVED("LOVED", "❤️", "Loved", "Feeling cherished & safe", 0xFFEC4899L),
    MOTIVATED("MOTIVATED", "🔥", "Motivated", "Ignited with creative flame", 0xFFF97316L),
    LONELY("LONELY", "🌧️", "Lonely", "Yearning for kindred minds", 0xFF6366F1L);

    companion object {
        fun fromId(id: String?): MoodType {
            return entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: LONELY
        }
    }
}

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val authorName: String,
    val authorHandle: String,
    val isAnonymous: Boolean,
    val anonymousNumber: Int,
    val content: String,
    val category: String, // "Thoughts", "Poetry", "Shayari", "Stories", "Quotes", "Questions"
    val moodTag: String?,
    val likesCount: Int,
    val commentsCount: Int,
    val sharesCount: Int,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false,
    val isFollowing: Boolean = false,
    val timestamp: Long,
    val pollQuestion: String? = null,
    val pollOptionsJson: String? = null, // e.g. "Night walks||Silent music||Reading old notes"
    val pollVotesJson: String? = null,   // e.g. "45||72||31"
    val userPollVotedIndex: Int = -1
)

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: String,
    val postId: String,
    val authorName: String,
    val isAnonymous: Boolean,
    val content: String,
    val timestamp: Long,
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    val isSupportive: Boolean = true
)

@Entity(tableName = "stories")
data class StoryEntity(
    @PrimaryKey val id: String,
    val title: String,
    val authorName: String,
    val genre: String, // Poetry, Shayari, Short stories, Romance, Mystery, Horror, Anime
    val summary: String,
    val chaptersJson: String, // Delimited or JSON format for chapter titles and content
    val readCount: Int,
    val likesCount: Int,
    val isSaved: Boolean = false
)

@Entity(tableName = "communities")
data class CommunityEntity(
    @PrimaryKey val id: String,
    val name: String,
    val icon: String,
    val description: String,
    val memberCount: Int,
    val isJoined: Boolean,
    val pinnedRule: String
)

@Entity(tableName = "challenges")
data class ChallengeEntity(
    @PrimaryKey val id: String,
    val dayNumber: Int,
    val title: String,
    val prompt: String,
    val xpReward: Int,
    val badgeName: String,
    val isCompleted: Boolean,
    val userSubmission: String? = null
)

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val conversationPartner: String,
    val isAnonymousPartner: Boolean,
    val senderIsMe: Boolean,
    val text: String,
    val timestamp: Long,
    val isRequest: Boolean = false
)

data class StoryChapter(
    val chapterNumber: Int,
    val title: String,
    val content: String,
    val readTimeMinutes: Int
)

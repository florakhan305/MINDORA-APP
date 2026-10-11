package com.example.mindora.data.repository

import com.example.mindora.data.local.MindoraDatabase
import com.example.mindora.data.model.ChallengeEntity
import com.example.mindora.data.model.CommentEntity
import com.example.mindora.data.model.CommunityEntity
import com.example.mindora.data.model.MessageEntity
import com.example.mindora.data.model.MoodType
import com.example.mindora.data.model.PostEntity
import com.example.mindora.data.model.StoryEntity
import com.example.mindora.data.remote.GeminiApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import kotlin.random.Random

data class UserProfileState(
    val name: String = "Akira",
    val handle: String = "@akira_writes",
    val title: String = "Writer | Student | Anime lover 🌙",
    val bio: String = "Exploring the quiet corners of the human mind. Believer in words that heal. Proud early soul on MINDORA.",
    val currentMood: MoodType = MoodType.LONELY,
    val mindScore: Int = 890,
    val xp: Int = 1450,
    val levelName: String = "Resonant Mind Level 4",
    val postsCount: Int = 124,
    val followersCount: Int = 2400,
    val followingCount: Int = 310
)

class MindoraRepository(
    private val database: MindoraDatabase
) {
    private val _userProfile = MutableStateFlow(UserProfileState())
    val userProfile = _userProfile.asStateFlow()

    fun getAllPosts(): Flow<List<PostEntity>> = database.postDao().getAllPosts()
    fun getAnonymousPosts(): Flow<List<PostEntity>> = database.postDao().getAnonymousPosts()
    fun getSavedPosts(): Flow<List<PostEntity>> = database.postDao().getSavedPosts()
    fun getPostsByMood(mood: String): Flow<List<PostEntity>> = database.postDao().getPostsByMood(mood)
    fun getCommentsForPost(postId: String): Flow<List<CommentEntity>> = database.commentDao().getCommentsForPost(postId)
    fun getAllStories(): Flow<List<StoryEntity>> = database.storyDao().getAllStories()
    fun getSavedStories(): Flow<List<StoryEntity>> = database.storyDao().getSavedStories()
    fun getAllCommunities(): Flow<List<CommunityEntity>> = database.communityDao().getAllCommunities()
    fun getAllChallenges(): Flow<List<ChallengeEntity>> = database.challengeDao().getAllChallenges()
    fun getAllMessages(): Flow<List<MessageEntity>> = database.messageDao().getAllMessages()
    fun getMessagesForConversation(partner: String): Flow<List<MessageEntity>> = database.messageDao().getMessagesForConversation(partner)

    suspend fun createPost(
        content: String,
        category: String,
        moodTag: String?,
        isAnonymous: Boolean,
        pollQuestion: String? = null,
        pollOptions: List<String>? = null
    ) {
        val anonNum = if (isAnonymous) Random.nextInt(1000, 9999) else 0
        val authorName = if (isAnonymous) "Anonymous Mind #$anonNum" else _userProfile.value.name
        val authorHandle = if (isAnonymous) "@anon_$anonNum" else _userProfile.value.handle

        val pollOptionsJson = pollOptions?.joinToString("||")
        val pollVotesJson = pollOptions?.map { 0 }?.joinToString("||")

        val newPost = PostEntity(
            id = "post-" + UUID.randomUUID().toString().take(8),
            authorName = authorName,
            authorHandle = authorHandle,
            isAnonymous = isAnonymous,
            anonymousNumber = anonNum,
            content = content,
            category = category,
            moodTag = moodTag,
            likesCount = 1,
            commentsCount = 0,
            sharesCount = 0,
            isLiked = false,
            isSaved = false,
            isFollowing = false,
            timestamp = System.currentTimeMillis(),
            pollQuestion = pollQuestion,
            pollOptionsJson = pollOptionsJson,
            pollVotesJson = pollVotesJson
        )
        database.postDao().insertPost(newPost)

        // Boost MindScore for sharing genuine vulnerability/creativity
        _userProfile.value = _userProfile.value.copy(
            mindScore = _userProfile.value.mindScore + 15,
            xp = _userProfile.value.xp + 25
        )
    }

    suspend fun toggleLike(post: PostEntity) {
        val newIsLiked = !post.isLiked
        val newLikesCount = if (newIsLiked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
        database.postDao().updatePost(
            post.copy(isLiked = newIsLiked, likesCount = newLikesCount)
        )
        if (newIsLiked) {
            _userProfile.value = _userProfile.value.copy(
                mindScore = _userProfile.value.mindScore + 2
            )
        }
    }

    suspend fun toggleSave(post: PostEntity) {
        val newIsSaved = !post.isSaved
        database.postDao().updatePost(post.copy(isSaved = newIsSaved))
    }

    suspend fun toggleFollow(post: PostEntity) {
        val newIsFollowing = !post.isFollowing
        database.postDao().updatePost(post.copy(isFollowing = newIsFollowing))
    }

    suspend fun votePoll(post: PostEntity, optionIndex: Int) {
        if (post.pollOptionsJson == null || post.pollVotesJson == null) return
        val votes = post.pollVotesJson.split("||").map { it.toIntOrNull() ?: 0 }.toMutableList()
        if (optionIndex in votes.indices) {
            votes[optionIndex] = votes[optionIndex] + 1
            database.postDao().updatePost(
                post.copy(
                    pollVotesJson = votes.joinToString("||"),
                    userPollVotedIndex = optionIndex
                )
            )
        }
    }

    suspend fun addComment(postId: String, text: String, isAnonymous: Boolean) {
        val anonNum = if (isAnonymous) Random.nextInt(1000, 9999) else 0
        val authorName = if (isAnonymous) "Kind Aura #$anonNum" else _userProfile.value.name

        val newComment = CommentEntity(
            id = "c-" + UUID.randomUUID().toString().take(8),
            postId = postId,
            authorName = authorName,
            isAnonymous = isAnonymous,
            content = text,
            timestamp = System.currentTimeMillis(),
            likesCount = 0,
            isSupportive = true
        )
        database.commentDao().insertComment(newComment)

        val post = database.postDao().getPostById(postId)
        if (post != null) {
            database.postDao().updatePost(post.copy(commentsCount = post.commentsCount + 1))
        }

        // Rewarding supportive comments
        _userProfile.value = _userProfile.value.copy(
            mindScore = _userProfile.value.mindScore + 10,
            xp = _userProfile.value.xp + 20
        )
    }

    suspend fun toggleJoinCommunity(community: CommunityEntity) {
        val newJoined = !community.isJoined
        val newCount = if (newJoined) community.memberCount + 1 else (community.memberCount - 1).coerceAtLeast(0)
        database.communityDao().updateCommunity(
            community.copy(isJoined = newJoined, memberCount = newCount)
        )
    }

    suspend fun submitChallenge(challenge: ChallengeEntity, submissionText: String) {
        database.challengeDao().updateChallenge(
            challenge.copy(
                isCompleted = true,
                userSubmission = submissionText
            )
        )
        _userProfile.value = _userProfile.value.copy(
            mindScore = _userProfile.value.mindScore + 50,
            xp = _userProfile.value.xp + challenge.xpReward
        )
    }

    suspend fun sendMessage(partner: String, text: String, isPartnerAnonymous: Boolean = false) {
        val newMsg = MessageEntity(
            id = "msg-" + UUID.randomUUID().toString().take(8),
            conversationPartner = partner,
            isAnonymousPartner = isPartnerAnonymous,
            senderIsMe = true,
            text = text,
            timestamp = System.currentTimeMillis()
        )
        database.messageDao().insertMessage(newMsg)
    }

    suspend fun createStory(title: String, genre: String, summary: String, chapters: List<Pair<String, String>>) {
        val chaptersEncoded = chapters.joinToString("##") { (chapTitle, chapContent) ->
            "$chapTitle::$chapContent::5"
        }
        val story = StoryEntity(
            id = "story-" + UUID.randomUUID().toString().take(8),
            title = title,
            authorName = _userProfile.value.name,
            genre = genre,
            summary = summary,
            chaptersJson = chaptersEncoded,
            readCount = 1,
            likesCount = 0,
            isSaved = false
        )
        database.storyDao().insertStory(story)
        _userProfile.value = _userProfile.value.copy(
            mindScore = _userProfile.value.mindScore + 30,
            xp = _userProfile.value.xp + 50
        )
    }

    fun updateUserMood(mood: MoodType) {
        _userProfile.value = _userProfile.value.copy(currentMood = mood)
    }

    suspend fun consultGeminiAi(prompt: String, mode: String): Result<String> {
        val systemInstruction = when (mode) {
            "POETIC" -> "You are MINDORA's Poetic Alchemist. Transform the user's raw, unvarnished thought into deep, evocative poetry, shayari, or literary prose. Keep it authentic and emotionally moving. Give 1-2 beautifully crafted alternatives."
            "REFLECT" -> "You are MINDORA's Empathetic Mind Companion. Offer non-judgmental, gentle, and grounding reflection to someone who shared what is in their heart. Do not give clinical diagnosis; provide human warmth and safe presence."
            "STORY" -> "You are MINDORA's Creative Writing Muse. Generate an imaginative narrative seed, compelling chapter outline, or mystery hook based on the user's premise."
            else -> "You are MINDORA AI, a calming, poetic presence for thoughtful writers and introspective souls."
        }
        return GeminiApiClient.generateAiContent(prompt, systemInstruction)
    }
}

package com.example.mindora.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindora.data.local.MindoraDatabase
import com.example.mindora.data.model.ChallengeEntity
import com.example.mindora.data.model.CommentEntity
import com.example.mindora.data.model.CommunityEntity
import com.example.mindora.data.model.MessageEntity
import com.example.mindora.data.model.MoodType
import com.example.mindora.data.model.PostEntity
import com.example.mindora.data.model.StoryEntity
import com.example.mindora.data.repository.MindoraRepository
import com.example.mindora.data.repository.UserProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainTab {
    FEED,
    MINDSPACE,
    WRITING,
    AI_ASSIST,
    COMMUNITIES,
    CHALLENGES,
    MESSAGES,
    PROFILE
}

enum class FeedFilter {
    FOR_YOU,
    FOLLOWING,
    EXPLORE,
    ANONYMOUS
}

class MindoraViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MindoraRepository

    init {
        val database = MindoraDatabase.getDatabase(application, viewModelScope)
        repository = MindoraRepository(database)
    }

    // Navigation & Tabs
    private val _currentTab = MutableStateFlow(MainTab.FEED)
    val currentTab = _currentTab.asStateFlow()

    private val _feedFilter = MutableStateFlow(FeedFilter.FOR_YOU)
    val feedFilter = _feedFilter.asStateFlow()

    private val _categoryFilter = MutableStateFlow<String?>(null)
    val categoryFilter = _categoryFilter.asStateFlow()

    // User Profile
    val userProfile: StateFlow<UserProfileState> = repository.userProfile

    // Raw Flows from DB
    private val allPosts = repository.getAllPosts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allStories = repository.getAllStories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCommunities = repository.getAllCommunities()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allChallenges = repository.getAllChallenges()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allMessages = repository.getAllMessages()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active Interactive States
    private val _activePostForComments = MutableStateFlow<PostEntity?>(null)
    val activePostForComments = _activePostForComments.asStateFlow()

    private val _commentsForActivePost = MutableStateFlow<List<CommentEntity>>(emptyList())
    val commentsForActivePost = _commentsForActivePost.asStateFlow()

    private val _activeStoryForReading = MutableStateFlow<StoryEntity?>(null)
    val activeStoryForReading = _activeStoryForReading.asStateFlow()

    private val _activeChatPartner = MutableStateFlow<String?>("Forida (Founder)")
    val activeChatPartner = _activeChatPartner.asStateFlow()

    private val _showCreatePostDialog = MutableStateFlow(false)
    val showCreatePostDialog = _showCreatePostDialog.asStateFlow()

    // Filtered Feed Flow
    val filteredPosts: StateFlow<List<PostEntity>> = combine(
        allPosts,
        _feedFilter,
        _categoryFilter
    ) { posts, filter, category ->
        var list = when (filter) {
            FeedFilter.FOR_YOU -> posts
            FeedFilter.FOLLOWING -> posts.filter { it.isFollowing }
            FeedFilter.EXPLORE -> posts.sortedByDescending { it.likesCount + it.commentsCount }
            FeedFilter.ANONYMOUS -> posts.filter { it.isAnonymous }
        }
        if (category != null && category != "All") {
            list = list.filter { it.category.equals(category, ignoreCase = true) }
        }
        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // AI Assistant state
    private val _aiPrompt = MutableStateFlow("")
    val aiPrompt = _aiPrompt.asStateFlow()

    private val _aiResult = MutableStateFlow("")
    val aiResult = _aiResult.asStateFlow()

    private val _aiLoading = MutableStateFlow(false)
    val aiLoading = _aiLoading.asStateFlow()

    private val _aiMode = MutableStateFlow("POETIC") // POETIC, REFLECT, STORY
    val aiMode = _aiMode.asStateFlow()

    // Navigation setters
    fun setTab(tab: MainTab) {
        _currentTab.value = tab
    }

    fun setFeedFilter(filter: FeedFilter) {
        _feedFilter.value = filter
    }

    fun setCategoryFilter(category: String?) {
        _categoryFilter.value = category
    }

    fun setMood(mood: MoodType) {
        repository.updateUserMood(mood)
    }

    // Post Interactions
    fun toggleLike(post: PostEntity) {
        viewModelScope.launch {
            repository.toggleLike(post)
        }
    }

    fun toggleSave(post: PostEntity) {
        viewModelScope.launch {
            repository.toggleSave(post)
        }
    }

    fun toggleFollow(post: PostEntity) {
        viewModelScope.launch {
            repository.toggleFollow(post)
        }
    }

    fun votePoll(post: PostEntity, optionIndex: Int) {
        viewModelScope.launch {
            repository.votePoll(post, optionIndex)
        }
    }

    fun openComments(post: PostEntity) {
        _activePostForComments.value = post
        viewModelScope.launch {
            repository.getCommentsForPost(post.id).collect { comments ->
                _commentsForActivePost.value = comments
            }
        }
    }

    fun closeComments() {
        _activePostForComments.value = null
        _commentsForActivePost.value = emptyList()
    }

    fun addComment(text: String, isAnonymous: Boolean) {
        val post = _activePostForComments.value ?: return
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.addComment(post.id, text, isAnonymous)
        }
    }

    fun createStory(title: String, genre: String, summary: String, chapters: List<Pair<String, String>>) {
        viewModelScope.launch {
            repository.createStory(title, genre, summary, chapters)
        }
    }

    // Story Interactions
    fun openStory(story: StoryEntity) {
        _activeStoryForReading.value = story
    }

    fun closeStory() {
        _activeStoryForReading.value = null
    }

    // Community
    fun toggleJoinCommunity(community: CommunityEntity) {
        viewModelScope.launch {
            repository.toggleJoinCommunity(community)
        }
    }

    // Challenge
    fun submitChallenge(challenge: ChallengeEntity, text: String) {
        viewModelScope.launch {
            repository.submitChallenge(challenge, text)
        }
    }

    // Chat
    fun selectChatPartner(partner: String) {
        _activeChatPartner.value = partner
    }

    fun sendMessage(text: String) {
        val partner = _activeChatPartner.value ?: return
        if (text.isBlank()) return
        viewModelScope.launch {
            val isAnon = partner.startsWith("Anonymous") || partner.startsWith("Kind Aura")
            repository.sendMessage(partner, text, isPartnerAnonymous = isAnon)
        }
    }

    // Create Post
    fun openCreatePost() {
        _showCreatePostDialog.value = true
    }

    fun closeCreatePost() {
        _showCreatePostDialog.value = false
    }

    fun publishPost(
        content: String,
        category: String,
        moodTag: String?,
        isAnonymous: Boolean,
        pollQuestion: String? = null,
        pollOptions: List<String>? = null
    ) {
        viewModelScope.launch {
            repository.createPost(
                content = content,
                category = category,
                moodTag = moodTag,
                isAnonymous = isAnonymous,
                pollQuestion = pollQuestion,
                pollOptions = pollOptions
            )
            _showCreatePostDialog.value = false
        }
    }

    // AI actions
    fun setAiPrompt(text: String) {
        _aiPrompt.value = text
    }

    fun setAiMode(mode: String) {
        _aiMode.value = mode
    }

    fun generateWithAi() {
        val prompt = _aiPrompt.value.trim()
        if (prompt.isBlank()) return
        _aiLoading.value = true
        _aiResult.value = ""
        viewModelScope.launch {
            val result = repository.consultGeminiAi(prompt, _aiMode.value)
            _aiLoading.value = false
            _aiResult.value = result.getOrDefault("Could not connect to mind resonance. Please try again.")
        }
    }
}

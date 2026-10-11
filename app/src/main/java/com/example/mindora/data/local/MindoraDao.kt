package com.example.mindora.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mindora.data.model.ChallengeEntity
import com.example.mindora.data.model.CommentEntity
import com.example.mindora.data.model.CommunityEntity
import com.example.mindora.data.model.MessageEntity
import com.example.mindora.data.model.PostEntity
import com.example.mindora.data.model.StoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY timestamp DESC")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE isAnonymous = 1 ORDER BY timestamp DESC")
    fun getAnonymousPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE isSaved = 1 ORDER BY timestamp DESC")
    fun getSavedPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE moodTag = :mood ORDER BY timestamp DESC")
    fun getPostsByMood(mood: String): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE id = :id LIMIT 1")
    suspend fun getPostById(id: String): PostEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Update
    suspend fun updatePost(post: PostEntity)

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun deletePost(id: String)
}

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY timestamp ASC")
    fun getCommentsForPost(postId: String): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentEntity>)

    @Update
    suspend fun updateComment(comment: CommentEntity)
}

@Dao
interface StoryDao {
    @Query("SELECT * FROM stories ORDER BY id ASC")
    fun getAllStories(): Flow<List<StoryEntity>>

    @Query("SELECT * FROM stories WHERE isSaved = 1")
    fun getSavedStories(): Flow<List<StoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(stories: List<StoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: StoryEntity)

    @Update
    suspend fun updateStory(story: StoryEntity)
}

@Dao
interface CommunityDao {
    @Query("SELECT * FROM communities ORDER BY memberCount DESC")
    fun getAllCommunities(): Flow<List<CommunityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunities(communities: List<CommunityEntity>)

    @Update
    suspend fun updateCommunity(community: CommunityEntity)
}

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM challenges ORDER BY dayNumber ASC")
    fun getAllChallenges(): Flow<List<ChallengeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallenges(challenges: List<ChallengeEntity>)

    @Update
    suspend fun updateChallenge(challenge: ChallengeEntity)
}

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE conversationPartner = :partner ORDER BY timestamp ASC")
    fun getMessagesForConversation(partner: String): Flow<List<MessageEntity>>

    @Query("SELECT DISTINCT conversationPartner FROM messages")
    fun getConversationPartners(): Flow<List<String>>

    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>)
}

package com.example.mindora.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mindora.data.model.ChallengeEntity
import com.example.mindora.data.model.CommentEntity
import com.example.mindora.data.model.CommunityEntity
import com.example.mindora.data.model.MessageEntity
import com.example.mindora.data.model.PostEntity
import com.example.mindora.data.model.StoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        PostEntity::class,
        CommentEntity::class,
        StoryEntity::class,
        CommunityEntity::class,
        ChallengeEntity::class,
        MessageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MindoraDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
    abstract fun storyDao(): StoryDao
    abstract fun communityDao(): CommunityDao
    abstract fun challengeDao(): ChallengeDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: MindoraDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MindoraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MindoraDatabase::class.java,
                    "mindora_database"
                )
                    .addCallback(MindoraDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class MindoraDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database)
                }
            }
        }

        suspend fun populateDatabase(database: MindoraDatabase) {
            database.postDao().insertPosts(SeedData.initialPosts)
            database.commentDao().insertComments(SeedData.initialComments)
            database.storyDao().insertStories(SeedData.initialStories)
            database.communityDao().insertCommunities(SeedData.initialCommunities)
            database.challengeDao().insertChallenges(SeedData.initialChallenges)
            database.messageDao().insertMessages(SeedData.initialMessages)
        }
    }
}

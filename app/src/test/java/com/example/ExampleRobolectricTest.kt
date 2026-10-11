package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.mindora.data.local.SeedData
import com.example.mindora.data.model.MoodType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read app_name from context matches MINDORA`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("MINDORA", appName)
  }

  @Test
  fun `mood types provide valid emojis and labels`() {
    val lonely = MoodType.fromId("LONELY")
    assertEquals("Lonely", lonely.label)
    assertEquals("🌧️", lonely.emoji)

    val lost = MoodType.fromId("LOST")
    assertEquals("Lost", lost.label)
    assertEquals("😶", lost.emoji)
  }

  @Test
  fun `seed data contains founder Forida post and anonymous mind 4821`() {
    val posts = SeedData.initialPosts
    val founderPost = posts.find { it.authorName.contains("Forida") }
    assertNotNull(founderPost)
    assertTrue(founderPost!!.content.contains("MINDORA"))

    val anonPost = posts.find { it.isAnonymous && it.anonymousNumber == 4821 }
    assertNotNull(anonPost)
    assertTrue(anonPost!!.content.contains("disconnected from everyone"))
  }
}

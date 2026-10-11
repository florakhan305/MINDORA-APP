package com.example.mindora.data.local

import com.example.mindora.data.model.ChallengeEntity
import com.example.mindora.data.model.CommentEntity
import com.example.mindora.data.model.CommunityEntity
import com.example.mindora.data.model.MessageEntity
import com.example.mindora.data.model.PostEntity
import com.example.mindora.data.model.StoryEntity

object SeedData {
    val initialPosts = listOf(
        PostEntity(
            id = "post-founder-1",
            authorName = "Forida",
            authorHandle = "@forida_founder",
            isAnonymous = false,
            anonymousNumber = 0,
            content = "Welcome to MINDORA — where your mind meets its aura. 🌌✨\n\nWe created this digital sanctuary so that no one has to suffer in silence or pretend everything is okay. Here, vulnerability is strength and kindness is our guiding aura. Speak what's in your mind without feeling judged.",
            category = "Thoughts",
            moodTag = "LOVED",
            likesCount = 1420,
            commentsCount = 89,
            sharesCount = 312,
            isLiked = true,
            isSaved = true,
            isFollowing = true,
            timestamp = System.currentTimeMillis() - 3600_000 * 5
        ),
        PostEntity(
            id = "post-anon-4821",
            authorName = "Anonymous Mind #4821",
            authorHandle = "@anon_4821",
            isAnonymous = true,
            anonymousNumber = 4821,
            content = "I don't know why, but lately I feel disconnected from everyone.\n\nI sit in rooms full of laughter and still feel miles away. I smile, answer questions, but inside it feels like static. Just wanted to put this somewhere without people in my life worrying.",
            category = "Thoughts",
            moodTag = "LOST",
            likesCount = 529,
            commentsCount = 48,
            sharesCount = 74,
            isLiked = false,
            isSaved = false,
            isFollowing = false,
            timestamp = System.currentTimeMillis() - 3600_000 * 2
        ),
        PostEntity(
            id = "post-quote-akira",
            authorName = "Akira",
            authorHandle = "@akira_writes",
            isAnonymous = false,
            anonymousNumber = 0,
            content = "🌙 “Sometimes you don't need advice. You just need someone to understand why you're quiet.”\n\nThe silence isn't empty; it's heavy with words that don't know how to arrange themselves yet.",
            category = "Quotes",
            moodTag = "LONELY",
            likesCount = 842,
            commentsCount = 63,
            sharesCount = 195,
            isLiked = true,
            isSaved = true,
            isFollowing = true,
            timestamp = System.currentTimeMillis() - 3600_000 * 8
        ),
        PostEntity(
            id = "post-shayari-1",
            authorName = "Zainab",
            authorHandle = "@zainab_poetry",
            isAnonymous = false,
            anonymousNumber = 0,
            content = "kuch log khamoshi se chale jaate hain,\npar unka chhodha hua sannaata barson tak goonjta rehta hai...\n\nHumne toh sirf ek shaam maangi thi unse,\nwoh saari umr ka intezaar de gaye. ✍️✨",
            category = "Shayari",
            moodTag = "SAD",
            likesCount = 612,
            commentsCount = 37,
            sharesCount = 120,
            isLiked = false,
            isSaved = false,
            isFollowing = false,
            timestamp = System.currentTimeMillis() - 3600_000 * 12
        ),
        PostEntity(
            id = "post-poll-mind",
            authorName = "Mindora Resonant",
            authorHandle = "@mindora_official",
            isAnonymous = false,
            anonymousNumber = 0,
            content = "Late Night Mind Check-in: When your mind feels crowded at 2 AM, what brings you the deepest calm?",
            category = "Questions",
            moodTag = "TIRED",
            likesCount = 389,
            commentsCount = 54,
            sharesCount = 42,
            isLiked = false,
            isSaved = false,
            isFollowing = true,
            timestamp = System.currentTimeMillis() - 3600_000 * 14,
            pollQuestion = "Late Night Mind Check-in",
            pollOptionsJson = "Night walks & Stargazing||Ambient lofi & piano||Journaling on paper||Sitting in total darkness",
            pollVotesJson = "142||310||189||98",
            userPollVotedIndex = 1
        ),
        PostEntity(
            id = "post-anon-7319",
            authorName = "Anonymous Mind #7319",
            authorHandle = "@anon_7319",
            isAnonymous = true,
            anonymousNumber = 7319,
            content = "Today I submitted my thesis after 3 years of self-doubt. I had no one in person to celebrate with, but sitting here with tea, I feel a quiet pride blooming. Small victories count. 🔥",
            category = "Motivation",
            moodTag = "MOTIVATED",
            likesCount = 741,
            commentsCount = 82,
            sharesCount = 53,
            isLiked = true,
            isSaved = false,
            isFollowing = false,
            timestamp = System.currentTimeMillis() - 3600_000 * 18
        )
    )

    val initialComments = listOf(
        CommentEntity(
            id = "c-1",
            postId = "post-anon-4821",
            authorName = "Kind Aura #109",
            isAnonymous = true,
            content = "You are not broken for feeling detached. We're right here breathing with you. No pressure to perform.",
            timestamp = System.currentTimeMillis() - 3600_000 * 1,
            likesCount = 28,
            isLiked = true,
            isSupportive = true
        ),
        CommentEntity(
            id = "c-2",
            postId = "post-anon-4821",
            authorName = "Akira",
            isAnonymous = false,
            content = "I felt that exact static yesterday. Give yourself grace. The fog clears when you stop fighting it.",
            timestamp = System.currentTimeMillis() - 1800_000,
            likesCount = 19,
            isLiked = false,
            isSupportive = true
        ),
        CommentEntity(
            id = "c-3",
            postId = "post-quote-akira",
            authorName = "Forida",
            isAnonymous = false,
            content = "Beautiful words Akira. This is exactly why we created MINDORA.",
            timestamp = System.currentTimeMillis() - 3600_000 * 6,
            likesCount = 42,
            isLiked = true,
            isSupportive = true
        )
    )

    val initialStories = listOf(
        StoryEntity(
            id = "story-1",
            title = "Whispers of the Cobalt Sea",
            authorName = "Akira",
            genre = "Romance / Mystery",
            summary = "A journey of two solitary travelers discovering forgotten lighthouses and the quiet truths of healing.",
            chaptersJson = "Chapter 1: The Lantern Keeper's Note::The light at the edge of the cliffs had not burned for ten winters. Yet when Evelyn reached the weathered timber gate, a subtle violet warmth seeped through the cracks. It was not fire, but memory.::4##Chapter 2: Words Left in Glass::Beneath the spiral stairs rested a jar stuffed with folded parchment. Each slip carried confessions never spoken out loud—regrets of departures, quiet apologies, wishes made under shooting stars.::6##Chapter 3: The Dawn Horizon::By dawn, the mist retreated. Standing together against the sea wind, they understood: some wounds don't need erasing, only gentle sunlight.::5",
            readCount = 1840,
            likesCount = 390,
            isSaved = true
        ),
        StoryEntity(
            id = "story-2",
            title = "Midnight Chronicles: Shadows of Neo-Kyoto",
            authorName = "Ren K.",
            genre = "Anime / Fandom",
            summary = "In a city draped in holographic rain, a cyber-poet tracks down corrupted emotional memories.",
            chaptersJson = "Chapter 1: Neon Static::Rain fell like silver threads across District 9. Every neon sign flickered with advertisements for synthetic serenity, yet inside the crowded noodle stalls, people kept their eyes lowered.::5##Chapter 2: The Stolen Verse::He found the datapad in an abandoned transit capsule. Scrawled across its cracked screen was a single verse written in human ink, defying digital encryption.::7",
            readCount = 2410,
            likesCount = 512,
            isSaved = false
        ),
        StoryEntity(
            id = "story-3",
            title = "Echoes of Forgotten Shayari",
            authorName = "Zainab",
            genre = "Poetry",
            summary = "An anthology of verses reflecting upon unspoken separation, timeless longing, and quiet self-discovery.",
            chaptersJson = "Chapter 1: Palkon Par Thahre Aansoo::Woh jo muskura ke guzar gaye, unhe kya khabar ke unke peeche kitne jazbaat thahar gaye...::3##Chapter 2: Raat Aur Tanhaai::Tanhaai mein jab chaand ubhar aata hai, toh khayaal aainaa ban jaata hai.::3",
            readCount = 980,
            likesCount = 275,
            isSaved = false
        )
    )

    val initialCommunities = listOf(
        CommunityEntity(
            id = "comm-writers",
            name = "Writers Club",
            icon = "✍️",
            description = "Poetry, stories, shayari, and creative prose. Share your raw drafts and inspire others.",
            memberCount = 14200,
            isJoined = true,
            pinnedRule = "Be constructive. Celebrate original voice and emotional honesty."
        ),
        CommunityEntity(
            id = "comm-study",
            name = "Study Club",
            icon = "📚",
            description = "Focus rooms, mutual motivation, student life struggles, and shared study goals.",
            memberCount = 9800,
            isJoined = true,
            pinnedRule = "Encourage each other without toxic productivity."
        ),
        CommunityEntity(
            id = "comm-anime",
            name = "Anime Club",
            icon = "🎌",
            description = "Deep thematic discussions, anime aesthetic appreciation, soundtrack lovers & lore.",
            memberCount = 18500,
            isJoined = true,
            pinnedRule = "Respect spoiler tags and diverse tastes."
        ),
        CommunityEntity(
            id = "comm-coding",
            name = "Coding Club",
            icon = "💻",
            description = "Developers sharing side projects, bug struggles, learning journeys, and tech musings.",
            memberCount = 8200,
            isJoined = false,
            pinnedRule = "Beginner friendly. No elitism, only support."
        ),
        CommunityEntity(
            id = "comm-artists",
            name = "Artists Haven",
            icon = "🎨",
            description = "Sketches, digital art, color palettes, and visual storytelling.",
            memberCount = 11300,
            isJoined = false,
            pinnedRule = "Credit original art and provide thoughtful compliments."
        ),
        CommunityEntity(
            id = "comm-gamers",
            name = "Gamers Lounge",
            icon = "🎮",
            description = "Cozy gaming, indie discoveries, narrative RPG discussions, and friendly lobbies.",
            memberCount = 7600,
            isJoined = false,
            pinnedRule = "Good sportsmanship only. Zero harassment tolerated."
        ),
        CommunityEntity(
            id = "comm-language",
            name = "Language Learning",
            icon = "🌍",
            description = "Practicing vocabulary, cultural idioms, poetry in original languages, and phonetic notes.",
            memberCount = 6400,
            isJoined = false,
            pinnedRule = "Mistakes are celebrated stepping stones."
        ),
        CommunityEntity(
            id = "comm-science",
            name = "Science & Mind",
            icon = "🔬",
            description = "Cosmology, psychology, neuroscience, cognitive wonder, and the mysteries of consciousness.",
            memberCount = 5900,
            isJoined = false,
            pinnedRule = "Cite sources when discussing scientific claims."
        )
    )

    val initialChallenges = listOf(
        ChallengeEntity(
            id = "chal-1",
            dayNumber = 1,
            title = "Day 1: Write about your biggest dream",
            prompt = "Strip away doubts and practical constraints. What does your heart desire in its purest form?",
            xpReward = 100,
            badgeName = "Dream Weaver 🏅",
            isCompleted = true,
            userSubmission = "My biggest dream is to publish a collection of midnight thoughts that gives a lonely teenager in another corner of the globe the exact reassurance I needed back then."
        ),
        ChallengeEntity(
            id = "chal-2",
            dayNumber = 2,
            title = "Day 2: Write a story using only 100 words",
            prompt = "Every syllable counts. Capture an entire world and emotional turning point within exactly a century of words.",
            xpReward = 150,
            badgeName = "100-Word Alchemist 🏅",
            isCompleted = true,
            userSubmission = "He held the faded postcard against the autumn drizzle. Thirty years of silence dissolved in four words written on the back: 'I forgive you too.' He smiled, letting the rain wash the rest away."
        ),
        ChallengeEntity(
            id = "chal-3",
            dayNumber = 3,
            title = "Day 3: Write a mystery ending",
            prompt = "Leave the reader breathless with an unexpected twist in the final sentence.",
            xpReward = 200,
            badgeName = "Shadow Architect 🏅",
            isCompleted = false,
            userSubmission = null
        ),
        ChallengeEntity(
            id = "chal-4",
            dayNumber = 4,
            title = "Day 4: Letter to your younger self",
            prompt = "What truth would have softened the sting of your hardest year?",
            xpReward = 250,
            badgeName = "Time Healer 🏅",
            isCompleted = false,
            userSubmission = null
        ),
        ChallengeEntity(
            id = "chal-5",
            dayNumber = 5,
            title = "Day 5: Describe a silence",
            prompt = "Not all quiet moments are peaceful. Describe a silence that spoke volumes.",
            xpReward = 300,
            badgeName = "Silence Listener 🏅",
            isCompleted = false,
            userSubmission = null
        ),
        ChallengeEntity(
            id = "chal-6",
            dayNumber = 6,
            title = "Day 6: A world without clocks",
            prompt = "How would human connection shift if time was measured only by heartbeats and seasons?",
            xpReward = 350,
            badgeName = "Timeless Mind 🏅",
            isCompleted = false,
            userSubmission = null
        ),
        ChallengeEntity(
            id = "chal-7",
            dayNumber = 7,
            title = "Day 7: The Grand Aura Chronicle",
            prompt = "Synthesize your week of writing into a crowning piece on the human mind.",
            xpReward = 500,
            badgeName = "MINDORA Laureate 🏆",
            isCompleted = false,
            userSubmission = null
        )
    )

    val initialMessages = listOf(
        MessageEntity(
            id = "m-1",
            conversationPartner = "Forida (Founder)",
            isAnonymousPartner = false,
            senderIsMe = false,
            text = "Hi Akira! Thank you for being one of the founding writers of MINDORA. Your words bring so much solace to others.",
            timestamp = System.currentTimeMillis() - 3600_000 * 24
        ),
        MessageEntity(
            id = "m-2",
            conversationPartner = "Forida (Founder)",
            isAnonymousPartner = false,
            senderIsMe = true,
            text = "Thank you so much Forida! The safe atmosphere here is truly special. Finally a place where quiet thoughts belong.",
            timestamp = System.currentTimeMillis() - 3600_000 * 23
        ),
        MessageEntity(
            id = "m-3",
            conversationPartner = "Forida (Founder)",
            isAnonymousPartner = false,
            senderIsMe = false,
            text = "That's exactly what MINDORA is for: Mind + Aura. Keep writing!",
            timestamp = System.currentTimeMillis() - 3600_000 * 22
        ),
        MessageEntity(
            id = "m-4",
            conversationPartner = "Anonymous Mind #4821",
            isAnonymousPartner = true,
            senderIsMe = false,
            text = "Hey, thank you for commenting on my post earlier. It really helped me get through a difficult evening.",
            timestamp = System.currentTimeMillis() - 3600_000 * 1
        ),
        MessageEntity(
            id = "m-5",
            conversationPartner = "Anonymous Mind #4821",
            isAnonymousPartner = true,
            senderIsMe = true,
            text = "I'm always here if you need to talk. You're never alone in this space.",
            timestamp = System.currentTimeMillis() - 1800_000
        )
    )
}

package com.packt.android

import com.packt.android.api.Post
import com.packt.android.repository.PostRepository

class DummyRepository : PostRepository {

    override suspend fun getPosts(): List<Post> {
        return listOf(
            Post(1L, 1L, "Title 1", "Body 1"),
            Post(2L, 1L, "Title 2", "Body 2"),
            Post(3L, 1L, "Title 3", "Body 3")
        )
    }
}
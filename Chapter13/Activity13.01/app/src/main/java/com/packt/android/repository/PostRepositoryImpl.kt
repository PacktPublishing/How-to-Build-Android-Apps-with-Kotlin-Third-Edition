package com.packt.android.repository

import com.packt.android.api.Post
import com.packt.android.api.PostService

class PostRepositoryImpl(private val postService: PostService) : PostRepository {
    override suspend fun getPosts(): List<Post> {
        return postService.getPosts()
    }
}
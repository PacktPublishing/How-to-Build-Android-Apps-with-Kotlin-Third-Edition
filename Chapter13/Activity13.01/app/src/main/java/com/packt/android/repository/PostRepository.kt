package com.packt.android.repository

import com.packt.android.api.Post

interface PostRepository {

    suspend fun getPosts(): List<Post>
}
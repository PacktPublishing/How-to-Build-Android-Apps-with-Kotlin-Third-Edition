package com.packt.android.repository

import com.packt.android.api.PostService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providePostRepository(postService: PostService): PostRepository {
        return PostRepositoryImpl(postService)
    }
}
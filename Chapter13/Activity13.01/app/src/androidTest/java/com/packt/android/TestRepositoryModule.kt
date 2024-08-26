package com.packt.android


import com.packt.android.repository.PostRepository
import com.packt.android.repository.RepositoryModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class TestRepositoryModule {

    @Singleton
    @Provides
    fun providePostRepository(): PostRepository {
        return DummyRepository()
    }
}
package com.packt.android

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Random

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideRandom(): Random = Random()

    @Provides
    fun provideNumberRepository(random: Random): NumberRepository = NumberRepositoryImpl(random)
}

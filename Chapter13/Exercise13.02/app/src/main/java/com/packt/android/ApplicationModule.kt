package com.packt.android

import dagger.Module
import dagger.Provides
import java.util.Random

@Module
class ApplicationModule {

    @Provides
    fun provideRandom(): Random = Random()

    @Provides
    fun provideNumberRepository(random: Random): NumberRepository = NumberRepositoryImpl(random)
}

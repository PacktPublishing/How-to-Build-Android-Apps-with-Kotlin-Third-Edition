package com.packt.android

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun createMainSubcomponent(): MainSubcomponent
}

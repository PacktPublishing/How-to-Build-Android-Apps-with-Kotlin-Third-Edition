package com.packt.android

import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [MainModule::class])
interface MainSubcomponent {
    fun inject(mainActivity: MainActivity)
}

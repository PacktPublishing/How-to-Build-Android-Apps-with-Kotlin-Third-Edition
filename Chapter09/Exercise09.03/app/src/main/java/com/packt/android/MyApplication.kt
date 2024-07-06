package com.packt.android

import android.app.Application
import java.util.Random

open class MyApplication : Application() {

    lateinit var randomizer: Randomizer

    override fun onCreate() {
        super.onCreate()
        randomizer = generateRandomizer()
    }

    open fun generateRandomizer(): Randomizer = RandomizerImpl(Random())
}
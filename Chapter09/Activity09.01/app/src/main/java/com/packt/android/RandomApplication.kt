package com.packt.android

import android.app.Application
import java.util.Random

open class RandomApplication : Application() {

    lateinit var randomNumberGenerator: RandomNumberGenerator

    override fun onCreate() {
        super.onCreate()
        randomNumberGenerator = RandomNumberGeneratorImpl(Random())
    }
}
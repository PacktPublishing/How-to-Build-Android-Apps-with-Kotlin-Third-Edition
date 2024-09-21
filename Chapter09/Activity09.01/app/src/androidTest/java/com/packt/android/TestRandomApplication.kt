package com.packt.android

class TestRandomApplication : RandomApplication() {

    override fun onCreate() {
        super.onCreate()
        randomNumberGenerator = object : RandomNumberGenerator {
            override fun generateNumber(): Int {
                return 10
            }

        }
    }
}
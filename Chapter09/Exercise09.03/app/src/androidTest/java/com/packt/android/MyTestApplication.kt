package com.packt.android

class MyTestApplication : MyApplication() {
    override fun generateRandomizer(): Randomizer = TestRandomizer()
}
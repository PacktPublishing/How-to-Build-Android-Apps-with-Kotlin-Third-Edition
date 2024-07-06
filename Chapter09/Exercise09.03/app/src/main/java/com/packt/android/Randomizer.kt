package com.packt.android

import java.util.Random

interface Randomizer {
    fun getNumber(): Int
}

class RandomizerImpl(private val random: Random) : Randomizer {
    override fun getNumber(): Int {
        return random.nextInt(5) + 1
    }
}

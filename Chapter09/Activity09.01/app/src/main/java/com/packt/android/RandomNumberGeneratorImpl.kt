package com.packt.android

import java.util.Random

class RandomNumberGeneratorImpl(private val random: Random) : RandomNumberGenerator {

    override fun generateNumber(): Int {
        return random.nextInt(10) + 1
    }
}
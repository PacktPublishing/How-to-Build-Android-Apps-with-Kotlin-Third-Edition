package com.packt.android

import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*
import java.util.Random

class RandomNumberGeneratorImplTest {

    private val random = mockk<Random>()
    private val randomNumberGenerator = RandomNumberGeneratorImpl(random)

    @Test
    fun generateNumber_success() {
        every {
            random.nextInt(10)
        } returns 3
        val result = randomNumberGenerator.generateNumber()
        assertEquals(4, result)
    }
}
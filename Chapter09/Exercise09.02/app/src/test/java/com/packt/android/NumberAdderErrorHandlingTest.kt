package com.packt.android

import io.mockk.mockk
import org.junit.Test
import java.math.BigInteger

class NumberAdderErrorHandlingTest {
    private val numberAdder = NumberAdder()
    @Test(expected = NumberAdder.InvalidNumberException::class)
    fun sum() {
        val input = -1
        val callback = mockk<(BigInteger) -> Unit>()
        numberAdder.sum(input, callback)
    }
}
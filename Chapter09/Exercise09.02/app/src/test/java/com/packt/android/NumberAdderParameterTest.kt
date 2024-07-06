package com.packt.android

import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.math.BigInteger

@RunWith(Parameterized::class)
class NumberAdderParameterTest(
    private val input: Int,
    private val expected: BigInteger
) {
    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun getData(): List<Array<out Any>> = listOf(
            arrayOf(0, BigInteger.ZERO),
            arrayOf(1, BigInteger.ONE),
            arrayOf(5, 15.toBigInteger()),
            arrayOf(20, 210.toBigInteger()),
            arrayOf(Int.MAX_VALUE, BigInteger("2305843008139952128"))
        )
    }

    private val numberAdder = NumberAdder()

    @Test
    fun sum() {
        val callback = mockk<(BigInteger) -> Unit>(relaxed = true)
        numberAdder.sum(input, callback)
        verify { callback.invoke(expected) }
    }
}
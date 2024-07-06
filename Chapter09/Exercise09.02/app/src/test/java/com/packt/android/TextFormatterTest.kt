package com.packt.android

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import java.math.BigInteger

class TextFormatterTest {
    private val numberAdder = mockk<NumberAdder>()
    private val context = mockk<Context>()
    private val textFormatter = TextFormatter(numberAdder, context)

    @Test
    fun getSumResult_success() {
        val n = 10
        val sumResult = BigInteger.TEN
        val expected = "expected"
        every {
            numberAdder.sum(eq(n), any())
        } answers {
            (it.invocation.args[1] as (BigInteger) -> Unit).invoke(sumResult)
        }
        every {
            context.getString(
                R.string.the_sum_of_numbers_from_1_to_is,
                n,
                sumResult.toString()
            )
        } returns expected
        val callback = mockk<(String) -> Unit>(relaxed = true)
        textFormatter.getSumResult(n, callback)
        verify { callback.invoke(expected) }
    }

    @Test
    fun getSumResult_error() {
        val n = 10
        val expected = "expected"
        every {
            numberAdder.sum(eq(n), any())
        } throws NumberAdder.InvalidNumberException
        every {
            context.getString(R.string.error_invalid_number)
        } returns expected
        val callback = mockk<(String) -> Unit>(relaxed = true)
        textFormatter.getSumResult(n, callback)
        verify { callback.invoke(expected) }
    }
}
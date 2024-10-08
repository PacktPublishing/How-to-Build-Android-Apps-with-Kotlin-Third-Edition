package com.packt.android

import android.content.Context

class TextFormatter(
    private val numberAdder: NumberAdder,
    private val context: Context
) {

    fun getSumResult(n: Int, callback: (String) -> Unit) {
        try {
            numberAdder.sum(n) {
                callback(
                    context.getString(
                        R.string.the_sum_of_numbers_from_1_to_is,
                        n,
                        it.toString()
                    )
                )
            }
        } catch (e: NumberAdder.InvalidNumberException) {
            callback(context.getString(R.string.error_invalid_number))
        }
    }
}
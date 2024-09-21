package com.packt.android

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class RandomApplicationTestRunner : AndroidJUnitRunner() {
    @Throws(Exception::class)
    override fun newApplication(
        cl: ClassLoader?, className: String?, context: Context?
    ): Application? {
        return super.newApplication(cl, TestRandomApplication::class.java.name, context)
    }
}
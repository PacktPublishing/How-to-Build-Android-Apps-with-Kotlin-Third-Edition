package com.packt.android

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import org.junit.Rule
import org.junit.Test

class MainActivityUiTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun verifyGeneratedNumber() {
        val scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        composeRule.onNodeWithText("Press Me")
            .performClick()
        composeRule.onNodeWithText(getApplicationContext<Application>().getString(R.string.generated_number,  2))
            .assertIsDisplayed()
    }
}
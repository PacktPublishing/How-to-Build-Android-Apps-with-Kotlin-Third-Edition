package com.packt.android

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUiTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun showSumResultInTextView() {
        val scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        composeRule.onNodeWithText("Text Field")
            .performTextInput("5")
        composeRule.onNodeWithText("Press Me")
            .performClick()
        composeRule.onNodeWithText(getApplicationContext<Application>().getString(R.string.the_sum_of_numbers_from_1_to_is,  5, 15))
            .assertIsDisplayed()
    }

    @Test
    fun showErrorInTextView() {
        val scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        composeRule.onNodeWithText("Text Field")
            .performTextInput("-5")
        composeRule.onNodeWithText("Press Me")
            .performClick()
        composeRule.onNodeWithText(getApplicationContext<Application>().getString(R.string.error_invalid_number))
            .assertIsDisplayed()
    }
}
package com.packt.android

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `show sum result in text view`() {
        val scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { activity ->
            composeRule.onNodeWithText("Text Field")
                .performTextInput("5")
            composeRule.onNodeWithText("Press Me")
                .performClick()
            composeRule.onNodeWithText(activity.getString(R.string.the_sum_of_numbers_from_1_to_is,  5, 15))
                .assertIsDisplayed()
        }
    }

    @Test
    fun `show error in text view`() {
        val scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { activity ->
            composeRule.onNodeWithText("Text Field")
                .performTextInput("-5")
            composeRule.onNodeWithText("Press Me")
                .performClick()
            composeRule.onNodeWithText(activity.getString(R.string.error_invalid_number))
                .assertIsDisplayed()
        }
    }
}

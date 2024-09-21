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

class MainActivityTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun verifyItemClicked() {
        val scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        composeRule.onNodeWithText(getApplicationContext<Application>().getString(R.string.press_me))
            .performClick()
        composeRule.onNodeWithText(
            getApplicationContext<Application>().getString(
                R.string.item_x,
                "9"
            )
        ).performClick()
        composeRule.onNodeWithText(
            getApplicationContext<Application>().getString(
                R.string.clicked_item_x,
                "9"
            )
        ).assertIsDisplayed()
    }
}
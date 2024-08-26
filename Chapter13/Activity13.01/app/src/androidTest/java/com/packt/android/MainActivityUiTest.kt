package com.packt.android

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityUiTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testDisplaysPosts() {
        val scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        composeRule
            .onNodeWithText("Title 1")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Body 1")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("Title 2")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Body 2")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("Title 3")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Body 3")
            .assertIsDisplayed()
    }
}
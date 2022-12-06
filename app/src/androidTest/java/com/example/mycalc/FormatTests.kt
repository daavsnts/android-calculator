package com.example.mycalc

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mycalc.view.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FormatTests {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun commasFormatting() {
        for (i in 1..13) onView(withId(R.id.button_n9)).perform(click())
        onView(withId(R.id.text_first_operand)).check(matches(withText("9,999,999,999,999")))
    }

    @Test
    fun commasWithDotFormatting() {
        for (i in 1..5) onView(withId(R.id.button_n9)).perform(click())
        onView(withId(R.id.button_dot)).perform(click())
        for (i in 1..3) onView(withId(R.id.button_n9)).perform(click())
        onView(withId(R.id.text_first_operand)).check(matches(withText("99,999.999")))
    }

    @Test
    fun bigNumberFormatting() {
        for (i in 1..5) onView(withId(R.id.button_n9)).perform(click())
        onView(withId(R.id.button_pow)).perform(click())
        onView(withId(R.id.button_n9)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("9.999E44")))
    }
}
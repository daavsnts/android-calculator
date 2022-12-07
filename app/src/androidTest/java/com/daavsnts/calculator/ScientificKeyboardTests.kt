package com.daavsnts.calculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daavsnts.calculator.view.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScientificKeyboardTests {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun square() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_square)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("64")))
    }

    @Test
    fun cube() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_cube)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("512")))
    }

    @Test
    fun tenPowOperand() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_ten_pow)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("100,000,000")))
    }

    @Test
    fun sine() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_sine)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("0.989")))
    }

    @Test
    fun cos() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_cos)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("-0.146")))
    }

    @Test
    fun tan() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_tan)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("-6.8")))
    }

    @Test
    fun ln() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_ln)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("2.079")))
    }

    @Test
    fun logBaseTen() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_log_ten)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("0.903")))
    }

    @Test
    fun sqrt() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_sqrt)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("2.828")))
    }

    @Test
    fun round() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_dot)).perform(click())
        onView(withId(R.id.button_n4)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_round)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("8")))
    }

    @Test
    fun pi() {
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_pi)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("3.142")))
    }

    @Test
    fun euler() {
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_euler)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("2.718")))
    }

    @Test
    fun percent() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_percent)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("0.08")))
    }

    @Test
    fun factorial() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_factorial)).perform(click())
        onView(withId(R.id.text_first_operand))
            .check(matches(withText("40,320")))
    }
}
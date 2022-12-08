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
class ValidationsTests {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun avoidOperationSelectionWithEmptyOperands() {
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_minus)).perform(click())
        onView(withId(R.id.button_multiply)).perform(click())
        onView(withId(R.id.button_divisor)).perform(click())
        onView(withId(R.id.button_pow)).perform(click())
        onView(withId(R.id.text_operator)).check(matches(withText("")))
    }

    @Test
    fun avoidDotSpam() {
        onView(withId(R.id.button_n2)).perform(click())
        onView(withId(R.id.button_dot)).perform(click())
        onView(withId(R.id.button_dot)).perform(click())
        onView(withId(R.id.text_first_operand)).check(matches(withText("2")))
    }

    @Test
    fun avoidDotAsFirstKey() {
        onView(withId(R.id.button_dot)).perform(click())
        onView(withId(R.id.text_first_operand)).check(matches(withText("")))
    }

    @Test
    fun avoidInfinitePolarity() {
        for (i in 1..7) onView(withId(R.id.button_n9)).perform(click())
        onView(withId(R.id.button_pow)).perform(click())
        for (i in 1..4) onView(withId(R.id.button_n9)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("∞")))

        // Trying to use polarity
        onView(withId(R.id.button_polarity)).perform(click())

        // Checking if infinity still is infinity
        onView(withId(R.id.text_operation_result)).check(matches(withText("∞")))
    }

    @Test
    fun avoidNaNPolarity() {
        // Making NaN
        onView(withId(R.id.button_n0)).perform(click())
        onView(withId(R.id.button_divisor)).perform(click())
        onView(withId(R.id.button_n0)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("-NaN")))

        // Try to polarize NaN
        onView(withId(R.id.button_polarity)).perform(click())

        // Checking if NaN wasn't polarized
        onView(withId(R.id.text_operation_result)).check(matches(withText("-NaN")))
    }

    @Test
    fun avoidUseNanAsOperand() {
        // Making NaN
        onView(withId(R.id.button_n0)).perform(click())
        onView(withId(R.id.button_divisor)).perform(click())
        onView(withId(R.id.button_n0)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("-NaN")))

        // Eval using operator
        onView(withId(R.id.button_plus)).perform(click())

        // Checking if operation has been cleared
        onView(withId(R.id.text_first_operand)).check(matches(withText("")))
        onView(withId(R.id.text_operator)).check(matches(withText("")))
        onView(withId(R.id.text_second_operand)).check(matches(withText("")))
        onView(withId(R.id.text_operation_result)).check(matches(withText("")))
    }

    @Test
    fun avoidDivisionByZero() {
        // Dividing by zero
        onView(withId(R.id.button_n2)).perform(click())
        onView(withId(R.id.button_divisor)).perform(click())
        onView(withId(R.id.button_n0)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())

        // Checking if the result is infinity
        onView(withId(R.id.text_operation_result)).check(matches(withText("∞")))
    }
}
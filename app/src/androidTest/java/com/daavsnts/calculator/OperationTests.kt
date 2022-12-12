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
class OperationTests {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun sum() {
        onView(withId(R.id.button_n1)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_n1)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("2")))
    }

    @Test
    fun multiply() {
        onView(withId(R.id.button_n2)).perform(click())
        onView(withId(R.id.button_multiply)).perform(click())
        onView(withId(R.id.button_n3)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("6")))
    }

    @Test
    fun divide() {
        onView(withId(R.id.button_n3)).perform(click())
        onView(withId(R.id.button_divisor)).perform(click())
        onView(withId(R.id.button_n2)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("1.5")))
    }

    @Test
    fun subtract() {
        onView(withId(R.id.button_n3)).perform(click())
        onView(withId(R.id.button_minus)).perform(click())
        onView(withId(R.id.button_n2)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("1")))
    }

    @Test
    fun pow() {
        onView(withId(R.id.button_n3)).perform(click())
        onView(withId(R.id.button_pow)).perform(click())
        onView(withId(R.id.button_n2)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("9")))
    }

    @Test
    fun mod() {
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_mod)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_n3)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("2")))
    }

    @Test
    fun reversePolarity() {
        // Reverse first operand
        onView(withId(R.id.button_n3)).perform(click())
        onView(withId(R.id.button_polarity)).perform(click())
        onView(withId(R.id.text_first_operand)).check(matches(withText("-3")))

        onView(withId(R.id.button_plus)).perform(click())

        // Reverse second operand
        onView(withId(R.id.button_n8)).perform(click())
        onView(withId(R.id.button_polarity)).perform(click())
        onView(withId(R.id.text_second_operand)).check(matches(withText("-8")))

        onView(withId(R.id.button_equal)).perform(click())

        // Reverse result
        onView(withId(R.id.button_polarity)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("11")))
    }

    @Test
    fun evalUsingOperator() {
        onView(withId(R.id.button_n3)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_n3)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("6")))
        onView(withId(R.id.text_first_operand)).check(matches(withText("6")))
        onView(withId(R.id.text_operator)).check(matches(withText("+")))
    }

    @Test
    fun reverseResultPolarityAndUseInNextOperation() {
        onView(withId(R.id.button_n3)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_n3)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())
        onView(withId(R.id.button_polarity)).perform(click())
        onView(withId(R.id.button_minus)).perform(click())
        onView(withId(R.id.text_first_operand)).check(matches(withText("-6")))
    }

    @Test
    fun operatorChange() {
        onView(withId(R.id.button_n2)).perform(click())

        // Change to +
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.text_operator)).check(matches(withText("+")))

        // Change to -
        onView(withId(R.id.button_minus)).perform(click())
        onView(withId(R.id.text_operator)).check(matches(withText("-")))

        // Change to x
        onView(withId(R.id.button_multiply)).perform(click())
        onView(withId(R.id.text_operator)).check(matches(withText("x")))

        // Change to /
        onView(withId(R.id.button_divisor)).perform(click())
        onView(withId(R.id.text_operator)).check(matches(withText("/")))

        // Change to ^
        onView(withId(R.id.button_pow)).perform(click())
        onView(withId(R.id.text_operator)).check(matches(withText("^")))

        // Change to mod
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.button_mod)).perform(click())
        onView(withId(R.id.button_open_close_scientific_container)).perform(click())
        onView(withId(R.id.text_operator)).check(matches(withText("mod")))
    }

    @Test
    fun removeLastKey() {
        // Typing operation
        onView(withId(R.id.button_n2)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_n2)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())

        // Erase operation result
        onView(withId(R.id.text_operation_result)).check(matches(withText("4")))
        onView(withId(R.id.button_backspace)).perform(click())
        onView(withId(R.id.text_operation_result)).check(matches(withText("")))

        // Erase second operand
        onView(withId(R.id.text_second_operand)).check(matches(withText("2")))
        onView(withId(R.id.button_backspace)).perform(click())
        onView(withId(R.id.text_second_operand)).check(matches(withText("")))

        // Erase operator
        onView(withId(R.id.text_operator)).check(matches(withText("+")))
        onView(withId(R.id.button_backspace)).perform(click())
        onView(withId(R.id.text_operator)).check(matches(withText("")))

        // Erase first operand
        onView(withId(R.id.text_first_operand)).check(matches(withText("2")))
        onView(withId(R.id.button_backspace)).perform(click())
        onView(withId(R.id.text_first_operand)).check(matches(withText("")))
    }

    @Test
    fun clearOperation() {
        // Typing operation
        onView(withId(R.id.button_n2)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_n2)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())

        // Clear operation
        onView(withId(R.id.button_ac)).perform(click())
        onView(withId(R.id.text_second_operand)).check(matches(withText("")))
        onView(withId(R.id.text_first_operand)).check(matches(withText("")))
        onView(withId(R.id.text_operation_result)).check(matches(withText("")))
    }
}
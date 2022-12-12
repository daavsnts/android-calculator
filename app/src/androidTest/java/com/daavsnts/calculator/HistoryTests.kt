package com.daavsnts.calculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.daavsnts.calculator.view.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HistoryTests {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun getOperationFromHistory() {
        // create an operation
        onView(withId(R.id.button_n1)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_n1)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())

        // clear operation
        onView(withId(R.id.button_ac)).perform(click())

        // click in operation on history
        onView(withContentDescription(R.string.top_bar_button_history)).perform(click())
        clickListItem(R.id.rv_operations, 0)

        // check if the operation on main is the same that has clicked on history
        onView(withId(R.id.text_first_operand)).check(matches(withText("1")))
        onView(withId(R.id.text_operator)).check(matches(withText("+")))
        onView(withId(R.id.text_second_operand)).check(matches(withText("1")))
        onView(withId(R.id.text_operation_result)).check(matches(withText("2")))
    }

    @Test
    fun deleteOperationFromHistory() {
        // create an operation
        onView(withId(R.id.button_n1)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_n1)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())

        // clear operation
        onView(withId(R.id.button_ac)).perform(click())

        // delete first operation on history
        onView(withContentDescription(R.string.top_bar_button_history)).perform(click())
        clickOnViewInItemList(R.id.button_delete_operation, R.id.rv_operations)
        clickDialogButtonWithDescription(R.string.history_dialog_confirm)

        // check if history is empty
        assertListItemCount(R.id.rv_operations, 0)
    }

    @Test
    fun clearHistory() {
        // create an operation
        onView(withId(R.id.button_n1)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_n1)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())

        // clear operation
        onView(withId(R.id.button_ac)).perform(click())

        // create an operation
        onView(withId(R.id.button_n2)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_n2)).perform(click())
        onView(withId(R.id.button_equal)).perform(click())

        // clear operation
        onView(withId(R.id.button_ac)).perform(click())

        // clear the history
        onView(withContentDescription(R.string.top_bar_button_history)).perform(click())
        onView(withContentDescription(R.string.top_bar_button_history_clear)).perform(click())
        clickDialogButtonWithDescription(R.string.history_dialog_confirm)

        // check if history is empty
        assertListItemCount(R.id.rv_operations, 0)
    }
}
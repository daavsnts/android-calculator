package com.daavsnts.calculator

import android.view.View
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.daavsnts.calculator.view.adapters.OperationAdapter
import org.hamcrest.Matcher

fun clickChildViewWithId(id: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Click on a child view with specified id."
        }

        override fun perform(uiController: UiController?, view: View) {
            val v: View = view.findViewById(id)
            v.performClick()
        }
    }
}

fun clickDialogButtonWithDescription(@StringRes textOfButton: Int) {
    onView(withText(textOfButton))
        .inRoot(isDialog())
        .check(matches(isDisplayed())).perform(click())
}

fun clickOnViewInItemList(viewId: Int, listId: Int) {
    onView(ViewMatchers.withId(listId))
        .perform(
            RecyclerViewActions.actionOnItemAtPosition<OperationAdapter.OperationViewHolder>(
                0,
                clickChildViewWithId(viewId)
            )
        )
}
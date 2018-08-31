package com.training.scoreboard

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.training.scoreboard.R.id.*
import com.training.scoreboard.ui.main.MainActivity
import android.support.test.espresso.matcher.ViewMatchers.*
import com.training.scoreboard.utils.EspressoIdlingResource
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource().idlingResource)

        onView(withText("MAN CITY")).check(matches(isDisplayed()))
        onView(withText("MAN CITY")).perform(click())

        onView(withId(add_to_favorite_prev)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite_prev)).perform(click())
        onView(withText("Added to favorite")).check(matches(isDisplayed()))
        pressBack()
        onView(withId(navigation_bottom)).check(matches(isDisplayed()))
        onView(withId(next_bottom)).perform(click())

        onView(withText("ARSENAL")).check(matches(isDisplayed()))
        onView(withText("ARSENAL")).perform(click())

        onView(withId(add_to_favorite_next)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite_next)).perform(click())
        onView(withText("Added to favorite")).check(matches(isDisplayed()))
        pressBack()

        onView(withId(navigation_bottom)).check(matches(isDisplayed()))
        onView(withId(favorite_bottom)).perform(click())

        onView(withText("MAN CITY")).check(matches(isDisplayed()))
        onView(withText("MAN CITY")).perform(click())
        pressBack()
    }
}
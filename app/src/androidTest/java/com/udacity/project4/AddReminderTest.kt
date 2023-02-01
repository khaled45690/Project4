package com.udacity.project4



import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.GrantPermissionRule
import org.junit.Assert.*
import org.junit.Assume.assumeTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
class AddReminderStepsTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        ACCESS_FINE_LOCATION,
        ACCESS_COARSE_LOCATION)

    fun setup() {
        assumeTrue("Skipping non-debug test", BuildConfig.DEBUG)

    }

    @Test
    fun exampleFragmentTest() {
        launchActivity<MainActivity>()
        Thread.sleep(500)
        onView(withId(R.id.floatingActionButton)).check(matches(isDisplayed())).perform(ViewActions.click())
        Thread.sleep(500)
        onView(withId(R.id.selectLocation)).check(matches(isDisplayed())).perform(ViewActions.click())
        Thread.sleep(500)
        onView(withId(R.id.reminderTitle)).check(matches(isDisplayed())).perform(ViewActions.typeText("reminderTitle"))
        Thread.sleep(500)
        onView(withId(R.id.reminderDescription)).check(matches(isDisplayed())).perform(ViewActions.typeText("reminderDescription"))
        Thread.sleep(2000)
        onView(withId(R.id.selectLocation)).perform(ViewActions.click())
        Thread.sleep(2000)
        onView(withId(R.id.setPOIForTesting)).check(matches(isDisplayed())).perform(ViewActions.click())
        Thread.sleep(1500)
        onView(withId(R.id.save_locatio_button)).check(matches(isDisplayed())).perform(ViewActions.click())
        Thread.sleep(1000)
        onView(withId(R.id.saveReminder)).check(matches(isDisplayed())).perform(ViewActions.click())

        Thread.sleep(12000)
    }

}
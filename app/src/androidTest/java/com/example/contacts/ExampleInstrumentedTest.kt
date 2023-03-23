package com.example.contacts

import androidx.test.espresso.Espresso.onView
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.contacts", appContext.packageName)
    }

    @Test
    fun add_contact(){

        onView(withId(R.id.ivAddContact))
            .perform(click())

        //inside add contact
        val name = Math.random().toString()
        onView(withId(R.id.etFirstName))
            .perform(typeText(name))
        onView(withId(R.id.etLastName))
            .perform(typeText("Doe"))
        onView(withId(R.id.etNumber))
            .perform(typeText("123456789"))

        onView(withId(R.id.btnConfirm))
            .perform(click())

        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText("$name Doe"))
            ))

        onView(withText("$name Doe"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun edit_contact(){
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //in contact info
        onView(withId(R.id.btnEdit))
            .perform(click())

        //in edit contact
        val name = Math.random().toString()
        //for every edit text 1. clear 2. write
        onView(withId(R.id.etFirstName))
            .perform(clearText())
        onView(withId(R.id.etFirstName))
            .perform(typeText(name))

        onView(withId(R.id.etLastName))
            .perform(clearText())
        onView(withId(R.id.etLastName))
            .perform(typeText("test"))

        onView(withId(R.id.etNumber))
            .perform(clearText())
        onView(withId(R.id.etNumber))
            .perform(typeText("123456789"))

        onView(withId(R.id.btnConfirm)).perform(click())

        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText("$name test"))
            ))

        onView(withText("$name test"))
            .check(matches(isDisplayed()))

    }
}
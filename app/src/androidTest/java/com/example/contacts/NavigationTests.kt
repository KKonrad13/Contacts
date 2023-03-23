package com.example.contacts

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.contacts.fragments.AddContactFragment
import com.example.contacts.fragments.ContactInfoFragment
import com.example.contacts.fragments.EditContactFragment
import com.example.contacts.fragments.MainFragment
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NavigationTests {

    @Test
    fun navigate_to_add_contact_from_main_fragment(){

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val mainFragmentScenario =
            launchFragmentInContainer<MainFragment>(themeResId = R.style.Theme_Contacts)

        mainFragmentScenario.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.ivAddContact)).perform(click())

        assertEquals(navController.currentDestination?.id, R.id.addContactFragment)
    }

    @Test
    fun navigate_to_main_fragment_from_add_contact(){

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val addContactFragmentScenario =
            launchFragmentInContainer<AddContactFragment>(themeResId = R.style.Theme_Contacts)

        addContactFragmentScenario.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(destId = R.id.addContactFragment)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.btnCancel)).perform(click())

        assertEquals(navController.currentDestination?.id, R.id.mainFragment)
    }

    @Test
    fun navigate_to_edit_contact_from_contact_info(){

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val addContactFragmentScenario =
            launchFragmentInContainer<ContactInfoFragment>(themeResId = R.style.Theme_Contacts)

        addContactFragmentScenario.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(destId = R.id.contactInfoFragment)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.btnEdit)).perform(click())

        assertEquals(navController.currentDestination?.id, R.id.editContactFragment)
    }

    @Test
    fun navigate_to_main_fragment_from_edit_contact(){

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val addContactFragmentScenario =
            launchFragmentInContainer<EditContactFragment>(themeResId = R.style.Theme_Contacts)

        addContactFragmentScenario.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(destId = R.id.editContactFragment)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.btnCancel)).perform(click())

        assertEquals(navController.currentDestination?.id, R.id.mainFragment)
    }

    @Test
    fun navigate_to_main_fragment_from_contact_info(){

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val addContactFragmentScenario =
            launchFragmentInContainer<ContactInfoFragment>(themeResId = R.style.Theme_Contacts)

        addContactFragmentScenario.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(destId = R.id.contactInfoFragment)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.btnCancelInfo)).perform(click())

        assertEquals(navController.currentDestination?.id, R.id.mainFragment)
    }
}
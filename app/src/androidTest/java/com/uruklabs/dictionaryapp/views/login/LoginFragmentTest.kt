package com.uruklabs.dictionaryapp.views.login

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uruklabs.dictionaryapp.MainActivity
import com.uruklabs.dictionaryapp.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {


    /**
     * Comment code "(activity as AppCompatActivity).supportActionBar?.hide()" in LoginFragment.kt to run this test
     */


    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var scenario: FragmentScenario<LoginFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_DictionaryApp)
    }


    @Test
    fun check_title_is_displayed() {
        onView(withId(R.id.tv_title_in_login)).check(matches(withText(scenario.withFragment { getString(R.string.app_name) })))
    }

    @Test
    fun check_sign_in_string_is_displayed() {
        onView(withId(R.id.tv_sign_in)).check(matches(withText(scenario.withFragment { getString(R.string.sign_in) })))
    }

    @Test
    fun check_welcome_string_is_displayed() {
        onView(withId(R.id.tv_welcome_login)).check(matches(withText(scenario.withFragment { getString(R.string.welcome) })))
    }

    @Test
    fun check_edt_email_is_displayed() {
        onView(withId(R.id.edtEmail)).check(matches(isDisplayed()))
    }

    @Test
    fun check_edt_email_hint_is_displayed() {
        onView(withId(R.id.edtEmail)).check(matches(withHint(scenario.withFragment { getString(R.string.email_address) })))
    }

    @Test
    fun check_edt_password_is_displayed() {
        onView(withId(R.id.edtPassword)).check(matches(isDisplayed()))
    }

    @Test
    fun check_edt_password_hint_is_displayed() {
        onView(withId(R.id.edtPassword)).check(matches(withHint(scenario.withFragment { getString(R.string.password) })))
    }

    @Test
    fun check_btn_login_is_displayed() {
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
    }

    @Test
    fun check_btn_login_text_is_displayed() {
        onView(withId(R.id.btnLogin)).check(matches(withText(scenario.withFragment { getString(R.string.sign_in) })))
    }

    @Test
    fun check_switch_remember_me_is_displayed() {
        onView(withId(R.id.switchCompat)).check(matches(isDisplayed()))
    }

    @Test
    fun check_btn_register_is_displayed() {
        onView(withId(R.id.tvSignUp)).check(matches(isDisplayed()))
    }

    @Test
    fun check_edt_email_is_enabled() {
        onView(withId(R.id.edtEmail)).check(matches(isEnabled()))
    }

    @Test
    fun check_edt_password_is_enabled() {
        onView(withId(R.id.edtPassword)).check(matches(isEnabled()))
    }

    @Test
    fun check_btn_login_is_enabled() {
        onView(withId(R.id.btnLogin)).check(matches(isEnabled()))
    }

    @Test
    fun check_switch_remember_me_is_enabled() {
        onView(withId(R.id.switchCompat)).check(matches(isEnabled()))
    }

   @Test
   fun check_edt_email_is_typed() {
       onView(withId(R.id.edtEmail)).perform(typeText("leo1@gmail.com"))
   }

   @Test
   fun check_edt_password_is_typed() {
       onView(withId(R.id.edtPassword)).perform(typeText("88540498"))
   }

   @Test
   fun check_btn_login_is_clicked() {
         onView(withId(R.id.btnLogin)).perform(click())
   }

   @Test
   fun check_switch_remember_me_is_clicked() {
       onView(withId(R.id.switchCompat)).perform(click())
   }


}
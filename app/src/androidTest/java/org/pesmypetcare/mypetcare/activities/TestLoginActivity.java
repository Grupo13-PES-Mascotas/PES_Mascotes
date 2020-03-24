package org.pesmypetcare.mypetcare.activities;

import android.view.View;
import android.widget.TableLayout;

import androidx.appcompat.app.ActionBar;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.tabs.TabLayout;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pesmypetcare.mypetcare.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestLoginActivity {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void shouldMakeTheSignUp() {
        onView(withId(R.id.signUpUsernameText)).perform(typeText("johnDoe"), closeSoftKeyboard());
        onView(withId(R.id.signUpMailText)).perform(typeText("johndoe@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.signUpPasswordText)).perform(typeText("AbcDert456$"), closeSoftKeyboard());
        onView(withId(R.id.signUpRepPasswordText)).perform(typeText("AbcDert456$"), closeSoftKeyboard());

        onView(withId(R.id.signupButton)).perform(click());
        //onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotMakeTheSignUpWithSomeEmptyFields() {
        onView(withId(R.id.signUpUsernameText)).perform(typeText("johnDoe"), closeSoftKeyboard());
        onView(withId(R.id.signUpMailText)).perform(typeText("johndoe@gmail.com"), closeSoftKeyboard());

        onView(withId(R.id.signupButton)).perform(click());
        onView(withId(R.id.signUpUsernameText)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotMakeTheSignUpIfThePasswordIsWeak() {
        onView(withId(R.id.signUpUsernameText)).perform(typeText("johnDoe"), closeSoftKeyboard());
        onView(withId(R.id.signUpMailText)).perform(typeText("johndoe@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.signUpPasswordText)).perform(typeText("abc"), closeSoftKeyboard());
        onView(withId(R.id.signUpPasswordText)).perform(typeText("abc"), closeSoftKeyboard());

        onView(withId(R.id.signupButton)).perform(click());
        onView(withId(R.id.signUpUsernameText)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotMakeTheSignUpIfThePasswordsDoesNotMatch() {
        onView(withId(R.id.signUpUsernameText)).perform(typeText("johnDoe"), closeSoftKeyboard());
        onView(withId(R.id.signUpMailText)).perform(typeText("johndoe@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.signUpPasswordText)).perform(typeText("abc"), closeSoftKeyboard());
        onView(withId(R.id.signUpPasswordText)).perform(typeText("123"), closeSoftKeyboard());

        onView(withId(R.id.signupButton)).perform(click());
        onView(withId(R.id.signUpUsernameText)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldMakeLogin() {
        onView(withText("LOG IN")).perform(click());
        onView(withId(R.id.loginUsernameText)).check(matches(isDisplayed()));

        onView(withId(R.id.loginUsernameText)).perform(typeText("johnDoe"), closeSoftKeyboard());
        onView(withId(R.id.loginPasswordText)).perform(typeText("AbcDert456$"), closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());
        //onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotMakeLoginIfAnyFieldIsEmpty() {
        onView(withText("LOG IN")).perform(click());
        onView(withId(R.id.loginUsernameText)).check(matches(isDisplayed()));

        onView(withId(R.id.loginUsernameText)).perform(typeText("johnDoe"), closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.loginUsernameText)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldChangeBetweenTabs() {
        onView(withText("LOG IN")).perform(click());
        onView(withId(R.id.loginUsernameText)).check(matches(isDisplayed()));

        onView(withText("SIGN UP")).perform(click());
        onView(withId(R.id.signUpUsernameText)).check(matches(isDisplayed()));
    }
}

package org.pesmypetcare.mypetcare.activities.fragments;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestNewPasswordFragment {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setUpClass() {
        MainActivity.setEnableLoginActivity(false);
    }

    @Test
    public void shouldShowAllComponentsOfNewPassword() {
        onView(withContentDescription(R.string.navigation_view_open)).perform(click());
        onView(withText(R.string.navigation_settings)).perform(click());
        onView(withId(R.id.changePasswordButton)).check(matches(isDisplayed()));
        onView(withId(R.id.changePasswordButton)).perform(click());
        onView(withId(R.id.oldPasswordText)).check(matches(isDisplayed()));

        onView(withId(R.id.oldPasswordText)).perform(typeText("AbcDef456"));
        onView(withId(R.id.newPasswordText)).perform(typeText("GhiJkl789"));
        onView(withId(R.id.confirmNewPasswordText)).perform(typeText("GhiJkl789"), closeSoftKeyboard());
        onView(withId(R.id.confirmButton)).perform(click());
        //onView(withId(R.id.oldPasswordText)).check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldNotBeAnyBlankField() {
        onView(withContentDescription(R.string.navigation_view_open)).perform(click());
        onView(withText(R.string.navigation_settings)).perform(click());
        onView(withId(R.id.changePasswordButton)).perform(click());
        onView(withId(R.id.oldPasswordText)).check(matches(isDisplayed()));

        onView(withId(R.id.oldPasswordText)).perform(typeText("AbcDef456"));
        onView(withId(R.id.newPasswordText)).perform(typeText("GhiJkl789"), closeSoftKeyboard());
        onView(withId(R.id.confirmButton)).perform(click());
        onView(withId(R.id.oldPasswordText)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotChangePasswordIfTheNewOnesDoesNotCoincide() {
        onView(withContentDescription(R.string.navigation_view_open)).perform(click());
        onView(withText(R.string.navigation_settings)).perform(click());
        onView(withId(R.id.changePasswordButton)).check(matches(isDisplayed()));
        onView(withId(R.id.changePasswordButton)).perform(click());
        onView(withId(R.id.oldPasswordText)).check(matches(isDisplayed()));

        onView(withId(R.id.oldPasswordText)).perform(typeText("AbcDef456"));
        onView(withId(R.id.newPasswordText)).perform(typeText("GhiJkl789"));
        onView(withId(R.id.confirmNewPasswordText)).perform(typeText("MnoPqr123"), closeSoftKeyboard());
        onView(withId(R.id.confirmButton)).perform(click());
        onView(withId(R.id.oldPasswordText)).check(matches(isDisplayed()));
    }
}

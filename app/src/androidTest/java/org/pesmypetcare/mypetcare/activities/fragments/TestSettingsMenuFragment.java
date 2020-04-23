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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestSettingsMenuFragment {
    private static final String LANGUAGE = "Català";

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setUpClass() {
        MainActivity.setEnableLoginActivity(false);
    }

    @Test
    public void shouldShowAllComponentsOfSettings() {
        onView(withContentDescription(R.string.navigation_view_open)).perform(click());
        onView(withText(R.string.navigation_settings)).check(matches(isDisplayed()));
        onView(withText(R.string.navigation_settings)).perform(click());
        onView(withText(R.string.navigation_my_pets)).check(matches(not(isDisplayed())));

        onView(withId(R.id.languageSelector)).check(matches(isDisplayed()));
        onView(withId(R.id.changePasswordButton)).check(matches(isDisplayed()));
        onView(withId(R.id.changeEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.version)).check(matches(withText(R.string.current_version)));
        onView(withId(R.id.logoutButton)).check(matches(isDisplayed()));
    }
}

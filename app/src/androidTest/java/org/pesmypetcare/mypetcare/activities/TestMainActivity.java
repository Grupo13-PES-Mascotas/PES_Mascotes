package org.pesmypetcare.mypetcare.activities;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pesmypetcare.mypetcare.R;

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
public class TestMainActivity {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setUpClass() {
        MainActivity.setEnableLoginActivity(false);
    }

    @Test
    public void shouldDisplayTheNavigationDrawer() {
        onView(withContentDescription(R.string.navigation_view_open)).perform(click());
        onView(withText(R.string.navigation_my_pets)).check(matches(isDisplayed()));
        onView(withText(R.string.navigation_pets_community)).check(matches(isDisplayed()));
        onView(withText(R.string.navigation_my_walks)).check(matches(isDisplayed()));
        onView(withText(R.string.navigation_near_establishments)).check(matches(isDisplayed()));
        onView(withText(R.string.navigation_calendar)).check(matches(isDisplayed()));
        onView(withText(R.string.navigation_achievements)).check(matches(isDisplayed()));
        onView(withText(R.string.navigation_settings)).check(matches(isDisplayed()));

        onView(withId(R.id.imgUser)).check(matches(isDisplayed()));
        onView(withId(R.id.lblUserName)).check(matches(isDisplayed()));
        onView(withId(R.id.lblUserEmail)).check(matches(isDisplayed()));

        onView(withContentDescription(R.string.navigation_view_closed)).perform(click());
        onView(withText(R.string.navigation_my_pets)).check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldNavigationDrawerChangeTitles() {
        onView(withContentDescription(R.string.navigation_view_open)).perform(click());
        onView(withText(R.string.navigation_my_pets)).check(matches(isDisplayed()));

        onView(withText(R.string.navigation_pets_community)).perform(click());
        onView(withText(R.string.navigation_my_pets)).check(matches(not(isDisplayed())));
        onView(withId(R.id.toolbar)).check(matches(withContentDescription(R.string.navigation_pets_community)));
    }
}

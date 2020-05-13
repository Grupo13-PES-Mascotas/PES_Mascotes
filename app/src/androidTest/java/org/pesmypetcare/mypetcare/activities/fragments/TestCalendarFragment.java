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
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestCalendarFragment {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setUpClass() {
        MainActivity.setEnableLoginActivity(false);
    }

    @Test
    public void shouldInputPersonalEvent() {
        onView(withContentDescription(R.string.navigation_view_open)).perform(click());
        onView(withText(R.string.navigation_calendar)).perform(click());
        onView(withId(R.id.btnAddPersonalEvent)).perform(click());
        onView(withText(R.string.dialog_new_event)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.reasonText)).perform(typeText("None"));
        onView(withContentDescription(R.string.timeText)).perform(clearText());
        onView(withContentDescription(R.string.timeText)).perform(typeText("00:00:00"));
        onView(withContentDescription(R.string.spinnerText)).perform(click());
    }

    @Test
    public void shouldCancelInputPersonalEvent() {
        onView(withContentDescription(R.string.navigation_view_open)).perform(click());
        onView(withText(R.string.navigation_calendar)).perform(click());
        onView(withId(R.id.btnAddPersonalEvent)).perform(click());
        onView(withText(R.string.dialog_new_event)).check(matches(isDisplayed()));
        onView(withText(R.string.cancel)).perform(click());
        onView(withId(R.id.btnAddPersonalEvent)).perform(click());
    }
}

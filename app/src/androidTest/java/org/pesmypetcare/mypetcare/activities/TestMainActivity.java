package org.pesmypetcare.mypetcare.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pesmypetcare.mypetcare.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withTagKey;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestMainActivity {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setUpClass() {
        MainActivity.setEnableLoginActivity(false);
    }

    /*@Ignore
    @Test
    public void shouldNavigationDrawerChangeTitles() {
        onView(withId(R.id.navigationPetsCommunity)).perform(click());
        //onView(withId(R.id.toolbar)).check(matches(withId(R.string.app_name)));
    }*/

    @Test
    public void shouldOpenTheFragmentForRegisteringNewPet() {
        onView(withId(R.id.flAddPet)).perform(click());
        onView(withId(R.id.inputPetName)).check(matches(isDisplayed()));

        onView(withId(R.id.inputPetName)).perform(typeText("Linux"));
        onView(withId(R.id.inputGender)).perform(typeText("Male"));
        onView(withId(R.id.inputBirthMonth)).perform(typeText("23 MAR 2020"));
        onView(withId(R.id.inputBreed)).perform(typeText("Husky"));
        onView(withId(R.id.inputWeight)).perform(typeText("5"));
        onView(withId(R.id.inputPathologies)).perform(typeText("lame"));
        onView(withId(R.id.inputRecommendedCalories)).perform(typeText("10"));
        onView(withId(R.id.inputWashFrequency)).perform(typeText("2"));

        onView(withId(R.id.btnAddPet)).perform(click());
        onView(withId(R.id.toolbar)).check(matches(withText("My pets")));
    }
}

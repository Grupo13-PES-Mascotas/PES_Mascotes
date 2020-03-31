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
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestImageZoomFragment {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setUpClass() {
        MainActivity.setEnableLoginActivity(false);
    }

    @Test
    public void shouldMakeZoomOnPetImage() {
        activityRule.getActivity().changeFragment(new InfoPetFragment());
        //onView(withId(R.id.mainMenu)).perform(click());
        onView(withContentDescription("pet profile image")).perform(click());

        onView(withId(R.id.displayedImage)).check(matches(isDisplayed()));
        onView(withId(R.id.flModifyImage)).check(matches(isDisplayed()));
        onView(withId(R.id.flDeleteImage)).check(matches(isDisplayed()));

        onView(withId(R.id.displayedImage)).perform(pressBack());
        onView(withId(R.id.imgPet)).check(matches(isDisplayed()));
        onView(withId(R.id.imgPet)).perform(pressBack());
        onView(withId(R.id.mainMenu)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldMakeZoomOnUserImage() {
        onView(withContentDescription(R.string.navigation_view_open)).perform(click());
        onView(withText(R.string.navigation_settings)).check(matches(isDisplayed()));
        onView(withId(R.id.navigationHeader)).perform(click());
        onView(withText(R.string.navigation_settings)).check(matches(not(isDisplayed())));

        onView(withId(R.id.displayedImage)).perform(pressBack());
        onView(withId(R.id.mainMenu)).check(matches(isDisplayed()));
    }
}

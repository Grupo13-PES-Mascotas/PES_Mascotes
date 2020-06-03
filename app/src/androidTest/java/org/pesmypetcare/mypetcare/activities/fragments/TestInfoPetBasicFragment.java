package org.pesmypetcare.mypetcare.activities.fragments;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.TestActivity;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetBasicFragment;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetFragment;
import org.pesmypetcare.mypetcare.features.pets.Pet;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Albert Pinto
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestInfoPetBasicFragment {

    @Rule
    public ActivityTestRule<TestActivity> activityRule = new ActivityTestRule<>(TestActivity.class);

    @Before
    public void setUp() {
        new InfoPetFragment();
        InfoPetFragment.setPet(new Pet("Dinky"));
        TestActivity.changeFragment(new InfoPetBasicFragment());
    }

    @Test
    public void shouldShowAllComponents() {
        onView(withId(R.id.breed)).check(matches(isDisplayed()));
        onView(withId(R.id.gender)).check(matches(isDisplayed()));
        onView(withId(R.id.pathologies)).check(matches(isDisplayed()));
        onView(withId(R.id.inputBirthMonth)).check(matches(isDisplayed()));
        onView(withId(R.id.updatePet)).check(matches(isDisplayed()));
        onView(withId(R.id.deleteButton)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldEditAllEditableComponents() {
        onView(withId(R.id.txtBreed)).perform(clearText(), typeText("Husky"), closeSoftKeyboard());
        onView(withId(R.id.inputGender)).perform(clearText(), typeText("Male"), closeSoftKeyboard());
    }

    @Test
    public void shouldUpdatePetIfChanges() {
        onView(withId(R.id.txtBreed)).perform(clearText(), typeText("Husky"), closeSoftKeyboard());
        onView(withId(R.id.inputGender)).perform(clearText(), typeText("Male"), closeSoftKeyboard());
        onView(withId(R.id.updatePet)).perform(click());
    }
}

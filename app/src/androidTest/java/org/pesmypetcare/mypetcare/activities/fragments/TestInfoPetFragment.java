package org.pesmypetcare.mypetcare.activities.fragments;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.button.MaterialButton;

import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestInfoPetFragment {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setUpClass() {
        MainActivity.setEnableLoginActivity(false);
    }

    @Test
    public void shouldShowAllComponents() {
        activityRule.getActivity().changeFragment(new InfoPetFragment());

        onView(withId(R.id.petName)).check(matches(isDisplayed()));
        onView(withId(R.id.breed)).check(matches(isDisplayed()));
        onView(withId(R.id.weight)).check(matches(isDisplayed()));
        onView(withId(R.id.gender)).check(matches(isDisplayed()));
        onView(withId(R.id.recommendedKcal)).check(matches(isDisplayed()));
        onView(withId(R.id.pathologies)).check(matches(isDisplayed()));
        onView(withId(R.id.washFrequency)).check(matches(isDisplayed()));
        onView(withId(R.id.inputBirthMonth)).check(matches(isDisplayed()));

        onView(withId(R.id.updatePet)).check(matches(isDisplayed()));
        onView(withId(R.id.updatePet)).perform(swipeUp());
        onView(withId(R.id.deleteButton)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldEditAllEditableComponents() {
        activityRule.getActivity().changeFragment(new InfoPetFragment());

        onView(withId(R.id.txtBreed)).perform(clearText(), typeText("Husky"), closeSoftKeyboard());
        onView(withId(R.id.txtWeight)).perform(clearText(), typeText("5.0"), closeSoftKeyboard());
        onView(withId(R.id.inputGender)).perform(clearText(), typeText("Male"), closeSoftKeyboard());
        onView(withId(R.id.txtWashFrequency)).perform(clearText(), typeText("7"), closeSoftKeyboard());
        onView(withId(R.id.scrollInfoPet)).perform(swipeUp());
    }

    private static ViewAction setButtonText(String text) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(MaterialButton.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "The button is not found";
            }

            @Override
            public void perform(UiController uiController, View view) {
                if (view instanceof MaterialButton) {
                    MaterialButton button = (MaterialButton) view;
                    button.setText(text);
                }
            }
        };
    }
}

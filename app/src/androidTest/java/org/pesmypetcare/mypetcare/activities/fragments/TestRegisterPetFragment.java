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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestRegisterPetFragment {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setUpClass() {
        MainActivity.setEnableLoginActivity(false);
    }

    @Test
    public void shouldRegisterNewPet() {
        onView(withId(R.id.flAddPet)).perform(click());
        onView(withId(R.id.inputPetName)).check(matches(isDisplayed()));

        onView(withId(R.id.inputPetName)).perform(typeText("Linux"), closeSoftKeyboard());
        onView(withId(R.id.inputGender)).perform(typeText("Male"), closeSoftKeyboard());
        onView(withId(R.id.inputBirthMonth)).perform(click());
        onView(withText("Selected date")).check(matches(isDisplayed())).perform(pressBack());
        onView(withId(R.id.inputBirthMonth)).perform(setButtonText("5 MAR 2020"));
        onView(withId(R.id.inputBreed)).perform(typeText("Husky"), closeSoftKeyboard());
        onView(withId(R.id.inputWeight)).perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.inputPathologies)).perform(typeText("lame"), closeSoftKeyboard());
        //onView(withId(R.id.inputRecommendedCalories)).perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.inputWashFrequency)).perform(typeText("2"), closeSoftKeyboard());

        onView(withId(R.id.btnAddPet)).perform(click());
        //onView(withId(R.id.mainMenu)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotRegisterNewPetIfAnyFieldIsEmpty() {
        onView(withId(R.id.flAddPet)).perform(click());
        onView(withId(R.id.inputPetName)).check(matches(isDisplayed()));

        onView(withId(R.id.inputPetName)).perform(typeText("Linux"));
        onView(withId(R.id.inputGender)).perform(typeText("Male"), closeSoftKeyboard());

        onView(withId(R.id.btnAddPet)).perform(click());
        onView(withId(R.id.inputPetName)).check(matches(isDisplayed()));
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

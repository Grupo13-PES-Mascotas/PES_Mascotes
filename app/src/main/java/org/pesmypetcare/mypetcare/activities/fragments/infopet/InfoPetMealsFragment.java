package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetMealsBinding;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.utilities.DateTime;
import org.pesmypetcare.mypetcare.utilities.InvalidFormatException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfoPetMealsFragment extends Fragment {
    private FragmentInfoPetMealsBinding binding;
    private SwitchMaterial intervalSelector;
    private boolean isWeeklyInterval;
    private Pet pet;
    private LinearLayout mealDisplay;
    private Button addMealButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetMealsBinding.inflate(inflater, container, false);
        pet = InfoPetFragment.getPet();
        mealDisplay = binding.mealsDisplayLayout;
        addMealButton = binding.addMealButton;
        initializeTestMeals(pet);
        initializeIntervalSwitch();
        initializeAddMealButton();
        initializeMealsLayoutView();
        return binding.getRoot();
    }

    /**
     * Method responsible for initializing the add meal button.
     */
    private void initializeAddMealButton() {
        addMealButton.setOnClickListener(v -> {
            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            EditMealFragment.setPet(pet);
            EditMealFragment.setEditing(false);
            ft.replace(R.id.mainActivityFrameLayout, new EditMealFragment());
            ft.commit();
        });
    }

    /**
     * Temporal method responsible for initializing the test meals.
     * @param pet The pet where the meals will be added
     */
    private void initializeTestMeals(Pet pet) {
        DateTime dateTime = null;
        try {
            dateTime = new DateTime(2020, 03, 25, 23, 51, 21);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 14; ++i) {
            Meals meals = new Meals(dateTime, 51.5 + i, "Meal " + i);
            dateTime.increaseDay();
            pet.addEvent(meals);
        }
    }

    /**
     * Method responsible for initializing the interval switch.
     */
    private void initializeIntervalSwitch() {
        intervalSelector = binding.mealIntervalSelector;
        intervalSelector.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isWeeklyInterval = intervalSelector.isChecked();
            initializeMealsLayoutView();
        });
    }

    /**
     * Method responsible for initializing the meals layout view.
     */
    private void initializeMealsLayoutView() {
        ArrayList<Event> mealsList = new ArrayList<>();
        mealsList.clear();
        mealDisplay.removeAllViews();
        if (isWeeklyInterval) {
            mealsList = getLastWeekMeals();
        } else {
            mealsList = (ArrayList<Event>) pet.getMealEvents();
        }

        for (Event meal : mealsList) {
            initializeMealComponent(meal);
        }
    }

    /**
     * Method responsible for initializing each meal component.
     * @param meal The meal for which we want to initialize the component
     */
    private void initializeMealComponent(Event meal) {
        MaterialButton mealButton = new MaterialButton(Objects.requireNonNull(this.getActivity()), null);
        mealButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        mealButton.setBackgroundColor(getResources().getColor(R.color.white));
        mealButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        mealButton.setStrokeColorResource(R.color.colorAccent);
        mealButton.setStrokeWidth(5);
        mealButton.setGravity(Gravity.START);
        String mealButtonText = ((Meals)meal).getMealName() + " " + ((Meals)meal).getDateTime().toString() + "\n" +
            "Meal Kcal " + ((Meals)meal).getKcal();
        mealButton.setText(mealButtonText);
        mealButton.setOnClickListener(v -> {
            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            EditMealFragment.setPet(pet);
            EditMealFragment.setEditing(true);
            EditMealFragment.setMeal((Meals)meal);
            ft.replace(R.id.mainActivityFrameLayout, new EditMealFragment());
            ft.commit();
        });
        mealDisplay.addView(mealButton);
    }

    /**
     * Method responsible for obtaining all the meals from the last week.
     * @return All the meals from the last week
     */
    private ArrayList<Event> getLastWeekMeals() {
        ArrayList<Event> result = new ArrayList<>();
        result.clear();
        List<Event> aux = pet.getMealEvents();
        for (Event e:aux) {
            boolean calc = DateTime.isLastWeek(e.getDateTime());
            if (calc) {
                result.add(e);
            }
        }
        return result;
    }
}

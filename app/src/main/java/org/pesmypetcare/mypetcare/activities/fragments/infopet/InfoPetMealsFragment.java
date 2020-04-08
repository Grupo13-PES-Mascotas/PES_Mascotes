package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.fragments.calendar.CalendarFragment;
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

    private void initializeAddMealButton() {
        addMealButton.setOnClickListener(v -> {
            Fragment calendar = new CalendarFragment();
            System.out.println("Aun no he petado");
            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainActivityFrameLayout, calendar);
            ft.commit();
        });
    }

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

    private void initializeIntervalSwitch() {
        intervalSelector = binding.mealIntervalSelector;
        intervalSelector.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isWeeklyInterval = intervalSelector.isChecked();
            initializeMealsLayoutView();
        });
    }

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

    private void initializeMealComponent(Event meal) {
        TextInputLayout layout = new TextInputLayout(this.getActivity(), null);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        String mealIdentificationText = ((Meals)meal).getMealName() + " " + ((Meals)meal).getDateTime().toString();
        layout.setHint(mealIdentificationText);
        layout.setBackgroundColor(getResources().getColor(R.color.white));
        layout.setBoxStrokeColor(getResources().getColor(R.color.colorAccent));
        layout.setHintAnimationEnabled(true);
        layout.setHintEnabled(true);
        layout.setStartIconContentDescription(mealIdentificationText);

        TextInputEditText mealComponent = new TextInputEditText(Objects.requireNonNull(this.getActivity()), null);
        String mealKcal = "Meal Kcal " + ((Meals)meal).getKcal();
        mealComponent.setText(mealKcal);
        mealComponent.setEnabled(false);
        layout.addView(mealComponent);
        mealDisplay.addView(layout);
    }

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

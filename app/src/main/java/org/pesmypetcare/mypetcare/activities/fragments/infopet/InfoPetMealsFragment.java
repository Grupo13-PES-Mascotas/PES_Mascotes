package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetMealsBinding;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.utilities.DateTime;
import org.pesmypetcare.mypetcare.utilities.InvalidFormatException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InfoPetMealsFragment extends Fragment {
    private FragmentInfoPetMealsBinding binding;
    private SwitchMaterial intervalSelector;
    private boolean isWeeklyInterval;
    private Pet pet;
    private LinearLayout mealDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetMealsBinding.inflate(inflater, container, false);
        pet = InfoPetFragment.getPet();
        mealDisplay = binding.mealsDisplayLayout;
        initializeTestMeals(pet);
        initializeIntervalSwitch();
        initializeMealsLayoutView();
        return binding.getRoot();
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
        ArrayList<Event> mealsToDisplay = new ArrayList<>();
        mealsToDisplay.clear();
        mealDisplay.removeAllViews();
        if (isWeeklyInterval) {
            mealsToDisplay = getLastWeekMeals();
            System.out.println("Nº de Meals Last Week " + mealsToDisplay.size());
        } else {
            mealsToDisplay = (ArrayList<Event>) pet.getMealEvents();
            System.out.println("Nº de meals totales " + mealsToDisplay.size());
        }

        for (Event meal : mealsToDisplay) {
            initializeMealComponent(meal);
        }
    }

    private void initializeMealComponent(Event meal) {
        LinearLayout layout = new LinearLayout(this.getActivity(), null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView mealIdentification = new TextView(this.getActivity(), null);
        String mealIdentificationText = ((Meals)meal).getMealName() + " " + ((Meals)meal).getDateTime().toString();
        mealIdentification.setText(mealIdentificationText);
        mealIdentification.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(mealIdentification);
        TextView mealKcal = new TextView(this.getActivity(), null);
        String mealKcalText = "Meal Kcal " + ((Meals)meal).getKcal();
        mealKcal.setText(mealKcalText);
        mealKcal.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(mealKcal);
        mealDisplay.addView(layout);
    }

    private ArrayList<Event> getLastWeekMeals() {
        DateTime dateTime = getCurrentDate();
        ArrayList<Event> result = new ArrayList<>();
        result.clear();
        for (int i = 0; i < 7; ++i) {
            ArrayList<Event> temp = (ArrayList<Event>) pet.getMealEventsForDate(dateTime.toString());
            System.out.println("Temp meals : " + temp.toString());
            result.addAll(temp);
            dateTime.increaseDay();
        }
        return result;
    }

    private DateTime getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String strData = dateFormat.format(date);
        return new DateTime(strData);
    }
}

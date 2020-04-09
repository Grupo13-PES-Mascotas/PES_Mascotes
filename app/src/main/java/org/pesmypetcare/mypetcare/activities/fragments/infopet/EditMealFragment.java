package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentEditMealBinding;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.utilities.DateTime;


public class EditMealFragment extends Fragment {
    private FragmentEditMealBinding binding;
    private static Pet pet;
    private static Meals meal;
    private static boolean editing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditMealBinding.inflate(inflater, container, false);
        if (editing) {
            initializeEditFragment();
        } else {
            initializeAddFragment();
        }
        return binding.getRoot();
    }

    private void initializeAddFragment() {
        binding.deleteMealButton.setVisibility(View.INVISIBLE);
        binding.editMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Add meal button");
            }
        });
    }

    private void initializeEditFragment() {
        binding.editMealButton.setText(getResources().getText(R.string.update_meal));
        binding.inputMealName.setText(meal.getMealName());
        binding.inputMealCal.setText(String.valueOf(meal.getKcal()));
        DateTime mealDate = meal.getMealDate();
        System.out.println("To String result: " + mealDate.toString());
        String dateString = mealDate.getDay() + "/" + mealDate.getMonth() + "/" + mealDate.getYear();
        System.out.println("My String result: " + dateString);
        binding.inputMealDate.setText(dateString);
    }

    public static Pet getPet() {
        return pet;
    }

    public static void setPet(Pet pet) {
        EditMealFragment.pet = pet;
    }

    public static Meals getMeal() {
        return meal;
    }

    public static void setMeal(Meals meal) {
        EditMealFragment.meal = meal;
    }

    public static void setEditing(boolean editing) {
        EditMealFragment.editing = editing;
    }

}

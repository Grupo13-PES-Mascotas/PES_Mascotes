package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentEditMealBinding;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.utilities.DateTime;

import java.util.Objects;


public class EditMealFragment extends Fragment {
    private FragmentEditMealBinding binding;
    private static Pet pet;
    private static Meals meal;
    private static boolean editing;
    private Button mealDate;
    private MaterialDatePicker materialDatePicker;
    private boolean isMealDateSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditMealBinding.inflate(inflater, container, false);
        setCalendarPicker();
        if (editing) {
            initializeEditFragment();
        } else {
            initializeAddFragment();
        }
        return binding.getRoot();
    }

    private void initializeAddFragment() {
        binding.deleteMealButton.setVisibility(View.INVISIBLE);
        initializeEditMealButton();
    }

    private void initializeEditMealButton() {
        binding.editMealButton.setOnClickListener(v -> {
            if (isAnyFieldBlank()) {
                Toast errorMsg = Toast.makeText(getActivity(), R.string.empty_fields, Toast.LENGTH_LONG);
                errorMsg.show();
            } else if (editing){
                Toast errorMsg = Toast.makeText(getActivity(), "Success on editing", Toast.LENGTH_LONG);
                errorMsg.show();
            } else {
                Toast errorMsg = Toast.makeText(getActivity(), "Success on adding", Toast.LENGTH_LONG);
                errorMsg.show();
            }
        });
    }

    private boolean isAnyFieldBlank() {
        boolean mealNameEmpty = "".equals(Objects.requireNonNull(binding.inputMealName.getText()).toString());
        boolean mealKcalEmpty = "".equals(Objects.requireNonNull(binding.inputMealCal.getText()).toString());
        return mealNameEmpty || mealKcalEmpty || !isMealDateSelected;
    }

    private void initializeEditFragment() {
        binding.editMealButton.setText(getResources().getText(R.string.update_meal));
        binding.inputMealName.setText(meal.getMealName());
        binding.inputMealCal.setText(String.valueOf(meal.getKcal()));
        initializeEditMealButton();
        initializeRemoveMealButton();
        DateTime mealDate = meal.getMealDate();
        System.out.println("To String result: " + mealDate.toString());
        String dateString = mealDate.getDay() + "/" + mealDate.getMonth() + "/" + mealDate.getYear();
        System.out.println("My String result: " + dateString);
        binding.inputMealDate.setText(dateString);
    }

    private void initializeRemoveMealButton() {
        binding.deleteMealButton.setOnClickListener(v -> {
            Toast errorMsg = Toast.makeText(getActivity(), "Success on deleting", Toast.LENGTH_LONG);
            errorMsg.show();
        });
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

    /**
     * Sets the calendar picker.
     */
    private void setCalendarPicker() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.select_birth_date));
        materialDatePicker = builder.build();

        mealDate = binding.inputMealDate;
        mealDate.setOnClickListener(v ->
            materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), "DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            mealDate.setText(materialDatePicker.getHeaderText());
            isMealDateSelected = true;
        });
    }
}

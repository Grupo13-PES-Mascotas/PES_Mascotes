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

    /**
     * Method responsible for initializing the add meal view.
     */
    private void initializeAddFragment() {
        binding.deleteMealButton.setVisibility(View.INVISIBLE);
        initializeEditMealButton();
    }

    /**
     * Method responsible for initializing the edit meal fragment.
     */
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

    /**
     * Method responsible for initializing the edit/add meal button
     */
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

    /**
     * Method responsible for checking if there is any empty field.
     * @return True if there is any empty field or false otherwise
     */
    private boolean isAnyFieldBlank() {
        boolean mealNameEmpty = "".equals(Objects.requireNonNull(binding.inputMealName.getText()).toString());
        boolean mealKcalEmpty = "".equals(Objects.requireNonNull(binding.inputMealCal.getText()).toString());
        return mealNameEmpty || mealKcalEmpty || !isMealDateSelected;
    }

    /**
     * Method responsible for initializing the remove meal button.
     */
    private void initializeRemoveMealButton() {
        binding.deleteMealButton.setOnClickListener(v -> {
            Toast errorMsg = Toast.makeText(getActivity(), "Success on deleting", Toast.LENGTH_LONG);
            errorMsg.show();
        });
    }

    /**
     * Getter of the pet associated with the meal of the fragment.
     * @return The pet associated with the meal of the fragment
     */
    public static Pet getPet() {
        return pet;
    }

    /**
     * Setter of the pet associated with the meal of the fragment.
     * @param pet The pet associated with the meal of the fragment
     */
    public static void setPet(Pet pet) {
        EditMealFragment.pet = pet;
    }

    /**
     * Getter of the meal of the fragment.
     * @return The meal of the fragment if editing is true or null otherwise
     */
    public static Meals getMeal() {
        return meal;
    }

    /**
     * Setter of the meal of the fragment.
     * @param meal The meal that has to be associated to the fragment
     */
    public static void setMeal(Meals meal) {
        EditMealFragment.meal = meal;
    }

    /**
     * Setter of the editing attribute.
     * @param editing True if we are editing and existing meal or false if we are adding a meal
     */
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

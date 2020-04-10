package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.datepicker.MaterialDatePicker;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentEditMealBinding;
import org.pesmypetcare.mypetcare.features.pets.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.utilities.DateConversion;
import org.pesmypetcare.mypetcare.utilities.DateTime;

import java.util.Calendar;
import java.util.Objects;


public class EditMealFragment extends Fragment {
    private static final String SEPARATOR = "/";
    public static final int FIRST_TWO_DIGITS = 10;
    private static Pet pet;
    private static Meals meal;
    private static boolean editing;
    private static InfoPetCommunication communication;
    private FragmentEditMealBinding binding;
    private Button mealDate;
    private MaterialDatePicker materialDatePicker;
    private Button mealTime;
    private TimePicker timePicker;
    private boolean isMealDateSelected;
    private int selectedHour;
    private int selectedMin;
    private boolean isMealTimeSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditMealBinding.inflate(inflater, container, false);
        communication = (InfoPetCommunication) getActivity();
        setCalendarPicker();
        setTimePicker();
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
        DateTime mealDate = meal.getMealDate();
        String dateString = mealDate.getDay() + SEPARATOR + mealDate.getMonth() + SEPARATOR + mealDate.getYear();
        binding.inputMealDate.setText(dateString);
        initializeEditMealButton();
        initializeRemoveMealButton();
    }

    /**
     * Method responsible for initializing the edit/add meal button.
     */
    private void initializeEditMealButton() {
        binding.editMealButton.setOnClickListener(v -> {
            if (isAnyFieldBlank()) {
                Toast errorMsg = Toast.makeText(getActivity(), R.string.empty_fields, Toast.LENGTH_LONG);
                errorMsg.show();
            } else if (editing) {
                Toast errorMsg = Toast.makeText(getActivity(), "Success on editing", Toast.LENGTH_LONG);
                errorMsg.show();
            } else {
                initializeAddButtonListener();
                FragmentTransaction ft = Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.mainActivityFrameLayout, new InfoPetMealsFragment());
                ft.commit();
            }
        });
    }

    private void initializeAddButtonListener() {
        StringBuilder dateString = new StringBuilder(DateConversion.convertToServer(binding.inputMealDate.getText().toString()));
        dateString.append('T');
        if (selectedHour < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(selectedHour).append(':');
        if (selectedMin < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(selectedMin).append(':');
        dateString.append("00");
        DateTime mealDate = new DateTime(dateString.toString());
        double kcal = Double.parseDouble(Objects.requireNonNull(binding.inputMealCal.getText()).toString());
        String mealName = Objects.requireNonNull(binding.inputMealName.getText()).toString();
        meal = new Meals(mealDate, kcal, mealName);
        try {
            communication.addPetMeal(pet, meal);
        } catch (MealAlreadyExistingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method responsible for checking if there is any empty field.
     * @return True if there is any empty field or false otherwise
     */
    private boolean isAnyFieldBlank() {
        boolean mealNameEmpty = "".equals(Objects.requireNonNull(binding.inputMealName.getText()).toString());
        boolean mealKcalEmpty = "".equals(Objects.requireNonNull(binding.inputMealCal.getText()).toString());
        return mealNameEmpty || mealKcalEmpty || !isMealDateSelected  || !isMealTimeSelected;
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

    private void setTimePicker() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        mealTime = binding.inputMealTime;
        mealTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedHour = hourOfDay;
                        selectedMin = minute;
                        isMealTimeSelected = true;
                    }
                }, hour, min, true);
                timePickerDialog.show();
            }
        });
    }
}

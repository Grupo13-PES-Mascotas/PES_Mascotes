package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetMealsBinding;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


/**
 * @author Xavier Campos
 */
public class InfoPetMealsFragment extends Fragment {
    private static final String EOL = "\n";
    private static final int STROKE_WIDTH = 5;
    private static final String SPACE = " ";
    private static final String DATESEPARATOR = "-";
    private static final String TIMESEPARATOR = ":";
    private static final int FIRST_TWO_DIGITS = 10;
    private static final String DEFAULT_SECONDS = "00";
    private static boolean editing;
    private static Meals meal;

    private FragmentInfoPetMealsBinding binding;
    private SwitchMaterial intervalSelector;
    private boolean isWeeklyInterval;
    private Pet pet;
    private LinearLayout mealDisplay;
    private Button addMealButton;
    private MaterialButton mealDate;
    private MaterialDatePicker materialDatePicker;
    private boolean isMealDateSelected;
    private boolean updatesDate;
    private int selectedHour;
    private int selectedMin;
    private MaterialButton mealTime;
    private boolean isMealTimeSelected;
    private MaterialButton editMealButton;
    private TextInputEditText inputMealName;
    private TextInputEditText inputMealCal;
    private MaterialButton deleteMealButton;
    private AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetMealsBinding.inflate(inflater, container, false);
        pet = InfoPetFragment.getPet();

        mealDisplay = binding.mealsDisplayLayout;
        addMealButton = binding.addMealButton;
        View editMealLayout = prepareDialog();
        dialog = getBasicMealDialog();
        dialog.setView(editMealLayout);

        initializeEditMealButton();
        initializeRemoveMealButton();
        initializeIntervalSwitch();
        initializeAddMealButton();

        return binding.getRoot();
    }

    /**
     * Method responsible for initializing the add meal button.
     */
    private void initializeAddMealButton() {
        addMealButton.setOnClickListener(v -> {
            editing = false;
            deleteMealButton.setVisibility(View.INVISIBLE);
            inputMealName.setText("");
            inputMealCal.setText("");
            mealDate.setText(R.string.meal_date);
            mealTime.setText(R.string.meal_time);
            editMealButton.setText(R.string.add_meal_button);
            dialog.setTitle(R.string.add_meal_button);
            dialog.show();
        });
    }

    /**
     * Create the basic meal dialog.
     * @return The basic main dialog
     */
    private AlertDialog getBasicMealDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),
            R.style.AlertDialogTheme);
        dialog.setTitle(R.string.edit_meal_title);
        dialog.setMessage(R.string.edit_meal_message);
        return dialog.create();
    }

    /**
     * Prepare the meal dialog.
     * @return The layout of the main dialog
     */
    private View prepareDialog() {
        View editMealLayout = getLayoutInflater().inflate(R.layout.edit_meal, null);
        inputMealName = editMealLayout.findViewById(R.id.inputMealName);
        inputMealCal = editMealLayout.findViewById(R.id.inputMealCal);
        editMealButton = editMealLayout.findViewById(R.id.editMealButton);
        deleteMealButton = editMealLayout.findViewById(R.id.deleteMealButton);
        mealDate = editMealLayout.findViewById(R.id.inputMealDate);
        mealTime = editMealLayout.findViewById(R.id.inputMealTime);

        setCalendarPicker();
        setTimePicker();
        return editMealLayout;
    }

    /**
     * Method responsible for initializing the remove meal button.
     */
    private void initializeRemoveMealButton() {
        deleteMealButton.setOnClickListener(v -> {
            InfoPetFragment.getCommunication().deletePetMeal(pet, meal);
            initializeMealsLayoutView();
            dialog.dismiss();
        });
    }

    /**
     * Method responsible for initializing the string for the inputMealDate button.
     * @param mealDate The date of the meal
     */
    private void showMealDate(DateTime mealDate) {
        StringBuilder dateString = new StringBuilder();
        dateString.append(mealDate.getYear()).append(DATESEPARATOR);
        if (mealDate.getMonth() < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(mealDate.getMonth()).append(DATESEPARATOR);
        if (mealDate.getDay() < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(mealDate.getDay());
        this.mealDate.setText(dateString);
    }

    /**
     * Method responsible for initializing the string for the inputMealTime button.
     * @param mealDate The date of the meal
     */
    private void showMealTime(DateTime mealDate) {
        StringBuilder timeString = new StringBuilder();
        if (mealDate.getHour() < FIRST_TWO_DIGITS) {
            timeString.append('0');
        }
        timeString.append(mealDate.getHour()).append(TIMESEPARATOR);
        if (mealDate.getMinutes() < FIRST_TWO_DIGITS) {
            timeString.append('0');
        }
        timeString.append(mealDate.getMinutes()).append(TIMESEPARATOR).append(DEFAULT_SECONDS);
        mealTime.setText(timeString);
        selectedHour = mealDate.getHour();
        selectedMin = mealDate.getMinutes();
    }

    /**
     * Initialize the edit meal button.
     */
    private void initializeEditMealButton() {
        editMealButton.setOnClickListener(v -> {
            if (isAnyFieldBlank()) {
                showErrorMessage();
            } else {
                if (editing) {
                    initializeEditButtonListener();
                } else {
                    initializeAddButtonListener();
                }

                dialog.dismiss();
                initializeMealsLayoutView();
            }
        });
    }

    /**
     * Show the error message.
     */
    private void showErrorMessage() {
        Toast errorMsg = Toast.makeText(getActivity(), R.string.empty_fields, Toast.LENGTH_LONG);
        errorMsg.show();
    }

    /**
     * Method responsible for initializing the addButton listener.
     */
    private void initializeAddButtonListener() {
        DateTime mealDate = getDateTime();
        double kcal = Double.parseDouble(Objects.requireNonNull(inputMealCal.getText()).toString());
        String mealName = Objects.requireNonNull(inputMealName.getText()).toString();
        meal = new Meals(mealDate, kcal, mealName);
        try {
            InfoPetFragment.getCommunication().addPetMeal(pet, meal);
        } catch (MealAlreadyExistingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method responsible for initializing the editButton listener.
     */
    private void initializeEditButtonListener() {
        String newDate = getDateTime().toString();
        String mealName = Objects.requireNonNull(inputMealName.getText()).toString();
        double kcal = Double.parseDouble(Objects.requireNonNull(inputMealCal.getText()).toString());
        meal.setMealName(mealName);
        meal.setKcal(kcal);
        InfoPetFragment.getCommunication().updatePetMeal(pet, meal, newDate, updatesDate);
        if (updatesDate) {
            meal.setMealDate(DateTime.Builder.buildFullString(newDate));
        }
    }

    /**
     * Method responsible for obtaining the date of the meal in the current format.
     * @return The dateTime of the meal
     */
    private DateTime getDateTime() {
        StringBuilder dateString = new StringBuilder(mealDate.getText().toString());
        dateString.append('T');
        if (selectedHour < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(selectedHour).append(':');
        if (selectedMin < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(selectedMin).append(':').append(DEFAULT_SECONDS);
        return DateTime.Builder.buildFullString(dateString.toString());
    }

    /**
     * Method responsible for checking if there is any empty field.
     * @return True if there is any empty field or false otherwise
     */
    private boolean isAnyFieldBlank() {
        boolean mealNameEmpty = "".equals(Objects.requireNonNull(inputMealName.getText()).toString());
        boolean mealKcalEmpty = "".equals(Objects.requireNonNull(inputMealCal.getText()).toString());
        if (editing) {
            return mealKcalEmpty || mealNameEmpty;
        }
        return mealNameEmpty || mealKcalEmpty || !isMealDateSelected || !isMealTimeSelected;
    }

    /**
     * Sets the time picker.
     */
    private void setTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        mealTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), (view, hourOfDay, minute) ->
                initializeTimePickerDialog(hourOfDay, minute), hour, min, true);
            timePickerDialog.show();
        });

    }

    /**
     * Method responsible for initializing the timePickerDialog.
     * @param hourOfDay The selected value for the hour
     * @param minute The selected value for the minutes
     */
    private void initializeTimePickerDialog(int hourOfDay, int minute) {
        selectedHour = hourOfDay;
        selectedMin = minute;
        StringBuilder time = formatTimePickerText();
        mealTime.setText(time);
        isMealTimeSelected = true;
        updatesDate = true;
    }

    /**
     * Method responsible for formatting the text for the time picker.
     * @return An stringbuilder containing the time in the correct format
     */
    private StringBuilder formatTimePickerText() {
        StringBuilder time = new StringBuilder();
        if (selectedHour < FIRST_TWO_DIGITS) {
            time.append('0');
        }
        time.append(selectedHour).append(':');
        if (selectedMin < FIRST_TWO_DIGITS) {
            time.append('0');
        }
        time.append(selectedMin).append(':').append(DEFAULT_SECONDS);
        return time;
    }

    /**
     * Sets the calendar picker.
     */
    private void setCalendarPicker() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.select_meal_date));
        materialDatePicker = builder.build();

        mealDate.setOnClickListener(v ->
            materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), "DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(this::initializeOnPositiveButtonClickListener);
    }

    /**
     * Method responsible for initializing the onPositiveButtonClickListener.
     * @param selection The selected value
     */
    private void initializeOnPositiveButtonClickListener(Object selection) {
        mealDate.setText(materialDatePicker.getHeaderText());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(selection.toString()));
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        mealDate.setText(formattedDate);
        isMealDateSelected = true;
        updatesDate = true;
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
        ArrayList<Event> mealsList;
        mealDisplay.removeAllViews();

        if (isWeeklyInterval) {
            mealsList = (ArrayList<Event>) getLastWeekMeals();
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
        initializeButtonParams(mealButton);
        initializeButtonLogic((Meals) meal, mealButton);
        mealDisplay.addView(mealButton);
    }

    /**
     * Method responsible for initializing the button logic.
     * @param meal The meal for which we want to initialize a button
     * @param mealButton The button that has to be initialized
     */
    private void initializeButtonLogic(Meals meal, MaterialButton mealButton) {
        String mealButtonText = getString(R.string.meal) + SPACE + meal.getMealName() + EOL
            + getString(R.string.from_date) + SPACE + meal.getDateTime() + EOL + getString(R.string.meal_kcal)
            + ": " + meal.getKcal();
        mealButton.setText(mealButtonText);
        mealButton.setOnClickListener(v -> {
            InfoPetMealsFragment.meal = meal;
            editing = true;
            initializeEditDialog();
            deleteMealButton.setVisibility(View.VISIBLE);
            dialog.setTitle(R.string.edit_meal_title);
            dialog.show();
        });
    }

    /**
     * Method responsible for initializing the edit meal dialog.
     */
    private void initializeEditDialog() {
        editMealButton.setText(R.string.update_meal);
        inputMealName.setText(meal.getMealName());
        inputMealCal.setText(String.valueOf(meal.getKcal()));
        updatesDate = false;
        DateTime mealDate = meal.getMealDate();
        showMealDate(mealDate);
        showMealTime(mealDate);
    }

    /**
     * Method responsible for initializing the button parameters.
     * @param mealButton The button that has to be initialized
     */
    private void initializeButtonParams(MaterialButton mealButton) {
        mealButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        mealButton.setBackgroundColor(getResources().getColor(R.color.white, null));
        mealButton.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        mealButton.setStrokeColorResource(R.color.colorAccent);
        mealButton.setStrokeWidth(STROKE_WIDTH);
        mealButton.setGravity(Gravity.START);
    }

    /**
     * Method responsible for obtaining all the meals from the last week.
     * @return All the meals from the last week
     */
    private List<Event> getLastWeekMeals() {
        ArrayList<Event> result = new ArrayList<>();
        result.clear();
        List<Event> aux = pet.getMealEvents();
        for (Event e:aux) {
            boolean calc = DateTime.isLastWeek(e.getDateTime().toString());
            if (calc) {
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeMealsLayoutView();
    }
}

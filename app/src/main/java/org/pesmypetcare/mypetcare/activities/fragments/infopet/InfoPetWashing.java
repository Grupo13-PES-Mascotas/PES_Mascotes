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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetWashingBinding;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Wash;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class InfoPetWashing extends Fragment {
    private static final String EOL = "\n";
    private static final int FIRST_TWO_DIGITS = 10;
    private static final String DEFAULT_SECONDS = "00";
    private static final int STROKE_WIDTH = 5;
    private static final String SPACE = " ";
    private static final String DATESEPARATOR = "-";
    private static final String TIMESEPARATOR = ":";
    private static boolean editing;
    private static Wash wash;

    private FragmentInfoPetWashingBinding binding;
    private SwitchMaterial intervalSelector;
    private Pet pet;
    private LinearLayout washDisplay;
    private boolean isWeeklyInterval;
    private Button addWashButton;
    private MaterialButton washDate;
    private MaterialDatePicker materialDatePicker;
    private boolean isWashDateSelected;
    private boolean updatesDate;
    private int selectedHour;
    private int selectedMin;
    private MaterialButton washTime;
    private boolean isWashTimeSelected;
    private MaterialButton editWashButton;
    private TextInputEditText inputWashName;
    private TextInputEditText inputWashDuration;
    private MaterialButton deleteWashButton;
    private AlertDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetWashingBinding.inflate(inflater, container, false);
        pet = InfoPetFragment.getPet();

        washDisplay = binding.washDisplayLayout;
        addWashButton = binding.addWashButton;
        View editWashLayout = prepareDialog();
        dialog = getBasicWashDialog();
        dialog.setView(editWashLayout);

        initializeEditWashButton();
        initializeRemoveWashButton();
        initializeIntervalSwitch();
        initializeAddWashButton();

        return binding.getRoot();
    }

    /**
     * Prepare the wash dialog.
     * @return The layout of the main dialog
     */
    private View prepareDialog() {
        View editWashLayout = getLayoutInflater().inflate(R.layout.edit_wash, null);
        inputWashName = editWashLayout.findViewById(R.id.inputWashName);
        inputWashDuration = editWashLayout.findViewById(R.id.inputWashDuration);
        editWashButton = editWashLayout.findViewById(R.id.editWashButton);
        deleteWashButton = editWashLayout.findViewById(R.id.deleteWashButton);
        washDate = editWashLayout.findViewById(R.id.inputWashDate);
        washTime = editWashLayout.findViewById(R.id.inputWashTime);

        setCalendarPicker();
        setTimePicker();
        return editWashLayout;
    }

    /**
     * Sets the calendar picker.
     */
    private void setCalendarPicker() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.select_wash_date));
        materialDatePicker = builder.build();

        washDate.setOnClickListener(v ->
                materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), "DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(this::initializeOnPositiveButtonClickListener);
    }

    /**
     * Method responsible for initializing the onPositiveButtonClickListener.
     * @param selection The selected value
     */
    private void initializeOnPositiveButtonClickListener(Object selection) {
        washDate.setText(materialDatePicker.getHeaderText());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(selection.toString()));
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        washDate.setText(formattedDate);
        isWashDateSelected = true;
        updatesDate = true;
    }

    /**
     * Sets the time picker.
     */
    private void setTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        washTime.setOnClickListener(v -> {
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
        washTime.setText(time);
        isWashTimeSelected = true;
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
     * Create the basic wash dialog.
     * @return The basic main dialog
     */
    private AlertDialog getBasicWashDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),
                R.style.AlertDialogTheme);
        dialog.setTitle(R.string.edit_wash_title);
        dialog.setMessage(R.string.edit_wash_message);
        return dialog.create();
    }

    /**
     * Initialize the edit wash button.
     */
    private void initializeEditWashButton() {
        editWashButton.setOnClickListener(v -> {
            if (isAnyFieldBlank()) {
                showErrorMessage();
            } else {
                if (editing) {
                    initializeEditButtonListener();
                } else {
                    initializeAddButtonListener();
                }

                dialog.dismiss();
                initializeWashLayoutView();
            }
        });
    }

    /**
     * Method responsible for checking if there is any empty field.
     * @return True if there is any empty field or false otherwise
     */
    private boolean isAnyFieldBlank() {
        boolean washNameEmpty = "".equals(Objects.requireNonNull(inputWashName.getText()).toString());
        boolean washDurationEmpty = "".equals(Objects.requireNonNull(inputWashDuration.getText()).toString());
        if (editing) {
            return washNameEmpty || washDurationEmpty;
        }
        return washNameEmpty || washDurationEmpty || !isWashDateSelected || !isWashTimeSelected;
    }

    /**
     * Show the error message.
     */
    private void showErrorMessage() {
        Toast errorMsg = Toast.makeText(getActivity(), R.string.empty_fields, Toast.LENGTH_LONG);
        errorMsg.show();
    }

    /**
     * Method responsible for initializing the editButton listener.
     */
    private void initializeEditButtonListener() {
        String newDate = getDateTime().toString();
        String washName = Objects.requireNonNull(inputWashName.getText()).toString();
        int duration = Integer.parseInt(Objects.requireNonNull(inputWashDuration.getText()).toString());
        wash.setWashName(washName);
        wash.setDuration(duration);
        //InfoPetFragment.getCommunication().updatePetWash(pet, wash, newDate, updatesDate);
        if (updatesDate) {
            wash.setWashDate(DateTime.Builder.buildFullString(newDate));
        }
    }

    /**
     * Method responsible for obtaining the date of the wash in the current format.
     * @return The dateTime of the wash
     */
    private DateTime getDateTime() {
        StringBuilder dateString = new StringBuilder(washDate.getText().toString());
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
     * Method responsible for initializing the addButton listener.
     */
    private void initializeAddButtonListener() {
        DateTime washDate = getDateTime();
        int duration = Integer.parseInt(Objects.requireNonNull(inputWashDuration.getText()).toString());
        String washName = Objects.requireNonNull(inputWashName.getText()).toString();
        wash = new Wash(washDate, duration, washName);
        /*try {
            InfoPetFragment.getCommunication().addPetWash(pet, wash);
        } catch (WashAlreadyExistingException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * Method responsible for initializing the remove wash button.
     */
    private void initializeRemoveWashButton() {
        deleteWashButton.setOnClickListener(v -> {
            //InfoPetFragment.getCommunication().deletePetWash(pet, wash);
            initializeWashLayoutView();
            dialog.dismiss();
        });
    }

    /**
     * Method responsible for initializing the wash layout view.
     */
    private void initializeWashLayoutView() {
        ArrayList<Event> washList;
        washDisplay.removeAllViews();

        if (isWeeklyInterval) {
            washList = (ArrayList<Event>) getLastWeekWashes();
        } else {
            washList = (ArrayList<Event>) pet.getWashEvents();
        }

        for (Event wash : washList) {
            initializeWashComponent(wash);
        }
    }

    /**
     * Method responsible for obtaining all the washes from the last week.
     * @return All the washes from the last week
     */
    private List<Event> getLastWeekWashes() {
        ArrayList<Event> result = new ArrayList<>();
        result.clear();
        List<Event> aux = pet.getWashEvents();
        for (Event e:aux) {
            boolean calc = DateTime.isLastWeek(e.getDateTime().toString());
            if (calc) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * Method responsible for initializing each wash component.
     * @param wash The wash for which we want to initialize the component
     */
    private void initializeWashComponent(Event wash) {
        MaterialButton washButton = new MaterialButton(Objects.requireNonNull(this.getActivity()), null);
        initializeButtonParams(washButton);
        initializeButtonLogic((Wash) wash, washButton);
        washDisplay.addView(washButton);
    }

    /**
     * Method responsible for initializing the button logic.
     * @param wash The wash for which we want to initialize a button
     * @param washButton The button that has to be initialized
     */
    private void initializeButtonLogic(Wash wash, MaterialButton washButton) {
        String washButtonText = getString(R.string.wash) + SPACE + wash.getWashName() + EOL
                + getString(R.string.from_date) + SPACE + wash.getDateTime() + EOL + getString(R.string.wash_duration)
                + ": " + wash.getDuration();
        washButton.setText(washButtonText);
        washButton.setOnClickListener(v -> {
            InfoPetWashing.wash = wash;
            editing = true;
            initializeEditDialog();
            deleteWashButton.setVisibility(View.VISIBLE);
            dialog.setTitle(R.string.edit_wash_title);
            dialog.show();
        });
    }

    /**
     * Method responsible for initializing the edit wash dialog.
     */
    private void initializeEditDialog() {
        editWashButton.setText(R.string.update_wash);
        inputWashName.setText(wash.getWashName());
        inputWashDuration.setText(String.valueOf(wash.getDuration()));
        updatesDate = false;
        DateTime washDate = wash.getWashDate();
        showWashDate(washDate);
        showWashTime(washDate);
    }

    /**
     * Method responsible for initializing the string for the inputWashDate button.
     * @param washDate The date of the wash
     */
    private void showWashDate(DateTime washDate) {
        StringBuilder dateString = new StringBuilder();
        dateString.append(washDate.getYear()).append(DATESEPARATOR);
        if (washDate.getMonth() < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(washDate.getMonth()).append(DATESEPARATOR);
        if (washDate.getDay() < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(washDate.getDay());
        this.washDate.setText(dateString);
    }

    /**
     * Method responsible for initializing the string for the inputWashTime button.
     * @param washDate The date of the wash
     */
    private void showWashTime(DateTime washDate) {
        StringBuilder timeString = new StringBuilder();
        if (washDate.getHour() < FIRST_TWO_DIGITS) {
            timeString.append('0');
        }
        timeString.append(washDate.getHour()).append(TIMESEPARATOR);
        if (washDate.getMinutes() < FIRST_TWO_DIGITS) {
            timeString.append('0');
        }
        timeString.append(washDate.getMinutes()).append(TIMESEPARATOR).append(DEFAULT_SECONDS);
        washTime.setText(timeString);
        selectedHour = washDate.getHour();
        selectedMin = washDate.getMinutes();
    }

    /**
     * Method responsible for initializing the button parameters.
     * @param washButton The button that has to be initialized
     */
    private void initializeButtonParams(MaterialButton washButton) {
        washButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        washButton.setBackgroundColor(getResources().getColor(R.color.white));
        washButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        washButton.setStrokeColorResource(R.color.colorAccent);
        washButton.setStrokeWidth(STROKE_WIDTH);
        washButton.setGravity(Gravity.START);
    }

    /**
     * Method responsible for initializing the interval switch.
     */
    private void initializeIntervalSwitch() {
        intervalSelector = binding.washIntervalSelector;
        intervalSelector.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isWeeklyInterval = intervalSelector.isChecked();
            initializeWashLayoutView();
        });
    }

    /**
     * Method responsible for initializing the add wash button.
     */
    private void initializeAddWashButton() {
        addWashButton.setOnClickListener(v -> {
            editing = false;
            deleteWashButton.setVisibility(View.INVISIBLE);
            inputWashName.setText("");
            inputWashDuration.setText("");
            washDate.setText(R.string.wash_date);
            washTime.setText(R.string.wash_time);
            editWashButton.setText(R.string.add_wash_button);
            dialog.setTitle(R.string.add_wash_button);
            dialog.show();
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        initializeWashLayoutView();
    }
}

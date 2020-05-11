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
import com.google.android.material.textfield.TextInputEditText;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetMedicalProfileBinding;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class InfoPetMedicalProfile extends Fragment {
    private FragmentInfoPetMedicalProfileBinding binding;
    private static final String EOL = "\n";
    private static final int FIRST_TWO_DIGITS = 10;
    private static final String DEFAULT_SECONDS = "00";
    private static final int STROKE_WIDTH = 5;
    private static final String SPACE = " ";
    private static final String DATESEPARATOR = "-";
    private static final String TIMESEPARATOR = ":";
    private static boolean editing;
    private static Vaccination vaccination;

    private Pet pet;
    private LinearLayout vaccinationDisplay;
    private Button addVaccinationButton;
    private MaterialButton vaccinationDate;
    private MaterialDatePicker materialDatePicker;
    private boolean isVaccinationDateSelected;
    private boolean isVaccinationTimeSelected;
    private boolean updatesDate;
    private int selectedHour;
    private int selectedMin;
    private MaterialButton vaccinationTime;
    private MaterialButton editVaccinationButton;
    private TextInputEditText inputVaccinationDescription;
    private MaterialButton deleteVaccinationButton;
    private AlertDialog dialog;

    private LinearLayout illnessDisplay;
    private Button addIllnessButton;
    private MaterialButton illnessDate;
    private boolean isIllnessDateSelected;
    private boolean isIllnessTimeSelected;
    private boolean illnessUpdatesDate;
    private int illnessSelectedHour;
    private int illnessSelectedMin;
    private MaterialButton illnessTime;
    private MaterialButton editIllnessButton;
    private TextInputEditText inputIllnessDescription;
    private MaterialButton deleteIllnessButton;
    private AlertDialog illnessDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetMedicalProfileBinding.inflate(inflater, container, false);
        pet = InfoPetFragment.getPet();

        vaccinationDisplay = binding.vaccinationDisplayLayout;
        addVaccinationButton = binding.addVaccinationsButton;
        View editVaccinationLayout = prepareDialog();
        dialog = getBasicVaccinationDialog();
        dialog.setView(editVaccinationLayout);

        initializeEditVaccinationButton();
        initializeRemoveVaccinationButton();
        initializeAddVaccinationButton();

        return binding.getRoot();
    }

    /**
     * Prepare the Vaccination dialog.
     * @return The layout of the main dialog
     */
    private View prepareDialog() {
        View editVaccinationLayout = getLayoutInflater().inflate(R.layout.edit_vaccination, null);
        inputVaccinationDescription = editVaccinationLayout.findViewById(R.id.inputVaccinationDescription);
        editVaccinationButton = editVaccinationLayout.findViewById(R.id.editVaccinationButton);
        deleteVaccinationButton = editVaccinationLayout.findViewById(R.id.deleteVaccinationButton);
        vaccinationDate = editVaccinationLayout.findViewById(R.id.inputVaccinationDate);
        vaccinationTime = editVaccinationLayout.findViewById(R.id.inputVaccinationTime);

        setCalendarPicker();
        setTimePicker();
        return editVaccinationLayout;
    }

    /**
     * Sets the calendar picker.
     */
    private void setCalendarPicker() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.select_vaccination_date));
        materialDatePicker = builder.build();

        vaccinationDate.setOnClickListener(v ->
                materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), "DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(this::initializeOnPositiveButtonClickListener);
    }

    /**
     * Method responsible for initializing the onPositiveButtonClickListener.
     * @param selection The selected value
     */
    private void initializeOnPositiveButtonClickListener(Object selection) {
        vaccinationDate.setText(materialDatePicker.getHeaderText());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(selection.toString()));
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        vaccinationDate.setText(formattedDate);
        isVaccinationDateSelected = true;
        updatesDate = true;
    }

    /**
     * Sets the time picker.
     */
    private void setTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        vaccinationTime.setOnClickListener(v -> {
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
        vaccinationTime.setText(time);
        isVaccinationTimeSelected = true;
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
     * Create the basic vaccination dialog.
     * @return The basic vaccinationdialog
     */
    private AlertDialog getBasicVaccinationDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),
                R.style.AlertDialogTheme);
        dialog.setTitle(R.string.edit_vaccination_title);
        dialog.setMessage(R.string.edit_vaccination_message);
        return dialog.create();
    }

    /**
     * Initialize the edit vaccination button.
     */
    private void initializeEditVaccinationButton() {
        editVaccinationButton.setOnClickListener(v -> {
            if (isAnyFieldBlank()) {
                showErrorMessage();
            } else {
                if (editing) {
                    initializeEditButtonListener();
                } else {
                    initializeAddButtonListener();
                }

                dialog.dismiss();
                initializeVaccinationLayoutView();
            }
        });
    }

    /**
     * Method responsible for checking if there is any empty field.
     * @return True if there is any empty field or false otherwise
     */
    private boolean isAnyFieldBlank() {
        boolean vaccinationNameEmpty = "".equals(Objects.requireNonNull(inputVaccinationDescription.getText()).toString());
        if (editing) {
            return vaccinationNameEmpty;
        }
        return vaccinationNameEmpty || !isVaccinationDateSelected || !isVaccinationTimeSelected;
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
        String description = Objects.requireNonNull(inputVaccinationDescription.getText()).toString();
        vaccination.setDescription(description);
        //InfoPetFragment.getCommunication().updatePetVaccination(pet, vaccination, newDate, updatesDate);
        if (updatesDate) {
            vaccination.setVaccinationDate(DateTime.Builder.buildDateString(newDate));
        }
    }

    /**
     * Method responsible for obtaining the date of the wash in the current format.
     * @return The dateTime of the wash
     */
    private DateTime getDateTime() {
        StringBuilder dateString = new StringBuilder(vaccinationDate.getText().toString());
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
        DateTime vaccinationDate = getDateTime();
        String description = Objects.requireNonNull(inputVaccinationDescription.getText()).toString();
        vaccination = new Vaccination(description, vaccinationDate);
        InfoPetFragment.getCommunication().addVaccination(pet, description, vaccinationDate);
    }

    /**
     * Method responsible for initializing the remove vaccination button.
     */
    private void initializeRemoveVaccinationButton() {
        deleteVaccinationButton.setOnClickListener(v -> {
            //InfoPetFragment.getCommunication().deletePetVaccination(pet, vaccination);
            initializeVaccinationLayoutView();
            dialog.dismiss();
        });
    }

    /**
     * Method responsible for initializing the vaccination layout view.
     */
    private void initializeVaccinationLayoutView() {
        ArrayList<Event> vaccinationList;
        vaccinationDisplay.removeAllViews();


        vaccinationList = (ArrayList<Event>) pet.getWashEvents();

        for (Event vaccination : vaccinationList) {
            initializeVaccinationComponent(vaccination);
        }
    }

    /**
     * Method responsible for initializing each vaccination component.
     * @param vaccination The vaccination for which we want to initialize the component
     */
    private void initializeVaccinationComponent(Event vaccination) {
        MaterialButton vaccinationButton = new MaterialButton(Objects.requireNonNull(this.getActivity()), null);
        initializeButtonParams(vaccinationButton);
        initializeButtonLogic((Vaccination) vaccination, vaccinationButton);
        vaccinationDisplay.addView(vaccinationButton);
    }

    /**
     * Method responsible for initializing the button logic.
     * @param vaccination The wash for which we want to initialize a button
     * @param vaccinationButton The button that has to be initialized
     */
    private void initializeButtonLogic(Vaccination vaccination, MaterialButton vaccinationButton) {
        String vaccinationButtonText = getString(R.string.vaccination) + SPACE + vaccination.getDescription() + EOL
                + getString(R.string.from_date) + SPACE + vaccination.getDateTime();
        vaccinationButton.setText(vaccinationButtonText);
        vaccinationButton.setOnClickListener(v -> {
            InfoPetMedicalProfile.vaccination = vaccination;
            editing = true;
            initializeEditDialog();
            deleteVaccinationButton.setVisibility(View.VISIBLE);
            dialog.setTitle(R.string.edit_vaccination_title);
            dialog.show();
        });
    }

    /**
     * Method responsible for initializing the edit wash dialog.
     */
    private void initializeEditDialog() {
        editVaccinationButton.setText(R.string.update_vaccination);
        inputVaccinationDescription.setText(vaccination.getDescription());
        updatesDate = false;
        DateTime vaccinationDate = vaccination.getVaccinationDate();
        showVaccinationDate(vaccinationDate);
        showVaccinationTime(vaccinationDate);
    }

    /**
     * Method responsible for initializing the string for the inputVaccinationDate button.
     * @param vaccinationDate The date of the vaccination
     */
    private void showVaccinationDate(DateTime vaccinationDate) {
        StringBuilder dateString = new StringBuilder();
        dateString.append(vaccinationDate.getYear()).append(DATESEPARATOR);
        if (vaccinationDate.getMonth() < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(vaccinationDate.getMonth()).append(DATESEPARATOR);
        if (vaccinationDate.getDay() < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(vaccinationDate.getDay());
        this.vaccinationDate.setText(dateString);
    }

    /**
     * Method responsible for initializing the string for the inputVaccinationTime button.
     * @param vaccinationDate The date of the vaccination
     */
    private void showVaccinationTime(DateTime vaccinationDate) {
        StringBuilder timeString = new StringBuilder();
        if (vaccinationDate.getHour() < FIRST_TWO_DIGITS) {
            timeString.append('0');
        }
        timeString.append(vaccinationDate.getHour()).append(TIMESEPARATOR);
        if (vaccinationDate.getMinutes() < FIRST_TWO_DIGITS) {
            timeString.append('0');
        }
        timeString.append(vaccinationDate.getMinutes()).append(TIMESEPARATOR).append(DEFAULT_SECONDS);
        vaccinationTime.setText(timeString);
        selectedHour = vaccinationDate.getHour();
        selectedMin = vaccinationDate.getMinutes();
    }

    /**
     * Method responsible for initializing the button parameters.
     * @param vaccinationButton The button that has to be initialized
     */
    private void initializeButtonParams(MaterialButton vaccinationButton) {
        vaccinationButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        vaccinationButton.setBackgroundColor(getResources().getColor(R.color.white));
        vaccinationButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        vaccinationButton.setStrokeColorResource(R.color.colorAccent);
        vaccinationButton.setStrokeWidth(STROKE_WIDTH);
        vaccinationButton.setGravity(Gravity.START);
    }

    /**
     * Method responsible for initializing the add vaccination button.
     */
    private void initializeAddVaccinationButton() {
        addVaccinationButton.setOnClickListener(v -> {
            editing = false;
            deleteVaccinationButton.setVisibility(View.INVISIBLE);
            inputVaccinationDescription.setText("");
            vaccinationDate.setText(R.string.vaccination_date);
            vaccinationTime.setText(R.string.vaccination_time);
            editVaccinationButton.setText(R.string.add_vaccinations_button);
            dialog.setTitle(R.string.add_vaccinations_button);
            dialog.show();
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        initializeVaccinationLayoutView();
    }
}

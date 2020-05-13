package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import org.pesmypetcare.mypetcare.features.pets.Illness;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class InfoPetMedicalProfile extends Fragment {
    private static final String EOL = "\n";
    private static final String DATESEPARATOR = "-";
    private static final String TIMESEPARATOR = ":";
    private static final String DEFAULT_SECONDS = "00";
    private static final String SPACE = " ";
    private static final String DATE_PICKER = "DATE_PICKER";
    private static final String LOW = "Low";
    private static final String MEDIUM = "Medium";
    private static final String HIGH = "High";
    private static final String NORMAL = "Normal";
    private static final String ALLERGY = "Allergy";
    private static final int FIRST_TWO_DIGITS = 10;
    private static final int STROKE_WIDTH = 5;
    private static boolean editing;
    private static boolean editingIllness;
    private static Vaccination vaccination;
    private static Illness illness;

    private FragmentInfoPetMedicalProfileBinding binding;

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
    private MaterialButton illnessEndDate;
    private MaterialDatePicker materialIllnessDatePicker;
    private MaterialDatePicker materialIllnessEndDatePicker;
    private boolean isIllnessDateSelected;
    private boolean isIllnessTimeSelected;
    private boolean isIllnessEndDateSelected;
    private boolean isIllnessEndTimeSelected;
    private int illnessSelectedHour;
    private int illnessSelectedMin;
    private int illnessSelectedEndHour;
    private int illnessSelectedEndMin;
    private boolean updatesIllnessDate;
    private boolean updatesIllnessEndDate;
    private MaterialButton illnessTime;
    private MaterialButton illnessEndTime;
    private MaterialButton editIllnessButton;
    private TextInputEditText inputIllnessDescription;
    private Spinner severity;
    private Spinner type;
    private MaterialButton deleteIllnessButton;
    private AlertDialog illnessDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetMedicalProfileBinding.inflate(inflater, container, false);
        pet = InfoPetFragment.getPet();

        initializeVaccinationDisplay();
        initializeIllnessDisplay();

        return binding.getRoot();
    }

    /**
     * Method responsible for initializing the illness display.
     */
    private void initializeIllnessDisplay() {
        illnessDisplay = binding.illnessesDisplayLayout;
        addIllnessButton = binding.addIllnessesButton;
        View editIllnessLayout = prepareIllnessDialog();
        illnessDialog = getBasicIllnessDialog();
        illnessDialog .setView(editIllnessLayout);
        initializeEditIllnessButton();
        initializeRemoveIllnessButton();
        initializeAddIllnessButton();
    }

    /**
     * Method responsible for initializing the vaccination display.
     */
    private void initializeVaccinationDisplay() {
        vaccinationDisplay = binding.vaccinationDisplayLayout;
        addVaccinationButton = binding.addVaccinationsButton;
        View editVaccinationLayout = prepareDialog();
        dialog = getBasicVaccinationDialog();
        dialog.setView(editVaccinationLayout);
        initializeEditVaccinationButton();
        initializeRemoveVaccinationButton();
        initializeAddVaccinationButton();
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
     * Prepare the Illness dialog.
     * @return The layout of the main dialog
     */
    private View prepareIllnessDialog() {
        View editIllnessLayout = getLayoutInflater().inflate(R.layout.edit_illness, null);
        inputIllnessDescription = editIllnessLayout.findViewById(R.id.inputIllnessDescription);
        severity = editIllnessLayout.findViewById(R.id.spinnerIllnessSeverity);
        type = editIllnessLayout.findViewById(R.id.spinnerIllnessType);
        editIllnessButton = editIllnessLayout.findViewById(R.id.editIllnessButton);
        deleteIllnessButton = editIllnessLayout.findViewById(R.id.deleteIllnessButton);
        illnessDate = editIllnessLayout.findViewById(R.id.inputIllnessDate);
        illnessEndDate = editIllnessLayout.findViewById(R.id.inputIllnessEndDate);
        illnessTime = editIllnessLayout.findViewById(R.id.inputIllnessTime);
        illnessEndTime = editIllnessLayout.findViewById(R.id.inputIllnessEndTime);

        setIllnessCalendarPicker();
        setIllnessTimePicker();
        return editIllnessLayout;
    }

    /**
     * Sets the calendar picker.
     */
    private void setCalendarPicker() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.select_vaccination_date));
        materialDatePicker = builder.build();

        vaccinationDate.setOnClickListener(v ->
                materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), DATE_PICKER));

        materialDatePicker.addOnPositiveButtonClickListener(this::initializeOnPositiveButtonClickListener);
    }

    /**
     * Sets the calendar picker.
     */
    private void setIllnessCalendarPicker() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.select_illness_date));
        materialIllnessDatePicker = builder.build();

        illnessDate.setOnClickListener(v ->
                materialIllnessDatePicker.show(Objects.requireNonNull(getFragmentManager()), DATE_PICKER));

        materialIllnessDatePicker.addOnPositiveButtonClickListener(this::initializeIllnessOnPositiveButtonClickListener);

        MaterialDatePicker.Builder builderEnd = MaterialDatePicker.Builder.datePicker();
        builderEnd.setTitleText(getString(R.string.illness_end_date));
        materialIllnessEndDatePicker = builderEnd.build();

        illnessEndDate.setOnClickListener(v ->
                materialIllnessEndDatePicker.show(Objects.requireNonNull(getFragmentManager()), DATE_PICKER));

        materialIllnessEndDatePicker.addOnPositiveButtonClickListener(this::initializeIllnessEndOnPositiveButtonClickListener);
    }

    /**
     * Method responsible for initializing the onPositiveButtonClickListener.
     * @param selection The selected value
     */
    private void initializeIllnessEndOnPositiveButtonClickListener(Object selection) {
        illnessEndDate.setText(materialIllnessEndDatePicker.getHeaderText());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(selection.toString()));
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        illnessEndDate.setText(formattedDate);
        isIllnessEndDateSelected = true;
        updatesIllnessEndDate = true;
    }

    /**
     * Method responsible for initializing the onPositiveButtonClickListener.
     * @param selection The selected value
     */
    private void initializeIllnessOnPositiveButtonClickListener(Object selection) {
        illnessDate.setText(materialIllnessDatePicker.getHeaderText());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(selection.toString()));
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        illnessDate.setText(formattedDate);
        isIllnessDateSelected = true;
        updatesDate = true;
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
     * Sets the time picker.
     */
    private void setIllnessTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        illnessTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), (view, hourOfDay, minute) ->
                    initializeIllnessTimePickerDialog(hourOfDay, minute), hour, min, true);
            timePickerDialog.show();
        });

        illnessEndTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), (view, hourOfDay, minute) ->
                    initializeIllnessEndTimePickerDialog(hourOfDay, minute), hour, min, true);
            timePickerDialog.show();
        });

    }

    /**
     * Method responsible for initializing the timePickerDialog.
     * @param hourOfDay The selected value for the hour
     * @param minute The selected value for the minutes
     */
    private void initializeIllnessEndTimePickerDialog(int hourOfDay, int minute) {
        illnessSelectedEndHour = hourOfDay;
        illnessSelectedEndMin = minute;
        StringBuilder time = formatIllnessEndTimePickerText();
        illnessEndTime.setText(time);
        isIllnessEndTimeSelected = true;
        updatesIllnessEndDate = true;
    }

    /**
     * Method responsible for formatting the text for the time picker.
     * @return An stringbuilder containing the time in the correct format
     */
    private StringBuilder formatIllnessEndTimePickerText() {
        StringBuilder time = new StringBuilder();
        if (illnessSelectedEndHour < FIRST_TWO_DIGITS) {
            time.append('0');
        }
        time.append(illnessSelectedEndHour).append(':');
        if (illnessSelectedEndMin < FIRST_TWO_DIGITS) {
            time.append('0');
        }
        time.append(illnessSelectedEndMin).append(':').append(DEFAULT_SECONDS);
        return time;
    }

    /**
     * Method responsible for initializing the timePickerDialog.
     * @param hourOfDay The selected value for the hour
     * @param minute The selected value for the minutes
     */
    private void initializeIllnessTimePickerDialog(int hourOfDay, int minute) {
        illnessSelectedHour = hourOfDay;
        illnessSelectedMin = minute;
        StringBuilder time = formatIllnessTimePickerText();
        illnessTime.setText(time);
        isIllnessTimeSelected = true;
        updatesIllnessDate = true;
    }

    /**
     * Method responsible for formatting the text for the time picker.
     * @return An stringbuilder containing the time in the correct format
     */
    private StringBuilder formatIllnessTimePickerText() {
        StringBuilder time = new StringBuilder();
        if (illnessSelectedHour < FIRST_TWO_DIGITS) {
            time.append('0');
        }
        time.append(illnessSelectedHour).append(':');
        if (illnessSelectedMin < FIRST_TWO_DIGITS) {
            time.append('0');
        }
        time.append(illnessSelectedMin).append(':').append(DEFAULT_SECONDS);
        return time;
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
     * @return The basic vaccination dialog
     */
    private AlertDialog getBasicVaccinationDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),
                R.style.AlertDialogTheme);
        dialog.setTitle(R.string.edit_vaccination_title);
        dialog.setMessage(R.string.edit_vaccination_message);
        return dialog.create();
    }

    /**
     * Create the basic illness dialog.
     * @return The basic illness dialog
     */
    private AlertDialog getBasicIllnessDialog() {
        AlertDialog.Builder illnessDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),
                R.style.AlertDialogTheme);
        illnessDialog.setTitle(R.string.edit_illness_title);
        illnessDialog.setMessage(R.string.edit_illness_message);
        return illnessDialog.create();
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
     * Initialize the edit illness button.
     */
    private void initializeEditIllnessButton() {
        editIllnessButton.setOnClickListener(v -> {
            if (isAnyIllnessFieldBlank()) {
                showErrorMessage();
            } else {
                if (editingIllness) {
                    initializeEditIllnessButtonListener();
                } else {
                    initializeAddIllnessButtonListener();
                }

                illnessDialog.dismiss();
                initializeIllnessLayoutView();
            }
        });
    }

    /**
     * Method responsible for checking if there is any empty field.
     * @return True if there is any empty field or false otherwise
     */
    private boolean isAnyIllnessFieldBlank() {
        boolean illnessNameEmpty = "".equals(Objects.requireNonNull(inputIllnessDescription.getText()).toString());
        if (editing) {
            return illnessNameEmpty;
        }
        boolean timesEmpty = !isIllnessTimeSelected || !isIllnessEndTimeSelected;
        boolean datesEmpty = !isIllnessDateSelected ||  !isIllnessEndDateSelected;
        return illnessNameEmpty || timesEmpty || datesEmpty;
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
    private void initializeEditIllnessButtonListener() {
        String newDate = getIllnessDateTime().toString();
        String description = Objects.requireNonNull(inputIllnessDescription.getText()).toString();
        illness.setDescription(description);
        illness.setSeverity(severity.getSelectedItem().toString());
        illness.setType(type.getSelectedItem().toString());
        illness.setEndTime(getIllnessEndDateTime());
        InfoPetFragment.getCommunication().updatePetIllness(pet, illness, newDate, updatesIllnessDate);
        if (updatesIllnessDate) {
            illness.setDateTime(DateTime.Builder.buildDateString(newDate));
        }
        if (updatesIllnessEndDate) {
            illness.setEndTime(getIllnessEndDateTime());
        }
    }

    /**
     * Method responsible for obtaining the date of the wash in the current format.
     * @return The dateTime of the wash
     */
    private DateTime getIllnessDateTime() {
        StringBuilder dateString = new StringBuilder(illnessDate.getText().toString());
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
     * Method responsible for obtaining the date of the wash in the current format.
     * @return The dateTime of the wash
     */
    private DateTime getIllnessEndDateTime() {
        StringBuilder dateString = new StringBuilder(illnessEndDate.getText().toString());
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
     * Method responsible for initializing the editButton listener.
     */
    private void initializeEditButtonListener() {
        String newDate = getDateTime().toString();
        String description = Objects.requireNonNull(inputVaccinationDescription.getText()).toString();
        vaccination.setDescription(description);
        InfoPetFragment.getCommunication().updatePetVaccination(pet, vaccination, newDate, updatesDate);
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
        InfoPetFragment.getCommunication().addPetVaccination(pet, description, vaccinationDate);
    }

    /**
     * Method responsible for initializing the addButton listener.
     */
    private void initializeAddIllnessButtonListener() {
        DateTime illnessDate = getIllnessDateTime();
        DateTime illnessEndDate = getIllnessEndDateTime();
        String description = Objects.requireNonNull(inputIllnessDescription.getText()).toString();
        illness = new Illness(description, illnessDate, illnessEndDate, type.getSelectedItem().toString(),
                severity.getSelectedItem().toString());
        InfoPetFragment.getCommunication().addPetIllness(pet, description, type.getSelectedItem().toString(),
                severity.getSelectedItem().toString(), illnessDate, illnessEndDate);
    }

    /**
     * Method responsible for initializing the remove vaccination button.
     */
    private void initializeRemoveVaccinationButton() {
        deleteVaccinationButton.setOnClickListener(v -> {
            InfoPetFragment.getCommunication().deletePetVaccination(pet, vaccination);
            initializeVaccinationLayoutView();
            dialog.dismiss();
        });
    }

    /**
     * Method responsible for initializing the remove illness button.
     */
    private void initializeRemoveIllnessButton() {
        deleteIllnessButton.setOnClickListener(v -> {
            InfoPetFragment.getCommunication().deletePetIllness(pet, illness);
            initializeIllnessLayoutView();
            illnessDialog.dismiss();
        });
    }

    /**
     * Method responsible for initializing the illness layout view.
     */
    private void initializeIllnessLayoutView() {
        ArrayList<Event> illnessList;
        illnessDisplay.removeAllViews();
        illnessList = (ArrayList<Event>) pet.getIllnessEvents();
        for (Event illness : illnessList) {
            initializeIllnessComponent(illness);
        }
    }

    /**
     * Method responsible for initializing each illness component.
     * @param illness The illness for which we want to initialize the component
     */
    private void initializeIllnessComponent(Event illness) {
        MaterialButton illnessButton = new MaterialButton(Objects.requireNonNull(this.getActivity()), null);
        initializeIllnessButtonParams(illnessButton);
        initializeIllnessButtonLogic((Illness) illness, illnessButton);
        illnessDisplay.addView(illnessButton);
    }

    /**
     * Method responsible for initializing the button parameters.
     * @param illnessButton The button that has to be initialized
     */
    private void initializeIllnessButtonParams(MaterialButton illnessButton) {
        illnessButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        illnessButton.setBackgroundColor(getResources().getColor(R.color.white));
        illnessButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        illnessButton.setStrokeColorResource(R.color.colorAccent);
        illnessButton.setStrokeWidth(STROKE_WIDTH);
        illnessButton.setGravity(Gravity.START);
    }

    /**
     * Method responsible for initializing the button logic.
     * @param illness The illness for which we want to initialize a button
     * @param illnessButton The button that has to be initialized
     */
    private void initializeIllnessButtonLogic(Illness illness, MaterialButton illnessButton) {
        String illnessButtonText = getString(R.string.illness) + SPACE + illness.getDescription() + EOL
                + getString(R.string.from_date) + SPACE + illness.getDateTime().toDateString();
        illnessButton.setText(illnessButtonText);
        illnessButton.setOnClickListener(v -> {
            InfoPetMedicalProfile.illness = illness;
            editingIllness = true;
            initializeEditIllnessDialog();
            deleteIllnessButton.setVisibility(View.VISIBLE);
            illnessDialog.setTitle(R.string.edit_illness_title);
            illnessDialog.show();
        });
    }

    /**
     * Method responsible for initializing the edit illness dialog.
     */
    private void initializeEditIllnessDialog() {
        editIllnessButton.setText(R.string.update_illness);
        inputIllnessDescription.setText(illness.getDescription());
        illnessEndDate.setText(illness.getEndTime().toDateString());
        illnessEndTime.setText(illness.getEndTime().toTimeString());

        ArrayList<String> severityList = new ArrayList<>();
        severityList.add(LOW);
        severityList.add(MEDIUM);
        severityList.add(HIGH);
        final ArrayAdapter<String> adp = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, severityList);
        severity.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        severity.setAdapter(adp);

        ArrayList<String> typeList = new ArrayList<>();
        typeList.add(NORMAL);
        typeList.add(ALLERGY);
        final ArrayAdapter<String> adp2 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, typeList);
        type.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        type.setAdapter(adp2);

        updatesIllnessDate = false;
        DateTime illnessDate = illness.getDateTime();
        showIllnessDate(illnessDate);
        showIllnessTime(illnessDate);
    }

    /**
     * Method responsible for initializing the string for the inputIllnessTime button.
     * @param illnessDate The date of the illness
     */
    private void showIllnessTime(DateTime illnessDate) {
        StringBuilder timeString = new StringBuilder();
        if (illnessDate.getHour() < FIRST_TWO_DIGITS) {
            timeString.append('0');
        }
        timeString.append(illnessDate.getHour()).append(TIMESEPARATOR);
        if (illnessDate.getMinutes() < FIRST_TWO_DIGITS) {
            timeString.append('0');
        }
        timeString.append(illnessDate.getMinutes()).append(TIMESEPARATOR).append(DEFAULT_SECONDS);
        illnessTime.setText(timeString);
        illnessSelectedHour = illnessDate.getHour();
        illnessSelectedMin = illnessDate.getMinutes();
    }

    /**
     * Method responsible for initializing the string for the inputIllnessDate button.
     * @param illnessDate The date of the illness
     */
    private void showIllnessDate(DateTime illnessDate) {
        StringBuilder dateString = new StringBuilder();
        dateString.append(illnessDate.getYear()).append(DATESEPARATOR);
        if (illnessDate.getMonth() < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(illnessDate.getMonth()).append(DATESEPARATOR);
        if (illnessDate.getDay() < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(illnessDate.getDay());
        this.illnessDate.setText(dateString);
    }

    /**
     * Method responsible for initializing the vaccination layout view.
     */
    private void initializeVaccinationLayoutView() {
        ArrayList<Event> vaccinationList;
        vaccinationDisplay.removeAllViews();


        vaccinationList = (ArrayList<Event>) pet.getVaccinationEvents();

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
                + getString(R.string.from_date) + SPACE + vaccination.getDateTime().toDateString();
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

    /**
     * Method responsible for initializing the add illness button.
     */
    private void initializeAddIllnessButton() {
        addIllnessButton.setOnClickListener(v -> {
            editing = false;
            deleteIllnessButton.setVisibility(View.INVISIBLE);
            inputIllnessDescription.setText("");

            ArrayList<String> severityList = new ArrayList<>();
            severityList.add(LOW);
            severityList.add(MEDIUM);
            severityList.add(HIGH);
            final ArrayAdapter<String> adp = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                    android.R.layout.simple_spinner_item, severityList);
            severity.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            severity.setAdapter(adp);

            ArrayList<String> typeList = new ArrayList<>();
            typeList.add(NORMAL);
            typeList.add(ALLERGY);
            final ArrayAdapter<String> adp2 = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                    android.R.layout.simple_spinner_item, typeList);
            type.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            type.setAdapter(adp2);

            illnessEndDate.setText(R.string.illness_end_date);
            illnessEndTime.setText(R.string.illness_end_time);
            illnessDate.setText(R.string.illness_date);
            illnessTime.setText(R.string.illness_time);
            editIllnessButton.setText(R.string.add_illnesses_button);
            illnessDialog.setTitle(R.string.add_illnesses_button);
            illnessDialog.show();
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        initializeIllnessLayoutView();
        initializeVaccinationLayoutView();
    }
}

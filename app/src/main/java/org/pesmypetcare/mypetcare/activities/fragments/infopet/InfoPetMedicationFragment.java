package org.pesmypetcare.mypetcare.activities.fragments.infopet;

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
import com.google.android.material.textfield.TextInputEditText;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetMedicationBinding;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Xavier Campos
 */
public class InfoPetMedicationFragment extends Fragment {
    private static final String EOL = "\n";
    private static final int STROKE_WIDTH = 5;
    private static final String SPACE = " ";
    private static final String DATESEPARATOR = "-";
    private static final int FIRST_TWO_DIGITS = 10;
    private static boolean editing;
    private static Medication medication;

    private FragmentInfoPetMedicationBinding binding;
    private Pet pet;
    private LinearLayout medicationDisplay;
    private Button addMedicationButton;
    private MaterialButton medicationDate;
    private MaterialDatePicker materialDatePicker;
    private boolean isMedicationDateSelected;
    private boolean updatesDate;
    private boolean updatesName;
    private MaterialButton editMedicationButton;
    private TextInputEditText inputMedicationName;
    private TextInputEditText inputMedicationQuantity;
    private TextInputEditText inputMedicationDuration;
    private TextInputEditText inputMedicationPeriodicity;
    private MaterialButton deleteMedicationButton;
    private AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetMedicationBinding.inflate(inflater, container, false);
        pet = InfoPetFragment.getPet();

        medicationDisplay = binding.medicationDisplayLayout;
        addMedicationButton = binding.addMedicationButton;
        View editMedicationLayout = prepareDialog();
        dialog = getBasicMealDialog();
        dialog.setView(editMedicationLayout);

        initializeEditMedicationButton();
        initializeRemoveMedicationButton();
        initializeAddMedicationButton();

        return binding.getRoot();
    }

    /**
     * Method responsible for initializing the add medication button.
     */
    private void initializeAddMedicationButton() {
        addMedicationButton.setOnClickListener(v -> {
            editing = false;
            deleteMedicationButton.setVisibility(View.INVISIBLE);
            inputMedicationName.setText("");
            inputMedicationQuantity.setText("");
            inputMedicationDuration.setText("");
            inputMedicationPeriodicity.setText("");
            medicationDate.setText(R.string.medication_inidate);
            editMedicationButton.setText(R.string.add_medication_button);
            dialog.setTitle(R.string.add_medication_button);
            dialog.show();
        });
    }

    /**
     * Create the basic medication dialog.
     * @return The basic main dialog
     */
    private AlertDialog getBasicMealDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),
            R.style.AlertDialogTheme);
        dialog.setTitle(R.string.edit_medication_title);
        dialog.setMessage(R.string.edit_medication_message);
        return dialog.create();
    }

    /**
     * Prepare the medication dialog.
     * @return The layout of the main dialog
     */
    private View prepareDialog() {
        View editMedicationLayout = getLayoutInflater().inflate(R.layout.edit_medication, null);
        inputMedicationName = editMedicationLayout.findViewById(R.id.inputMedicationName);
        inputMedicationQuantity = editMedicationLayout.findViewById(R.id.inputMedicationQuantity);
        inputMedicationDuration = editMedicationLayout.findViewById(R.id.inputMedicationDuration);
        inputMedicationPeriodicity = editMedicationLayout.findViewById(R.id.inputMedicationPeriodicity);
        editMedicationButton = editMedicationLayout.findViewById(R.id.editMedicationButton);
        deleteMedicationButton = editMedicationLayout.findViewById(R.id.deleteMedicationButton);
        medicationDate = editMedicationLayout.findViewById(R.id.inputMedicationIniDate);

        setCalendarPicker();
        return editMedicationLayout;
    }

    /**
     * Method responsible for initializing the remove medication button.
     */
    private void initializeRemoveMedicationButton() {
        deleteMedicationButton.setOnClickListener(v -> {
            InfoPetFragment.getCommunication().deletePetMedication(pet, medication);
            initializeMedicationsLayoutView();
            dialog.dismiss();
        });
    }

    /**
     * Method responsible for initializing the string for the inputMedicationDate button.
     * @param medicationIniDate The date of the medication
     */
    private void showMedicationIniDate(DateTime medicationIniDate) {
        StringBuilder dateString = new StringBuilder();
        dateString.append(medicationIniDate.getYear()).append(DATESEPARATOR);
        if (medicationIniDate.getMonth() < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(medicationIniDate.getMonth()).append(DATESEPARATOR);
        if (medicationIniDate.getDay() < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(medicationIniDate.getDay());
        this.medicationDate.setText(dateString);
    }

    /**
     * Initialize the edit medication button.
     */
    private void initializeEditMedicationButton() {
        editMedicationButton.setOnClickListener(v -> {
            if (isAnyFieldBlank()) {
                showErrorMessage();
            } else {
                if (editing) {
                    initializeEditButtonListener();
                } else {
                    initializeAddButtonListener();
                }

                dialog.dismiss();
                initializeMedicationsLayoutView();
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
        DateTime medicationIniDate = getDateTime();
        String medicationName = Objects.requireNonNull(inputMedicationName.getText()).toString();
        double medicationQuantity = Double.parseDouble(Objects.requireNonNull(
            inputMedicationQuantity.getText()).toString());
        int medicationPeriodicity = Integer.parseInt(Objects.requireNonNull(
            inputMedicationPeriodicity.getText()).toString());
        int medicationDuration = Integer.parseInt(Objects.requireNonNull(
            inputMedicationDuration.getText()).toString());
        medication = new Medication(medicationName, medicationQuantity, medicationPeriodicity,
            medicationDuration, medicationIniDate);
        communicateAddition();
    }

    /**
     * Method responsible for communicating the pet addition to the server.
     */
    private void communicateAddition() {
        try {
            InfoPetFragment.getCommunication().addPetMedication(pet, medication);
        } catch (MedicationAlreadyExistingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method responsible for initializing the editButton listener.
     */
    private void initializeEditButtonListener() {
        final String newDate = getDateTime().toString();
        String medicationName = Objects.requireNonNull(inputMedicationName.getText()).toString();
        double medicationQuantity = Double.parseDouble(Objects.requireNonNull(
            inputMedicationQuantity.getText()).toString());
        int medicationPeriodicity = Integer.parseInt(Objects.requireNonNull(
            inputMedicationPeriodicity.getText()).toString());
        int medicationDuration = Integer.parseInt(Objects.requireNonNull(
            inputMedicationDuration.getText()).toString());
        updateMedicationBody(medicationQuantity, medicationPeriodicity, medicationDuration);
        if (!medicationName.equals(medication.getMedicationName())) {
            updatesName = true;
        }
        InfoPetFragment.getCommunication().updatePetMedication(pet, medication, newDate, updatesDate, medicationName,
            updatesName);
        updateMedicationKey(newDate, medicationName);
    }

    /**
     * Updates the medication key.
     * @param newDate The new date of the medication
     * @param medicationName The new name of the medication
     */
    private void updateMedicationKey(String newDate, String medicationName) {
        medication.setMedicationName(medicationName);
        medication.setMedicationDate(DateTime.Builder.buildFullString(newDate));
    }

    /**
     * Updates the body of the medication.
     * @param medicationQuantity The new medication quantity
     * @param medicationPeriodicity The new medication periodicity
     * @param medicationDuration The new medication duration
     */
    private void updateMedicationBody(double medicationQuantity, int medicationPeriodicity, int medicationDuration) {
        medication.setMedicationQuantity(medicationQuantity);
        medication.setMedicationFrequency(medicationPeriodicity);
        medication.setMedicationDuration(medicationDuration);
    }

    /**
     * Method responsible for obtaining the date of the medication in the current format.
     * @return The dateTime of the medication
     */
    private DateTime getDateTime() {
        String dateString = medicationDate.getText().toString();
        return DateTime.Builder.buildDateString(dateString);
    }

    /**
     * Method responsible for checking if there is any empty field.
     * @return True if there is any empty field or false otherwise
     */
    private boolean isAnyFieldBlank() {
        boolean medicationNameEmpty = "".equals(Objects.requireNonNull(inputMedicationName.getText()).toString());
        boolean medicationQuantityEmpty = "".equals(Objects.requireNonNull(
            inputMedicationQuantity.getText()).toString());
        boolean medicationPeriodicityEmpty = "".equals(Objects.requireNonNull(
            inputMedicationPeriodicity.getText()).toString());
        boolean medicationDurationEmpty = "".equals(Objects.requireNonNull(
            inputMedicationDuration.getText()).toString());
        boolean quantityNameEmpty = medicationNameEmpty || medicationQuantityEmpty;
        boolean periodicityDurationEmpty = medicationPeriodicityEmpty || medicationDurationEmpty;
        if (editing) {
            return quantityNameEmpty || periodicityDurationEmpty;
        }
        return quantityNameEmpty || periodicityDurationEmpty || !isMedicationDateSelected;
    }

    /**
     * Sets the calendar picker.
     */
    private void setCalendarPicker() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.select_medication_date));
        materialDatePicker = builder.build();

        medicationDate.setOnClickListener(v ->
            materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), "DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(this::initializeOnPositiveButtonClickListener);
    }

    /**
     * Method responsible for initializing the onPositiveButtonClickListener.
     * @param selection The selected value
     */
    private void initializeOnPositiveButtonClickListener(Object selection) {
        medicationDate.setText(materialDatePicker.getHeaderText());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(selection.toString()));
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        medicationDate.setText(formattedDate);
        isMedicationDateSelected = true;
        updatesDate = true;
    }

    /**
     * Method responsible for initializing the medication layout view.
     */
    private void initializeMedicationsLayoutView() {
        ArrayList<Event> medicationsList = (ArrayList<Event>) pet.getMedicationEvents();
        medicationDisplay.removeAllViews();
        for (Event medication : medicationsList) {
            initializeMealComponent(medication);
        }
    }

    /**
     * Method responsible for initializing each medication component.
     * @param medication The medication for which we want to initialize the component
     */
    private void initializeMealComponent(Event medication) {
        MaterialButton medicationButton = new MaterialButton(Objects.requireNonNull(this.getActivity()), null);
        initializeButtonParams(medicationButton);
        initializeButtonLogic((Medication) medication, medicationButton);
        medicationDisplay.addView(medicationButton);
    }

    /**
     * Method responsible for initializing the button logic.
     * @param medication The medication for which we want to initialize a button
     * @param medicationButton The button that has to be initialized
     */
    private void initializeButtonLogic(Medication medication, MaterialButton medicationButton) {
        String medicationButtonText = getString(R.string.medication) + SPACE + medication.getMedicationName() + EOL
            + getString(R.string.quantity) + SPACE + medication.getMedicationQuantity() + EOL
            + getString(R.string.periodicity) + SPACE + medication.getMedicationFrequency();
        medicationButton.setText(medicationButtonText);
        medicationButton.setOnClickListener(v -> {
            InfoPetMedicationFragment.medication = medication;
            editing = true;
            initializeEditDialog();
            deleteMedicationButton.setVisibility(View.VISIBLE);
            dialog.setTitle(R.string.edit_medication_title);
            dialog.show();
        });
    }

    /**
     * Method responsible for initializing the edit medication dialog.
     */
    private void initializeEditDialog() {
        editMedicationButton.setText(R.string.update_medication);
        inputMedicationName.setText(medication.getMedicationName());
        inputMedicationQuantity.setText(String.valueOf(medication.getMedicationQuantity()));
        inputMedicationPeriodicity.setText(String.valueOf(medication.getMedicationFrequency()));
        inputMedicationDuration.setText(String.valueOf(medication.getMedicationDuration()));
        updatesDate = false;
        updatesName = false;
        DateTime medicationDate = medication.getMedicationDate();
        showMedicationIniDate(medicationDate);
        showMedicationIniDate(medicationDate);
    }

    /**
     * Method responsible for initializing the button parameters.
     * @param medicationButton The button that has to be initialized
     */
    private void initializeButtonParams(MaterialButton medicationButton) {
        medicationButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        medicationButton.setBackgroundColor(getResources().getColor(R.color.white));
        medicationButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        medicationButton.setStrokeColorResource(R.color.colorAccent);
        medicationButton.setStrokeWidth(STROKE_WIDTH);
        medicationButton.setGravity(Gravity.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeMedicationsLayoutView();
    }
}

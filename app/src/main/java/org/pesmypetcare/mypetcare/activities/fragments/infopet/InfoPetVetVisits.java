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

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetVetVisitsBinding;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.pets.events.vetvisit.VetVisit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Xavier Campos
 */
public class InfoPetVetVisits extends Fragment {
    private static final String EOL = "\n";
    private static final int STROKE_WIDTH = 5;
    private static final String SPACE = " ";
    private static final String DATESEPARATOR = "-";
    private static final String TIMESEPARATOR = ":";
    private static final int FIRST_TWO_DIGITS = 10;
    private static final String DEFAULT_SECONDS = "00";
    private static boolean editing;
    private static VetVisit vetVisit;

    private FragmentInfoPetVetVisitsBinding binding;
    private Pet pet;
    private SwitchMaterial visitSelectorSwitch;
    private boolean isPendentVisits;
    private LinearLayout visitDisplay;
    private Button addVisitButton;
    private MaterialButton visitDate;
    private MaterialDatePicker materialDatePicker;
    private boolean isVisitDateSelected;
    private boolean updatesDate;
    private int selectedHour;
    private int selectedMin;
    private MaterialButton visitTime;
    private boolean isVisitTimeSelected;
    private MaterialButton editVisitButton;
    private TextInputEditText inputVisitAddress;
    private TextInputEditText inputVisitReason;
    private MaterialButton deleteVisitButton;
    private AlertDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetVetVisitsBinding.inflate(inflater, container, false);
        pet = InfoPetFragment.getPet();

        visitDisplay = binding.vetVisitsDisplayLayout;
        addVisitButton = binding.addVisitButton;
        View editVisitLayout = prepareDialog();
        dialog = getBasicVisitDialog();
        dialog.setView(editVisitLayout);

        initializeEditVisitButton();
        initializeRemoveVisitButton();
        initializeVisitSelectorSwitch();
        initializeAddVisitButton();

        return binding.getRoot();
    }

    /**
     * Method responsible for initializing the add visit button.
     */
    private void initializeAddVisitButton() {
        addVisitButton.setOnClickListener(v -> {
            editing = false;
            deleteVisitButton.setVisibility(View.INVISIBLE);
            inputVisitAddress.setText("");
            inputVisitReason.setText("");
            visitDate.setText(R.string.vet_visit_date);
            visitTime.setText(R.string.vet_visit_time);
            editVisitButton.setText(R.string.add_vet_visit);
            dialog.setTitle(R.string.add_vet_visit);
            dialog.show();
        });
    }

    /**
     * Create the basic visit dialog.
     * @return The basic main dialog
     */
    private AlertDialog getBasicVisitDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),
            R.style.AlertDialogTheme);
        dialog.setTitle(R.string.edit_vet_visit_title);
        dialog.setMessage(R.string.edit_vet_visit_message);
        return dialog.create();
    }

    /**
     * Prepare the vet visit dialog.
     * @return The layout of the main dialog
     */
    private View prepareDialog() {
        View editVisitLayout = getLayoutInflater().inflate(R.layout.edit_vet_visit, null);
        inputVisitReason = editVisitLayout.findViewById(R.id.inputVisitReason);
        inputVisitAddress = editVisitLayout.findViewById(R.id.inputVisitAdress);
        editVisitButton = editVisitLayout.findViewById(R.id.editVisitButton);
        deleteVisitButton = editVisitLayout.findViewById(R.id.deleteVisitButton);
        visitDate = editVisitLayout.findViewById(R.id.inputVisitDate);
        visitTime = editVisitLayout.findViewById(R.id.inputVisitTime);

        setCalendarPicker();
        setTimePicker();
        return editVisitLayout;
    }

    /**
     * Method responsible for initializing the remove visit button.
     */
    private void initializeRemoveVisitButton() {
        deleteVisitButton.setOnClickListener(v -> {
            InfoPetFragment.getCommunication().deletePetVetVisit(pet, vetVisit);
            initializeVisitsLayoutView();
            dialog.dismiss();
        });
    }

    /**
     * Method responsible for initializing the string for the inputVisitDate button.
     * @param visitDate The date of the visit
     */
    private void showVisitDate(DateTime visitDate) {
        StringBuilder dateString = new StringBuilder();
        dateString.append(visitDate.getYear()).append(DATESEPARATOR);
        if (visitDate.getMonth() < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(visitDate.getMonth()).append(DATESEPARATOR);
        if (visitDate.getDay() < FIRST_TWO_DIGITS) {
            dateString.append('0');
        }
        dateString.append(visitDate.getDay());
        this.visitDate.setText(dateString);
    }

    /**
     * Method responsible for initializing the string for the inputVisitTime button.
     * @param visitTime The date of the visit
     */
    private void showVisitTime(DateTime visitTime) {
        StringBuilder timeString = new StringBuilder();
        if (visitTime.getHour() < FIRST_TWO_DIGITS) {
            timeString.append('0');
        }
        timeString.append(visitTime.getHour()).append(TIMESEPARATOR);
        if (visitTime.getMinutes() < FIRST_TWO_DIGITS) {
            timeString.append('0');
        }
        timeString.append(visitTime.getMinutes()).append(TIMESEPARATOR).append(DEFAULT_SECONDS);
        this.visitTime.setText(timeString);
        selectedHour = visitTime.getHour();
        selectedMin = visitTime.getMinutes();
    }

    /**
     * Initialize the edit visit button.
     */
    private void initializeEditVisitButton() {
        editVisitButton.setOnClickListener(v -> {
            if (isAnyFieldBlank()) {
                showErrorMessage();
            } else {
                if (editing) {
                    initializeEditButtonListener();
                } else {
                    initializeAddButtonListener();
                }

                dialog.dismiss();
                initializeVisitsLayoutView();
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
        DateTime visitDate = getDateTime();
        String address = Objects.requireNonNull(inputVisitAddress.getText()).toString();
        String reason = Objects.requireNonNull(inputVisitReason.getText()).toString();
        vetVisit = new VetVisit(visitDate, address, reason);
        InfoPetFragment.getCommunication().addPetVetVisit(pet, vetVisit);
    }

    /**
     * Method responsible for initializing the editButton listener.
     */
    private void initializeEditButtonListener() {
        String newDate = getDateTime().toString();
        String address = Objects.requireNonNull(inputVisitAddress.getText()).toString();
        String reason = Objects.requireNonNull(inputVisitReason.getText()).toString();
        vetVisit.setAddress(address);
        vetVisit.setReason(reason);
        InfoPetFragment.getCommunication().updatePetVetVisit(pet, vetVisit, newDate, updatesDate);
        if (updatesDate) {
            vetVisit.setVisitDate(DateTime.Builder.buildFullString(newDate));
        }
    }

    /**
     * Method responsible for obtaining the date of the visit in the current format.
     * @return The dateTime of the visit
     */
    private DateTime getDateTime() {
        StringBuilder dateString = new StringBuilder(visitDate.getText().toString());
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
        boolean visitAddressEmpty = "".equals(Objects.requireNonNull(inputVisitAddress.getText()).toString());
        boolean visitReasonEmpty = "".equals(Objects.requireNonNull(inputVisitAddress.getText()).toString());
        if (editing) {
            return visitReasonEmpty || visitAddressEmpty;
        }
        return visitAddressEmpty || visitReasonEmpty || !isVisitDateSelected || !isVisitTimeSelected;
    }

    /**
     * Sets the time picker.
     */
    private void setTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        visitTime.setOnClickListener(v -> {
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
        visitTime.setText(time);
        isVisitTimeSelected = true;
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
        builder.setTitleText(getString(R.string.vet_visit_date));
        materialDatePicker = builder.build();

        visitDate.setOnClickListener(v ->
            materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), "DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(this::initializeOnPositiveButtonClickListener);
    }

    /**
     * Method responsible for initializing the onPositiveButtonClickListener.
     * @param selection The selected value
     */
    private void initializeOnPositiveButtonClickListener(Object selection) {
        visitDate.setText(materialDatePicker.getHeaderText());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(selection.toString()));
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        visitDate.setText(formattedDate);
        isVisitDateSelected = true;
        updatesDate = true;
    }

    /**
     * Method responsible for initializing the visit selector switch.
     */
    private void initializeVisitSelectorSwitch() {
        visitSelectorSwitch = binding.petVisitSelector;
        visitSelectorSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isPendentVisits = visitSelectorSwitch.isChecked();
            initializeVisitsLayoutView();
        });
    }

    /**
     * Method responsible for initializing the visits layout view.
     */
    private void initializeVisitsLayoutView() {
        ArrayList<Event> visitList;
        visitDisplay.removeAllViews();

        if (isPendentVisits) {
            visitList = (ArrayList<Event>) getPendentVisits();
        } else {
            visitList = (ArrayList<Event>) pet.getVetVisitEvents();
        }

        for (Event visit : visitList) {
            initializeVisitComponent(visit);
        }
    }

    /**
     * Method responsible for initializing each visit component.
     * @param visit The visit for which we want to initialize the component
     */
    private void initializeVisitComponent(Event visit) {
        MaterialButton visitButton = new MaterialButton(Objects.requireNonNull(this.getActivity()), null);
        initializeButtonParams(visitButton);
        initializeButtonLogic((VetVisit) visit, visitButton);
        visitDisplay.addView(visitButton);
    }

    /**
     * Method responsible for initializing the button logic.
     * @param visit The visit for which we want to initialize a button
     * @param visitButton The button that has to be initialized
     */
    private void initializeButtonLogic(VetVisit visit, MaterialButton visitButton) {
        String visitButtonText = getString(R.string.visit_of_the_day) + SPACE + visit.getVisitDate() + EOL
            + getString(R.string.visit_reason) + SPACE + visit.getReason() + EOL + getString(R.string.visit_addres)
            + SPACE + visit.getAddress();
        visitButton.setText(visitButtonText);
        visitButton.setOnClickListener(v -> {
            InfoPetVetVisits.vetVisit = visit;
            editing = true;
            initializeEditDialog();
            deleteVisitButton.setVisibility(View.VISIBLE);
            dialog.setTitle(R.string.edit_vet_visit_title);
            dialog.show();
        });
    }

    /**
     * Method responsible for initializing the edit visit dialog.
     */
    private void initializeEditDialog() {
        editVisitButton.setText(R.string.update_vet_visit);
        inputVisitAddress.setText(vetVisit.getAddress());
        inputVisitReason.setText(vetVisit.getReason());
        updatesDate = false;
        DateTime visitDate = vetVisit.getVisitDate();
        showVisitDate(visitDate);
        showVisitTime(visitDate);
    }

    /**
     * Method responsible for initializing the button parameters.
     * @param visitButton The button that has to be initialized
     */
    private void initializeButtonParams(MaterialButton visitButton) {
        visitButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        visitButton.setBackgroundColor(getResources().getColor(R.color.white, null));
        visitButton.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        visitButton.setStrokeColorResource(R.color.colorAccent);
        visitButton.setStrokeWidth(STROKE_WIDTH);
        visitButton.setGravity(Gravity.START);
    }

    /**
     * Method responsible for obtaining all the pendent visits.
     * @return All the pendent visits
     */
    private List<Event> getPendentVisits() {
        ArrayList<Event> result = new ArrayList<>();
        result.clear();
        List<Event> aux = pet.getVetVisitEvents();
        for (Event e:aux) {
            DateTime currentDate = DateTime.getCurrentDate();
            if (e.getDateTime().compareTo(currentDate) > 0) {
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeVisitsLayoutView();
    }
}

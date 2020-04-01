package org.pesmypetcare.mypetcare.activities.fragments.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.CalendarEventsView;
import org.pesmypetcare.mypetcare.databinding.FragmentCalendarBinding;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.DateConversion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CalendarFragment extends Fragment {
    private static final int PADDING_20 = 20;
    private static final float TEXT_SIZE_14 = 14f;
    private static final float TEXT_SIZE_12 = 12f;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private FragmentCalendarBinding binding;
    private CalendarView calendar;
    private User user;
    private Pet selectedPet;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        CalendarCommunication communication = (CalendarCommunication) getActivity();
        user = Objects.requireNonNull(communication).getUser();

        setUpCalendar();
        binding.btnAddPersonalEvent.setOnClickListener(v -> initializeDialog());
        return binding.getRoot();
    }

    /**
     * Initialize the dialog for a new personal event.
     */
    private void initializeDialog() {
        MaterialAlertDialogBuilder newPersonal = new MaterialAlertDialogBuilder(Objects.requireNonNull(getContext()),
                R.style.AlertDialogTheme);
        initializeDialogComponents(newPersonal);
        newPersonal.show();
    }

    /**
     * Initialize the dialog components for a new personal event.
     */
    private void initializeDialogComponents(MaterialAlertDialogBuilder newPersonal) {
        newPersonal.setTitle("New personal notice");
        newPersonal.setMessage("Enter the event description, its time and select the pet that will participate.");
        LinearLayout layout = new LinearLayout(getContext());
        EditText reasonText = initializeDialogLayout(layout);
        LinearLayout time = new LinearLayout(getContext());
        TextView dateText = initializeTimeLayout(time);
        EditText timeText = putHourTimeLayout(time);
        layout.addView(time);
        Spinner sp = initializeSpinner(layout);
        newPersonal.setView(layout);
        createPersonalEventListener(newPersonal, reasonText, dateText, timeText, sp);
        cancelDialog(newPersonal);
    }

    /**
     * Listener of the create button of the dialog.
     * @param newPersonal The dialog
     * @param dateText The date of the event
     * @param reasonText The reason of the event
     * @param sp The spinner for pet selection
     * @param timeText The hour of the event
     */
    private void createPersonalEventListener(MaterialAlertDialogBuilder newPersonal, EditText reasonText, TextView
            dateText, EditText timeText, Spinner sp) {
        newPersonal.setPositiveButton("Create", (dialog, which) -> {
            if (sp.getSelectedItem() != null) {
                createPersonalEvent(reasonText, dateText, timeText, sp);
            } else {
                toastText("Add a pet");
            }
        });
    }

    /**
     * Create a new toast.
     * @param text The toast text
     */
    private void toastText(String text) {
        Toast toast1 = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
    }

    /**
     * Create a new personal event.
     * @param dateText The date of the event
     * @param reasonText The reason of the event
     * @param sp The spinner for pet selection
     * @param timeText The hour of the event
     */
    private void createPersonalEvent(EditText reasonText, TextView dateText, EditText timeText, Spinner sp) {
        String petName = sp.getSelectedItem().toString();
        StringBuilder dateTime = new StringBuilder(dateText.getText());
        dateTime.append('T');
        dateTime.append(timeText.getText());
        getPet(petName);
        if (isValidTime(timeText.getText().toString()) && reasonText.getText() != null) {
            selectedPet.addEvent(new Event(reasonText.getText().toString(), dateTime.toString()));
        } else {
            toastText("Incorrect entry");
        }
    }

    /**
     * Cancel button of the dialog.
     * @param newPersonal The dialog
     */
    private void cancelDialog(MaterialAlertDialogBuilder newPersonal) {
        newPersonal.setNegativeButton("Cancel", (dialog, which) ->
                newPersonal.setCancelable(true));
    }

    /**
     * Initialize the layout of the dialog.
     * @param layout The layout of the dialog
     * @return The reasonText
     */
    private EditText initializeDialogLayout(LinearLayout layout) {
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(PADDING_20, 0, PADDING_20, 0);
        EditText reasonText = new EditText(new ContextThemeWrapper(getContext(), R.style.HintStyle));
        reasonText.setHint("Description");
        reasonText.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_14);
        layout.addView(reasonText);
        return reasonText;
    }

    /**
     * Initialize the hour in the time section of the dialog.
     * @param time The layout to put the hour
     * @return The timeText
     */
    private EditText putHourTimeLayout(LinearLayout time) {
        EditText timeText = new EditText(new ContextThemeWrapper(getContext(), R.style.HintStyle));
        timeText.setTextColor(Color.parseColor("#0070C0"));
        timeText.setHint("00:00:00");
        timeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_14);
        time.addView(timeText);
        return timeText;
    }

    /**
     * Initialize the time section of the dialog.
     * @param time The layout to put the time and date
     * @return The dateText
     */
    private TextView initializeTimeLayout(LinearLayout time) {
        time.setOrientation(LinearLayout.HORIZONTAL);
        time.setGravity(Gravity.CENTER_HORIZONTAL);
        time.setWeightSum(2);
        TextView dateText = new TextView(new ContextThemeWrapper(getContext(), R.style.HintStyle));
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        dateText.setText(dateFormat.format(currentTime));
        dateText.setTextColor(Color.parseColor("#0070C0"));
        dateText.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_12);
        time.addView(dateText);
        return dateText;
    }

    /**
     * Initialize the spinner of the dialog.
     * @param layout The layout to put the spinner
     * @return The spinner
     */
    private Spinner initializeSpinner(LinearLayout layout) {
        final ArrayAdapter<String> adp = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, getPetsName());
        Spinner sp = new Spinner(getContext());
        sp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);
        layout.addView(sp);
        return sp;
    }

    /**
     * Indicates is a string is in a correct time format.
     * @param time The time string
     * @return True if the string is in a correct time format, false otherwise
     */
    private boolean isValidTime(String time) {
        boolean result = false;
        if (time.length() == 8 && time.indexOf(':') == 2 && time.lastIndexOf(':') == 5
                && Character.isDigit(time.charAt(0)) && Character.isDigit(time.charAt(1))
                && Character.isDigit(time.charAt(3)) && Character.isDigit(time.charAt(4))
                && Character.isDigit(time.charAt(6)) && Character.isDigit(time.charAt(7))) {
            result = true;
        }
        return result;
    }

    /**
     * Select a pet for a new event.
     * @param petName The name of the pet
     */
    private void getPet(String petName) {
        ArrayList<Pet> pets = user.getPets();

        for (Pet pet : pets) {
            if (pet.getName().equals(petName)) {
                selectedPet = pet;
            }
        }
    }

    /**
     * Gets all the pets of the user.
     * @return An array with the pets name
     */
    private ArrayList<String> getPetsName() {
        ArrayList<String> petsName = new ArrayList<>();
        ArrayList<Pet> pets = user.getPets();

        for (Pet pet : pets) {
            petsName.add(pet.getName());
        }
        return petsName;
    }

    /**
     * Set up the calendar.
     */
    private void setUpCalendar() {
        calendar = binding.calendar;
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        String date = dateFormat.format(currentTime);

        addComponents(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)),
            Integer.parseInt(date.substring(9, 10)));

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> addComponents(year, month, dayOfMonth));
    }

    private void addComponents(int year, int month, int dayOfMonth) {
        ArrayList<Pet> pets = user.getPets();
        String date = DateConversion.getDate(year, month, dayOfMonth);
        binding.eventInfoLayout.removeAllViews();

        for (Pet pet : pets) {
            CalendarEventsView calendarEventsView = new CalendarEventsView(getContext(), null);
            calendarEventsView.showEvents(pet, date);
            binding.eventInfoLayout.addView(calendarEventsView);
        }
    }
}

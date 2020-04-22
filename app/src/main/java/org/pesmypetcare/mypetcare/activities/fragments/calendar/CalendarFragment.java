package org.pesmypetcare.mypetcare.activities.fragments.calendar;

import android.annotation.SuppressLint;
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
import org.pesmypetcare.mypetcare.activities.views.EventView;
import org.pesmypetcare.mypetcare.activities.views.PetComponentView;
import org.pesmypetcare.mypetcare.databinding.FragmentCalendarBinding;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.DateConversion;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CalendarFragment extends Fragment {
    private static final int PADDING_20 = 20;
    private static final float TEXT_SIZE_14 = 14f;
    private static final float TEXT_SIZE_12 = 12f;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String PRIMARY_COLOR = "#0070C0";
    private static final int TIME_LENGTH = 8;
    private static final int POS5 = 5;
    private static final int POS2 = 2;
    private static final int POS3 = 3;
    private static final int POS4 = 4;
    private static final int POS6 = 6;
    private static final int POS7 = 7;
    private FragmentCalendarBinding binding;
    private CalendarView calendar;
    private User user;
    private Pet selectedPet;
    private CalendarCommunication communication;
    private String selectedDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        communication = (CalendarCommunication) getActivity();
        user = Objects.requireNonNull(communication).getUser();
        try {
            setUpCalendar();
        } catch (ParseException | InvalidFormatException e) {
            e.printStackTrace();
        }

        binding.btnAddPersonalEvent.setOnClickListener(v -> {
            try {
                initializeDialog();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        binding.btnAddPeriodicEvent.setOnClickListener(v -> {
            try {
                initializePeriodicDialog();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        return binding.getRoot();
    }


    /**
     * Initialize the dialog for a new personal event.
     */
    private void initializeDialog() throws ParseException {
        MaterialAlertDialogBuilder newPersonal = new MaterialAlertDialogBuilder(Objects.requireNonNull(getContext()),
                R.style.AlertDialogTheme);
        initializeDialogComponents(newPersonal);
        newPersonal.show();
    }

    private void initializePeriodicDialog() throws ParseException {
        MaterialAlertDialogBuilder newPeriodic = new MaterialAlertDialogBuilder(Objects.requireNonNull(getContext()),
                R.style.AlertDialogTheme);
        initializeDialogComponentsPeriodic(newPeriodic);
        newPeriodic.show();
    }

    private void initializeDialogComponentsPeriodic(MaterialAlertDialogBuilder newPeriodic) throws ParseException {
        LinearLayout layout = new LinearLayout(getContext());
        EditText reasonText = initializeDialogLayout(layout);
        reasonText.setContentDescription(getString(R.string.reasonText));
        LinearLayout time = new LinearLayout(getContext());
        TextView dateText = initializeTimeLayout(time);
        EditText timeText = putHourTimeLayout(time);
        timeText.setContentDescription(getString(R.string.timeText));
        layout.addView(time);
        Spinner sp_pets = initializeSpinner(layout);
        sp_pets.setContentDescription(getString(R.string.selectPet));
        Spinner sp_period = initializeSpinnerPeriod(layout);
        sp_period.setContentDescription(getString(R.string.selectPeriodicity));
        dialogElementsPeriodic(newPeriodic, layout, reasonText, dateText, timeText, sp_pets, sp_period);
    }

    private void dialogElementsPeriodic(MaterialAlertDialogBuilder newPeriodic, LinearLayout layout, EditText
            reasonText, TextView dateText, EditText timeText, Spinner sp_pets, Spinner sp_period) {
        newPeriodic.setTitle(R.string.periodic_notification_title);
        newPeriodic.setMessage(R.string.dialog_new_periodic_message);
        newPeriodic.setView(layout);
        cancelDialog(newPeriodic);
        createPeriodicEventListener(newPeriodic, reasonText, dateText, timeText, sp_pets, sp_period);
    }

    private void createPeriodicEventListener(MaterialAlertDialogBuilder newPeriodic, EditText reasonText, TextView
            dateText, EditText timeText, Spinner sp_pets, Spinner sp_period) {
        newPeriodic.setPositiveButton(getString(R.string.create), (dialog, which) -> {
            if (sp_pets.getSelectedItem() != null) {
                if (reasonText.getText().toString().length() != 0) {
                    try {
                        createPeriodicNotification(reasonText, dateText, timeText, sp_pets, sp_period);
                    } catch (ParseException | InvalidFormatException e) {
                        e.printStackTrace();
                    }
                } else {
                    toastText(getString(R.string.no_description));
                }
            } else {
                toastText(getString(R.string.add_a_pet));
            }
        });
    }

    private void createPeriodicNotification(EditText reasonText, TextView dateText, EditText timeText,
                                            Spinner sp_pets, Spinner sp_period) throws ParseException, InvalidFormatException {
        String petName = sp_pets.getSelectedItem().toString();
        String periodicity = sp_period.getSelectedItem().toString();
        System.out.println(periodicity);
        int period = setPeriodicity(periodicity);
        int periodDay;
        DateTime dateTime = DateTime.Builder.buildDateTimeString(dateText.getText().toString(),
                timeText.getText().toString());;
        getPet(petName);
        if (isValidTime(timeText.getText().toString()) && reasonText.getText() != null) {
            System.out.println(period);
            periodDay = getPeriodicityDay(dateTime.toString(), period);
            System.out.println(periodDay);
            selectedPet.addPeriodicNotification(new Event(reasonText.getText().toString(), dateTime), period, periodDay);
            communication.newPeriodicNotification(selectedPet, period, reasonText.getText().toString(), dateTime.toString());
            Calendar c = Calendar.getInstance();
            calendarAlarmInitialization(dateTime, c);
            communication.schedulePeriodicNotification(getContext(), c.getTimeInMillis() , selectedPet.getName(), reasonText.getText().toString(), period);
            setUpCalendar();
        } else {
            toastText(getString(R.string.incorrect_entry));
        }
    }

    private int getPeriodicityDay(String dateTime, int period) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = formatter.parse(dateTime.replaceAll("Z$", "+0000"));
        DateFormat dfWeek = new SimpleDateFormat("u", Locale.ENGLISH);
        DateFormat dfMonth = new SimpleDateFormat("dd", Locale.ENGLISH);
        switch (period) {
            case 1:
                return 0;
            case 7:
            case 14:
                return Integer.parseInt(dfWeek.format(date));
            case -1:
            case -3:
                return Integer.parseInt(dfMonth.format(date));
            default:
                return -1;
        }
    }

    private int setPeriodicity(String periodicity) {
        if(getString(R.string.one_day) == periodicity) return 1;
        else if(getString(R.string.one_week) == periodicity) return 7;
        else if(getString(R.string.two_weeks) == periodicity) return 14;
        else if(getString(R.string.one_month) == periodicity) return -1;
        else if(getString(R.string.three_months) == periodicity) return -3;
        else return 0;
    }

    private Spinner initializeSpinnerPeriod(LinearLayout layout) {
        final ArrayAdapter<String> adp = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, (List<String>) getPeriodicityOptions());
        Spinner sp = new Spinner(getContext());
        sp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);
        layout.addView(sp);
        return sp;
    }

    private ArrayList<?> getPeriodicityOptions() {
        ArrayList<String> PeriodOpt = new ArrayList<>();

        PeriodOpt.add(getString(R.string.two_weeks));
        PeriodOpt.add(getString(R.string.one_week));
        PeriodOpt.add(getString(R.string.one_day));
        PeriodOpt.add(getString(R.string.one_month));
        PeriodOpt.add(getString(R.string.three_months));
        return PeriodOpt;
    }

    /**
     * Initialize the dialog components for a new personal event.
     */
    private void initializeDialogComponents(MaterialAlertDialogBuilder newPersonal) throws ParseException {
        LinearLayout layout = new LinearLayout(getContext());
        EditText reasonText = initializeDialogLayout(layout);
        reasonText.setContentDescription(getString(R.string.reasonText));
        LinearLayout time = new LinearLayout(getContext());
        TextView dateText = initializeTimeLayout(time);
        EditText timeText = putHourTimeLayout(time);
        timeText.setContentDescription(getString(R.string.timeText));
        layout.addView(time);
        Spinner sp = initializeSpinner(layout);
        sp.setContentDescription(getString(R.string.spinnerText));
        dialogElements(newPersonal, layout, reasonText, dateText, timeText, sp);
    }

    /**
     * Initialize the dialog elements.
     * @param timeText The time
     * @param sp The spinner
     * @param reasonText The event description
     * @param dateText The event date
     * @param layout The layout of the dialog
     * @param newPersonal The dialog
     */
    private void dialogElements(MaterialAlertDialogBuilder newPersonal, LinearLayout layout, EditText reasonText,
                                TextView dateText, EditText timeText, Spinner sp) {
        newPersonal.setTitle(getString(R.string.dialog_new_event));
        newPersonal.setMessage(R.string.dialog_new_event_message);
        newPersonal.setView(layout);
        cancelDialog(newPersonal);
        createPersonalEventListener(newPersonal, reasonText, dateText, timeText, sp);
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
        newPersonal.setPositiveButton(getString(R.string.create), (dialog, which) -> {
            if (sp.getSelectedItem() != null) {
                if (reasonText.getText().toString().length() != 0) {
                    createPersonalEvent(reasonText, dateText, timeText, sp);
                } else {
                    toastText(getString(R.string.no_description));
                }
            } else {
                toastText(getString(R.string.add_a_pet));
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
        DateTime dateTime = DateTime.Builder.buildDateTimeString(dateText.getText().toString(),
                timeText.getText().toString());;
        getPet(petName);
        if (isValidTime(timeText.getText().toString()) && reasonText.getText() != null) {
            selectedPet.addEvent(new Event(reasonText.getText().toString(), dateTime));
            communication.newPersonalEvent(selectedPet, reasonText.getText().toString(), dateTime.toString());
        } else {
            toastText(getString(R.string.incorrect_entry));
        }
    }

    /**
     * Initializes the calendar of a alarm.
     * @param date The time and date of the alarm
     * @param c The calendar
     */
    private void calendarAlarmInitialization(DateTime date, Calendar c) {
        String dateTime = date.toString();
        System.out.println(dateTime.toString());
        int year = Integer.parseInt(dateTime.substring(0,4));
        int month = Integer.parseInt(dateTime.substring(5,7));
        int day = Integer.parseInt(dateTime.substring(8,10));
        int hour = Integer.parseInt(dateTime.substring(11,13));
        int min = Integer.parseInt(dateTime.substring(14,16));
        int sec = Integer.parseInt(dateTime.substring(17,19));
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, sec);
    }

    /**
     * Cancel button of the dialog.
     * @param newPersonal The dialog
     */
    private void cancelDialog(MaterialAlertDialogBuilder newPersonal) {
        newPersonal.setNegativeButton(getString(R.string.cancel), (dialog, which) ->
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
        reasonText.setHint(getString(R.string.description));
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
        timeText.setTextColor(Color.parseColor(PRIMARY_COLOR));
        timeText.setText(getString(R.string.default_hour));
        timeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_14);
        time.addView(timeText);
        return timeText;
    }

    /**
     * Initialize the time section of the dialog.
     * @param time The layout to put the time and date
     * @return The dateText
     */
    private TextView initializeTimeLayout(LinearLayout time) throws ParseException {
        time.setOrientation(LinearLayout.HORIZONTAL);
        time.setGravity(Gravity.CENTER_HORIZONTAL);
        time.setWeightSum(2);
        TextView dateText = new TextView(new ContextThemeWrapper(getContext(), R.style.HintStyle));
        DateFormat format = new SimpleDateFormat("yyyy-MM-d");
        Date currentTime = format.parse(selectedDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        dateText.setText(dateFormat.format(currentTime));
        dateText.setTextColor(Color.parseColor(PRIMARY_COLOR));
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
                android.R.layout.simple_spinner_item, (List<String>) getPetsName());
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
        if (time.length() == TIME_LENGTH && time.indexOf(':') == POS2 && time.lastIndexOf(':') == POS5) {
            if (Character.isDigit(time.charAt(0)) && Character.isDigit(time.charAt(1))
                    && Character.isDigit(time.charAt(POS3))) {
                if (Character.isDigit(time.charAt(POS4)) && Character.isDigit(time.charAt(POS6))
                        && Character.isDigit(time.charAt(POS7))) {
                    result = true;
                }
            }
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
    private ArrayList<?> getPetsName() {
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
    private void setUpCalendar() throws ParseException, InvalidFormatException {
        calendar = binding.calendar;
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        String date = dateFormat.format(currentTime);

        addComponents(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)),
            Integer.parseInt(date.substring(9, 10)));

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            try {
                addComponents(year, month, dayOfMonth);
            } catch (ParseException | InvalidFormatException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set up the components.
     */
    private void addComponents(int year, int month, int dayOfMonth) throws ParseException, InvalidFormatException {
        ArrayList<Pet> pets = user.getPets();
        String dateTime = (DateTime.Builder.build(year, month, dayOfMonth)).toString();
        selectedDate = dateTime.substring(0, dateTime.indexOf('T'));
        binding.eventInfoLayout.removeAllViews();
        for (Pet pet : pets) {
            System.out.println("entra aqui bien");
            CalendarEventsView calendarEventsView = new CalendarEventsView(getContext(), null);
            calendarEventsView.showEvents(pet, selectedDate);
            List<PetComponentView> petComponents = calendarEventsView.getPetComponents();
            for (PetComponentView p : petComponents) {
                p.setOnClickListener(v -> deleteEventDialog(p));
            }
            binding.eventInfoLayout.addView(calendarEventsView);
        }
    }

    /**
     * Set up the delete event dialog.
     */
    private void deleteEventDialog(PetComponentView p) {
        MaterialAlertDialogBuilder deleteEvent = new MaterialAlertDialogBuilder(Objects.requireNonNull(
                getContext()), R.style.AlertDialogTheme);
        deleteEvent.setTitle(getString(R.string.delete_event));
        deleteEvent.setMessage(getString(R.string.confirmation));
        Pet pet = p.getPet();
        Event event = ((EventView) p).getEvent();
        deleteEvent.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
            pet.deleteEvent(event);
            communication.deletePersonalEvent(pet, event);
        });
        deleteEvent.setNegativeButton(getString(R.string.no), (dialog, which) -> {
        });
        deleteEvent.show();
    }
}

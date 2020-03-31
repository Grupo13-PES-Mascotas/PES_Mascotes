package org.pesmypetcare.mypetcare.activities.fragments.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.activities.views.CalendarEventsView;
import org.pesmypetcare.mypetcare.databinding.FragmentCalendarBinding;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.DateConversion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding;
    private CalendarCommunication communication;
    private CalendarView calendar;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        communication = (CalendarCommunication) getActivity();
        user = Objects.requireNonNull(communication).getUser();

        setUpCalendar();

        return binding.getRoot();
    }

    /**
     * Set up the calendar.
     */
    private void setUpCalendar() {
        calendar = binding.calendar;
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(currentTime);

        addComponents(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)),
            Integer.parseInt(date.substring(9, 10)));

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            addComponents(year, month, dayOfMonth);
        });
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

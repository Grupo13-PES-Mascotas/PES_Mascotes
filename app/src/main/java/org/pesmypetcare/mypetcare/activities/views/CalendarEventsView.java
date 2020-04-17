package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;

import java.util.ArrayList;
import java.util.List;

public class CalendarEventsView extends LinearLayout {
    private Context context;
    private List<CircularEntryView> petComponents;

    public CalendarEventsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        this.petComponents = new ArrayList<>();
        setOrientation(VERTICAL);
        LinearLayout.LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.START;
        setLayoutParams(params);
    }

    /**
     * Show the events that the pet has got on a date.
     * @param pet The pet that has got the event
     * @param date The date to obtain the events from
     */
    public void showEvents(Pet pet, String date) {
        //pet.addEvent(new Event("Take to vet", "2020-04-03T10:30:00"));
        List<Event> events = pet.getEvents(date);
        for (Event event : events) {
            CircularEntryView circularEntryView = new EventView(context, null, pet, event).initializeComponent();
            addView(circularEntryView);
            this.petComponents.add(circularEntryView);
        }
    }

    /**
     * Get all the views of the pets.
     * @return All the views of the pets
     */
    public List<CircularEntryView> getPetComponents() {
        return petComponents;
    }
}

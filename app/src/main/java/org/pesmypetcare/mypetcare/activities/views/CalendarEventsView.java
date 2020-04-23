package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CalendarEventsView extends LinearLayout {
    private Context context;
    private List<PetComponentView> petComponents;

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
    public void showEvents(Pet pet, String date) throws ParseException {
        //pet.addEvent(new Event("Take to vet", "2020-04-03T10:30:00"));
        List<Event> events = pet.getEvents(date);
        List<Event> periodicEvents = pet.getPeriodicEvents(date);
        events.addAll(periodicEvents);
        for (Event event : events) {
            PetComponentView petComponentView = new EventView(context, null, event).initializePetComponent(pet);
            addView(petComponentView);
            this.petComponents.add(petComponentView);
        }
    }

    /**
     * Get all the views of the pets.
     * @return All the views of the pets
     */
    public List<PetComponentView> getPetComponents() {
        return petComponents;
    }
}

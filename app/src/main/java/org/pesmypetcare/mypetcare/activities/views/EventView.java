package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.utilities.DateConversion;

public class EventView extends PetComponentView {
    private Event event;

    public EventView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventView(Context context, AttributeSet attrs, Event event) {
        super(context, attrs);
        this.event = event;
    }

    @Override
    protected String getFirstLineText(Pet pet) {
        return event.getDescription();
    }

    @Override
    protected String getSecondLineText(Pet pet) {
        return DateConversion.getHourMinutes(event.getDateTime());
    }
}

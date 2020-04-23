package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

public class EventView extends PetComponentView {
    private Event event;
    private static final int TEN = 10;

    public EventView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventView(Context context, AttributeSet attrs, Event event) {
        super(context, attrs);
        this.event = event;
    }

    /**
     * Get the event of the view.
     * @return The event of the view
     */
    public Event getEvent() {
        return event;
    }

    @Override
    protected String getFirstLineText(Pet pet) {
        return pet.getName() + " - " + event.getDescription();
    }

    @Override
    protected String getSecondLineText(Pet pet) {
        DateTime dt = event.getDateTime();
        StringBuilder time = new StringBuilder();
        int h = dt.getHour();
        int min = dt.getMinutes();
        if (h < TEN) {
            time.append('0');
        }
        time.append(h).append(':');
        if (min < TEN) {
            time.append('0');
        }
        time.append(min);
        return time.toString();
    }
}

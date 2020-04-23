package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

public class EventView extends CircularEntryView {
    private Pet pet;
    private Event event;
    private final int TEN = 10;

    public EventView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventView(Context context, AttributeSet attrs, Pet pet, Event event) {
        super(context, attrs);
        this.pet = pet;
        this.event = event;
    }

    @Override
    protected CircularImageView getImage() {
        CircularImageView image = new CircularImageView(getCurrentActivity(), null);
        Drawable petImageDrawable = getResources().getDrawable(R.drawable.single_paw);

        if (pet.getProfileImage() != null) {
            petImageDrawable = new BitmapDrawable(getResources(), pet.getProfileImage());
        }

        image.setDrawable(petImageDrawable);
        int imageDimensions = getImageDimensions();
        image.setLayoutParams(new LinearLayout.LayoutParams(imageDimensions, imageDimensions));
        int imageId = View.generateViewId();
        image.setId(imageId);

        return image;
    }

    @Override
    public Object getObject() {
        return event;
    }

    /**
     * Get the event of the view.
     * @return The event of the view
     */
    public Event getEvent() {
        return event;
    }

    @Override
    protected String getFirstLineText() {
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

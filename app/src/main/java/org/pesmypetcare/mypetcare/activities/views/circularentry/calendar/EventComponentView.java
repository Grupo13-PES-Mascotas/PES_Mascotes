package org.pesmypetcare.mypetcare.activities.views.circularentry.calendar;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularImageView;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.utilities.DateConversion;

/**
 * @author Daniel Clemente & Enric Hernando
 */
public class EventComponentView extends CircularEntryView {
    private Pet pet;
    private Event event;

    public EventComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventComponentView(Context context, AttributeSet attrs, Pet pet, Event event) {
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
        return pet;
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
    protected String getSecondLineText() {
        return DateConversion.getHourMinutes(event.getDateTime().toString());
    }

    @Override
    protected ImageView getRightImage() {
        return null;
    }

    @Override
    protected ImageView getBottomImage() {
        return null;
    }
}

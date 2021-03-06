package org.pesmypetcare.mypetcare.activities.views.circularentry.petsinfo;

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

/**
 * @author Xavier Campos
 */
public class PetsInfoComponentView extends CircularEntryView {
    private Pet pet;

    public PetsInfoComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PetsInfoComponentView(Context context, AttributeSet attrs, Pet pet) {
        super(context, attrs);
        this.pet = pet;
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

    @Override
    protected String getFirstLineText() {
        return pet.getName();
    }

    @Override
    protected String getSecondLineText() {
        return String.format("%s - %s", pet.getBreed(), pet.getBirthDateInstance().toDateStringReverse());
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

package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.pets.Pet;

public class PetsInfoView extends CircularEntryView {
    private Pet pet;

    public PetsInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PetsInfoView(Context context, AttributeSet attrs, Pet pet) {
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
        return String.format("%s - %s", pet.getBreed(), pet.getBirthDate());
    }
}

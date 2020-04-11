package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;

import org.pesmypetcare.mypetcare.features.pets.Pet;

public class PetsInfoView extends PetComponentView {
    public PetsInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected String getFirstLineText(Pet pet) {
        return pet.getName();
    }

    @Override
    protected String getSecondLineText(Pet pet) {
        return String.format("%s - %s", pet.getBreed(), pet.getBirthDate());
    }
}

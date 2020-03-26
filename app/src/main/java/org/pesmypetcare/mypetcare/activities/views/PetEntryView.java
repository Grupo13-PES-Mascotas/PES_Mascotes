package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.pesmypetcare.mypetcare.features.pets.Pet;

public class PetEntryView extends ConstraintLayout {
    private Pet pet;

    public PetEntryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}

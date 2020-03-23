package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.ArrayList;

public class MainMenuView extends LinearLayout {
    private Context currentActivity;
    private ArrayList<Pet> userPets;

    public MainMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.currentActivity = context;
        this.setOrientation(VERTICAL);
        LinearLayout.LayoutParams params= new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.START;
        this.setLayoutParams(params);
    }

    public void showPets(User currentUser) {
        this.userPets = currentUser.getPets();
        Space space;
        space = new Space(currentActivity);
        space.setMinimumHeight(55);
        this.addView(space);
        while (!userPets.isEmpty()) {
            Pet currentPet = userPets.remove(0);
            PetComponentView component = new PetComponentView(currentActivity, null);
            this.addView(component.initializePetComponent(currentPet));
            space = new Space(currentActivity);
            space.setMinimumHeight(55);
            this.addView(space);
        }
    }
}

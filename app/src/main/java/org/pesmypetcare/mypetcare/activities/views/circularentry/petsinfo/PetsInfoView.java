package org.pesmypetcare.mypetcare.activities.views.circularentry.petsinfo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.ArrayList;
import java.util.List;

public class PetsInfoView extends LinearLayout {
    private Context currentActivity;
    private List<CircularEntryView> petComponents;
    private final int SPACE_SIZE = 40;

    public PetsInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.currentActivity = context;
        this.petComponents = new ArrayList<>();
        this.setOrientation(VERTICAL);
        LinearLayout.LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.START;
        this.setLayoutParams(params);
    }

    /**
     * Method responsible for showing all the pets from the current user.
     * @param currentUser The current user of the application
     */
    public void showPets(User currentUser) {
        List<Pet> userPets = new ArrayList<>(currentUser.getPets());
        Space space;
        space = initializeSpacer();
        this.addView(space);
        while (!userPets.isEmpty()) {
            initializeComponent(userPets);
        }
    }

    /**
     * Method responsible for creating and adding all the required pet components.
     * @param userPets The list containing all the pets
     */
    private void initializeComponent(List<Pet> userPets) {
        Space space;
        Pet currentPet = userPets.remove(0);
        CircularEntryView component = new PetsInfoComponentView(currentActivity, null, currentPet)
            .initializeComponent();
        this.addView(component);
        this.petComponents.add(component);
        space = initializeSpacer();
        this.addView(space);
    }

    /**
     * Method responsible for initializing the spacers.
     * @return The initialized spacer;
     */
    private Space initializeSpacer() {
        Space space;
        space = new Space(currentActivity);
        space.setMinimumHeight(SPACE_SIZE);
        return space;
    }

    /**
     * Getter of the pet components arraylist.
     * @return The arraylist containing all the pet components
     */
    public List<CircularEntryView> getPetComponents() {
        return petComponents;
    }
}

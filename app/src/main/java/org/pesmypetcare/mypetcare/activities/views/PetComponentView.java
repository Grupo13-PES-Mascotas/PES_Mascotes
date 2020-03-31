package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.pets.Pet;

public abstract class PetComponentView extends ConstraintLayout {
    private Context currentActivity;
    private Pet pet;
    private final int PADDING = 15;
    private final int IMAGELAYOUTMARGIN = 10;
    private final int PETINFOIMAGEMARGIN = 40;
    private final int IMAGEDIMESIONS = 150;

    public PetComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.currentActivity = context;
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setPadding(PADDING, PADDING, 0, 0);
        this.setId(View.generateViewId());
    }

    /**
     * Method responsible of the initialization of an pet component for a specific pet.
     * @param currentPet The pet that has to be shown in the component
     * @return The initialized component
     */
    public PetComponentView initializePetComponent(Pet currentPet) {
        pet = currentPet;
        CircularImageView image = addPetImage();
        this.addView(image);
        LinearLayout petInfo = addPetInfo();
        this.addView(petInfo);
        generateConstraints(image.getId(), petInfo.getId(), this.getId(), this);
        return this;
    }

    /**
     * Get the current pet.
     * @return The current pet
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * Method responsible for generating the appropriate constraints.
     * @param imageId The id of the circular image view
     * @param petId The id of the linear layout that contains the pet info
     * @param layoutId The id of the container
     * @param petComponentView The container where we want to set the constraints
     */
    private void generateConstraints(int imageId, int petId, int layoutId, PetComponentView petComponentView) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(petComponentView);
        constraintSet.connect(imageId, ConstraintSet.LEFT, layoutId, ConstraintSet.LEFT, IMAGELAYOUTMARGIN);
        constraintSet.connect(petId, ConstraintSet.LEFT, imageId, ConstraintSet.RIGHT, PETINFOIMAGEMARGIN);
        constraintSet.connect(petId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP,
            PETINFOIMAGEMARGIN - IMAGELAYOUTMARGIN);
        constraintSet.applyTo(petComponentView);
    }

    /**
     * Method responsible for handling the pet image.
     * @return The Circular image view containing the pet image
     */
    private CircularImageView addPetImage() {
        CircularImageView image = new CircularImageView(currentActivity, null);
        image.setDrawable(getResources().getDrawable(R.drawable.single_paw, null));
        image.setLayoutParams(new LinearLayout.LayoutParams(IMAGEDIMESIONS, IMAGEDIMESIONS));
        int imageId = View.generateViewId();
        image.setId(imageId);
        return image;
    }

    /**
     * Method responsible for generating the linear layout that contains the pet info.
     * @return The linear layout that contains the pet info
     */
    private LinearLayout addPetInfo() {
        LinearLayout petInfo = new LinearLayout(currentActivity);
        petInfo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        petInfo.setOrientation(LinearLayout.VERTICAL);
        firstLineTextInitializer(petInfo);
        secondLineTextInitializer(petInfo);
        int petInfoId = View.generateViewId();
        petInfo.setId(petInfoId);
        return petInfo;
    }

    /**
     * Method responsible for creating the text view that will contain the pet info.
     * @param petInfo The parent layout
     */
    private void secondLineTextInitializer(LinearLayout petInfo) {
        TextView infoText = new TextView(currentActivity);
        infoText.setText(getSecondLineText(pet));
        infoText.setGravity(Gravity.START + Gravity.CENTER_VERTICAL);
        infoText.setTextColor(Color.BLACK);
        petInfo.addView(infoText);
    }

    /**
     * Method responsible for creating the text view that will contain the pet name.
     * @param petInfo The parent layout
     */
    private void firstLineTextInitializer(LinearLayout petInfo) {
        TextView nameText = new TextView(currentActivity);
        nameText.setText(getFirstLineText(pet));
        nameText.setTypeface(null, Typeface.BOLD);
        nameText.setGravity(Gravity.START + Gravity.CENTER_VERTICAL);
        nameText.setTextColor(Color.BLACK);
        petInfo.addView(nameText);
    }

    /**
     * Get the first line of the component text.
     * @param pet The pet that appears in the component
     * @return The first line of the component text.
     */
    protected abstract String getFirstLineText(Pet pet);

    /**
     * Get the second line of the component text.
     * @param pet The pet that appears in the component
     * @return The second line of the component text.
     */
    protected abstract String getSecondLineText(Pet pet);
}

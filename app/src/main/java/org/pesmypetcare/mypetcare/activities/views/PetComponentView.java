package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
    private final int IMAGE_LAYOUT_MARGIN = 10;
    private final int PET_INFO_IMAGE_MARGIN = 40;

    private final int IMAGE_DIMESIONS = 150;

    public PetComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.currentActivity = context;
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setPadding(PADDING, PADDING, 0, 0);
        this.setId(View.generateViewId());
    }

    public PetComponentView initializeComponent() {
        CircularImageView image = getImage();
        addView(image);
        LinearLayout info = getInfo();
        addView(info);
        generateConstraints(image.getId(), info.getId(), getId(), this);
        return this;
    }

    protected abstract CircularImageView getImage();

    /**
     * Method responsible of the initialization of an pet component for a specific pet.
     * @param currentPet The pet that has to be shown in the component
     * @return The initialized component
     */
    public PetComponentView initializePetComponent(Pet currentPet) {
        pet = currentPet;
        CircularImageView image = addPetImage();
        this.addView(image);
        LinearLayout petInfo = getInfo();
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

    public abstract Object getObject();

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
        constraintSet.connect(imageId, ConstraintSet.LEFT, layoutId, ConstraintSet.LEFT, IMAGE_LAYOUT_MARGIN);
        constraintSet.connect(petId, ConstraintSet.LEFT, imageId, ConstraintSet.RIGHT, PET_INFO_IMAGE_MARGIN);
        constraintSet.connect(petId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP,
            PET_INFO_IMAGE_MARGIN - IMAGE_LAYOUT_MARGIN);
        constraintSet.applyTo(petComponentView);
    }

    /**
     * Method responsible for handling the pet image.
     * @return The Circular image view containing the pet image
     */
    private CircularImageView addPetImage() {
        CircularImageView image = new CircularImageView(currentActivity, null);
        Drawable petImageDrawable = getResources().getDrawable(R.drawable.single_paw);

        if (pet.getProfileImage() != null) {
            petImageDrawable = new BitmapDrawable(getResources(), pet.getProfileImage());
        }

        image.setDrawable(petImageDrawable);
        image.setLayoutParams(new LinearLayout.LayoutParams(IMAGE_DIMESIONS, IMAGE_DIMESIONS));
        int imageId = View.generateViewId();
        image.setId(imageId);

        return image;
    }

    /**
     * Method responsible for generating the linear layout that contains the pet info.
     * @return The linear layout that contains the pet info
     */
    private LinearLayout getInfo() {
        LinearLayout info = new LinearLayout(currentActivity);
        info.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        info.setOrientation(LinearLayout.VERTICAL);
        firstLineTextInitializer(info);
        secondLineTextInitializer(info);
        int infoId = View.generateViewId();
        info.setId(infoId);
        return info;
    }

    /**
     * Method responsible for creating the text view that will contain the pet info.
     * @param info The parent layout
     */
    private void secondLineTextInitializer(LinearLayout info) {
        TextView infoText = new TextView(currentActivity);
        infoText.setText(getSecondLineText());
        infoText.setGravity(Gravity.START + Gravity.CENTER_VERTICAL);
        infoText.setTextColor(Color.BLACK);
        info.addView(infoText);
    }

    /**
     * Method responsible for creating the text view that will contain the pet name.
     * @param info The parent layout
     */
    private void firstLineTextInitializer(LinearLayout info) {
        TextView nameText = new TextView(currentActivity);
        nameText.setText(getFirstLineText());
        nameText.setTypeface(null, Typeface.BOLD);
        nameText.setGravity(Gravity.START + Gravity.CENTER_VERTICAL);
        nameText.setTextColor(Color.BLACK);
        info.addView(nameText);
    }

    public Context getCurrentActivity() {
        return currentActivity;
    }

    public int getImageDimensions() {
        return IMAGE_DIMESIONS;
    }

    /**
     * Get the first line of the component text.
     * @return The first line of the component text.
     */
    protected abstract String getFirstLineText();

    /**
     * Get the second line of the component text.
     * @return The second line of the component text.
     */
    protected abstract String getSecondLineText();

    /*if (pet.getProfileImage() == null) {
            try {
                System.out.println("TRY");
                byte[] bytes = ImageManager.readImage(ImageManager.PROFILE_IMAGES_PATH,
                    pet.getOwner().getUsername() + '_' + pet.getName());
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                pet.setProfileImage(bitmap);
                petImageDrawable = new BitmapDrawable(getResources(), bitmap);
            } catch (IOException e) {
                System.out.println("CATCH");
                petImageDrawable = getResources().getDrawable(R.drawable.single_paw, null);
                pet.setProfileImage(((BitmapDrawable) petImageDrawable).getBitmap());
            } finally {
                MainActivity.setPetImage(pet);
            }
        } else {
            System.out.println("ELSE");
            petImageDrawable = new BitmapDrawable(getResources(), pet.getProfileImage());
        }*/
}

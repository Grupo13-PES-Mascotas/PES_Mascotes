package org.pesmypetcare.mypetcare.activities.views.circularentry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

/**
 * @author Xavier Campos
 */
public abstract class CircularEntryView extends ConstraintLayout {
    private static final int PADDING = 15;
    private static final int IMAGE_LAYOUT_MARGIN = 10;
    private static final int PET_INFO_IMAGE_MARGIN = 40;
    private static final int RIGHT_IMAGE_MARGIN = 40;
    private static final int BOTTOM_IMAGE_MARGIN = 40;
    private static final int IMAGE_DIMENSIONS = 150;

    private Context currentActivity;

    public CircularEntryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.currentActivity = context;
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setPadding(PADDING, PADDING, 0, 0);
        this.setId(View.generateViewId());
    }

    /**
     * Initialize the component.
     * @return The actual circular entry view
     */
    public CircularEntryView initializeComponent() {
        CircularImageView image = getImage();
        addView(image);
        LinearLayout info = getInfo();
        addView(info);
        ImageView rightImage = getRightImage();
        int rightImageId = -1;

        if (rightImage != null) {
            addView(rightImage);
            rightImageId = rightImage.getId();
        }

        /*ImageView bottomImage = getBottomImage();
        int bottomImageId = -1;

        if (bottomImage != null) {
            addView(bottomImage);
            bottomImageId = bottomImage.getId();
        }*/

        generateConstraints(image.getId(), info.getId(), rightImageId, getId(), this);
        return this;
    }

    /**
     * Method responsible for generating the appropriate constraints.
     * @param imageId The id of the circular image view
     * @param petId The id of the linear layout that contains the pet info
     * @param rightImageId The id of the right image
     * @param layoutId The id of the container
     * @param circularEntryView The container where we want to set the constraints
     */
    private void generateConstraints(int imageId, int petId, int rightImageId, int layoutId,
                                     CircularEntryView circularEntryView) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(circularEntryView);
        constraintSet.connect(imageId, ConstraintSet.LEFT, layoutId, ConstraintSet.LEFT, IMAGE_LAYOUT_MARGIN);
        constraintSet.connect(petId, ConstraintSet.LEFT, imageId, ConstraintSet.RIGHT, PET_INFO_IMAGE_MARGIN);
        constraintSet.connect(petId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP, PET_INFO_IMAGE_MARGIN);

        if (rightImageId != -1) {
            constraintSet.connect(rightImageId, ConstraintSet.RIGHT, layoutId, ConstraintSet.RIGHT);
            constraintSet.connect(rightImageId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP,
                RIGHT_IMAGE_MARGIN - IMAGE_LAYOUT_MARGIN);
        }

        /*if (bottomImageId != -1) {
            constraintSet.connect(bottomImageId, ConstraintSet.LEFT, layoutId, ConstraintSet.LEFT);
            constraintSet.connect(bottomImageId, ConstraintSet.RIGHT, layoutId, ConstraintSet.RIGHT);
            constraintSet.connect(bottomImageId, ConstraintSet.TOP, petId, ConstraintSet.BOTTOM, 20);
            constraintSet.setVerticalBias(bottomImageId, 0.0f);
        }*/

        constraintSet.applyTo(circularEntryView);
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
        bottomImageInitializer(info);
        int infoId = View.generateViewId();
        info.setId(infoId);
        return info;
    }

    private void bottomImageInitializer(LinearLayout info) {
        ImageView bottomImage = getBottomImage();

        if (bottomImage != null) {
            info.addView(bottomImage);
        }
    }

    /**
     * Method responsible for creating the text view that will contain the pet info.
     * @param info The parent layout
     */
    @SuppressLint("WrongConstant")
    private void secondLineTextInitializer(LinearLayout info) {
        TextView infoText = new TextView(currentActivity);
        infoText.setText(getSecondLineText());
        infoText.setGravity(Gravity.START + Gravity.CENTER_VERTICAL);
        infoText.setTextColor(Color.BLACK);
        infoText.setBreakStrategy(Layout.BREAK_STRATEGY_BALANCED);
        info.addView(infoText);
    }

    /**
     * Method responsible for creating the text view that will contain the pet name.
     * @param info The parent layout
     */
    @SuppressLint("WrongConstant")
    private void firstLineTextInitializer(LinearLayout info) {
        TextView nameText = new TextView(currentActivity);
        nameText.setText(getFirstLineText());
        nameText.setTypeface(null, Typeface.BOLD);
        nameText.setGravity(Gravity.START + Gravity.CENTER_VERTICAL);
        nameText.setTextColor(Color.BLACK);
        nameText.setBreakStrategy(Layout.BREAK_STRATEGY_BALANCED);
        info.addView(nameText);
    }

    /**
     * Get the current activity.
     * @return The current activity
     */
    public Context getCurrentActivity() {
        return currentActivity;
    }

    /**
     * Get the image dimensions.
     * @return The image dimensions
     */
    public int getImageDimensions() {
        return IMAGE_DIMENSIONS;
    }

    /**
     * Get the image of the circular entry view.
     * @return The image of the circular entry view
     */
    protected abstract CircularImageView getImage();

    /**
     * Get the object of the circular entry view.
     * @return The object of the circular entry view
     */
    public abstract Object getObject();

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

    /**
     * Get the right image if it is defined or null otherwise.
     * @return The right image
     */
    protected abstract ImageView getRightImage();

    /**
     * Get the bottom image if it is defined or null otherwise.
     * @return The bottom image
     */
    protected abstract ImageView getBottomImage();
}

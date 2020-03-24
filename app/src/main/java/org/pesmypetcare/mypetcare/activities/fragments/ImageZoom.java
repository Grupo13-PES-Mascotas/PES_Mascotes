package org.pesmypetcare.mypetcare.activities.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.CircularImageView;
import org.pesmypetcare.mypetcare.databinding.FragmentImageZoomBinding;

import java.util.Objects;

public class ImageZoom extends Fragment {
    private static final String[] IMAGE_MIME_TYPES = {"image/jpeg", "image/png"};
    private static final int GALLERY_ZOOM_REQUEST_CODE = 0;
    private static final float RADIUS = 1000.0f;
    private static Drawable drawable;
    private static boolean isMainActivity;

    private FragmentImageZoomBinding binding;

    public ImageZoom(Drawable drawable) {
        ImageZoom.drawable = drawable;
    }

    /**
     * Get the drawable of the displaying image.
     * @return The drawable of the displaying image
     */

    public static Drawable getDrawable() {
        return drawable;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentImageZoomBinding.inflate(inflater, container, false);

        initializeCircularImageView();
        initializeFloatingButtons();

        return binding.getRoot();
    }

    /**
     * Initialize the floating buttons.
     */
    private void initializeFloatingButtons() {
        FloatingActionButton flModifyImage = binding.flModifyImage;
        FloatingActionButton flDeleteImage = binding.flDeleteImage;

        flModifyImage.setOnClickListener(view -> {
            Intent imagePicker = getGalleryIntent();
            startActivityForResult(imagePicker, GALLERY_ZOOM_REQUEST_CODE);
        });

        flDeleteImage.setOnClickListener(view -> {
            MaterialAlertDialogBuilder alertDialog = createAlertDialog();
            alertDialog.show();
        });
    }

    /**
     * Creates the alert dialog to display before deleting the image.
     * @return The alert dialog to display before deleting the image
     */
    private MaterialAlertDialogBuilder createAlertDialog() {
        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(
            Objects.requireNonNull(getActivity()));
        alertDialog.setTitle(R.string.delete_pet_image_title);
        alertDialog.setMessage(R.string.delete_pet_image_text);
        alertDialog.setPositiveButton(R.string.affirmative_response, (dialog, which) -> {
            setDrawable(getResources().getDrawable(R.drawable.single_paw, null));
        });
        alertDialog.setNegativeButton(R.string.negative_response, null);
        return alertDialog;
    }

    /**
     * Gets the gallery intent.
     * @return The gallery intent
     */
    private Intent getGalleryIntent() {
        Intent imagePicker = new Intent(Intent.ACTION_PICK);
        imagePicker.setType("image/*");
        imagePicker.putExtra(Intent.EXTRA_MIME_TYPES, IMAGE_MIME_TYPES);
        return imagePicker;
    }

    /**
     * Initialize the circular image view.
     */
    private void initializeCircularImageView() {
        CircularImageView circularImageView = binding.displayedImage;
        circularImageView.setDrawable(drawable);
        circularImageView.setRadius(RADIUS);
    }

    /**
     * Set the drawable to display.
     * @param drawable The drawable to set
     */
    public void setDrawable(Drawable drawable) {
        ImageZoom.drawable = drawable;
        initializeCircularImageView();
    }

    public static boolean isMainActivity() {
        return isMainActivity;
    }

    public static void setIsMainActivity(boolean isMainActivity) {
        ImageZoom.isMainActivity = isMainActivity;
    }
}

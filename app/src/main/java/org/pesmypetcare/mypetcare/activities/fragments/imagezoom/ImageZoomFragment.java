package org.pesmypetcare.mypetcare.activities.fragments.imagezoom;

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
import org.pesmypetcare.mypetcare.activities.MainActivity;
import org.pesmypetcare.mypetcare.activities.fragments.community.groups.InfoGroupFragment;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetFragment;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularImageView;
import org.pesmypetcare.mypetcare.databinding.FragmentImageZoomBinding;
import org.pesmypetcare.mypetcare.utilities.androidservices.GalleryService;

import java.util.Objects;

/**
 * @author Albert Pinto
 */
public class ImageZoomFragment extends Fragment {
    private static final int GALLERY_ZOOM_REQUEST_CODE = 100;
    private static final float RADIUS = 1000.0f;
    private static Drawable drawable;
    private static int origin;
    private static boolean isDefaultImage = true;
    private static boolean isImageDeleted = false;

    private FragmentImageZoomBinding binding;
    private ImageZoomCommunication communication;

    public ImageZoomFragment(Drawable drawable) {
        ImageZoomFragment.drawable = drawable;
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
        communication = (ImageZoomCommunication) getActivity();

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
            Intent imagePicker = GalleryService.getGalleryIntent();
            MainActivity.setFragmentRequestCode(GALLERY_ZOOM_REQUEST_CODE);
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
        switch (origin) {
            case MainActivity.MAIN_ACTIVITY_ZOOM_IDENTIFIER:
                alertDialog.setTitle(R.string.delete_user_image_title);
                alertDialog.setMessage(R.string.delete_user_image_text);
                break;
            case InfoPetFragment.INFO_PET_ZOOM_IDENTIFIER:
                alertDialog.setTitle(R.string.delete_pet_image_title);
                alertDialog.setMessage(R.string.delete_pet_image_text);
                break;
            case InfoGroupFragment.INFO_GROUP_ZOOM_IDENTIFIER:
                alertDialog.setTitle(R.string.delete_group_image_title);
                alertDialog.setMessage(R.string.delete_group_image_text);
                break;
            default:
        }

        alertDialog.setPositiveButton(R.string.affirmative_response, (dialog, which) -> {
            isImageDeleted = true;
            switch (origin) {
                case MainActivity.MAIN_ACTIVITY_ZOOM_IDENTIFIER:
                    setDrawable(getResources().getDrawable(R.drawable.user_icon_sample, null));
                    MainActivity.setDefaultUserImage();
                    break;
                case InfoPetFragment.INFO_PET_ZOOM_IDENTIFIER:
                    setDrawable(getResources().getDrawable(R.drawable.single_paw, null));
                    InfoPetFragment.setDefaultPetImage();
                    InfoPetFragment.setIsDefaultPetImage(true);
                    break;
                case InfoGroupFragment.INFO_GROUP_ZOOM_IDENTIFIER:
                    setDrawable(getResources().getDrawable(R.drawable.icon_group, null));
                    break;
                default:
            }

            /*
            if (origin == MainActivity.MAIN_ACTIVITY_ZOOM_IDENTIFIER) {
                setDrawable(getResources().getDrawable(R.drawable.user_icon_sample, null));
                MainActivity.setDefaultUserImage();
            } else {
                setDrawable(getResources().getDrawable(R.drawable.single_paw, null));
                InfoPetFragment.setDefaultPetImage();
                InfoPetFragment.setIsDefaultPetImage(true);
            }*/
        });
        alertDialog.setNegativeButton(R.string.negative_response, null);
        return alertDialog;
    }

    public static void setIsDefaultImage(boolean isDefaultImage) {
        ImageZoomFragment.isDefaultImage = isDefaultImage;
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
        ImageZoomFragment.drawable = drawable;
        initializeCircularImageView();
    }

    public static int getOrigin() {
        return origin;
    }

    public static void setOrigin(int origin) {
        ImageZoomFragment.origin = origin;
    }

    public static boolean isImageDeleted() {
        return isImageDeleted;
    }

    public static void setIsImageDeleted(boolean isImageDeleted) {
        ImageZoomFragment.isImageDeleted = isImageDeleted;
    }
}

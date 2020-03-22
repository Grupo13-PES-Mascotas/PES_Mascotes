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
    public static final int GALLERY_ZOOM_REQUEST_CODE = 0;
    private static final float RADIUS = 1000.0f;

    private FragmentImageZoomBinding binding;
    private static Drawable drawable;

    public ImageZoom(Drawable drawable) {
        ImageZoom.drawable = drawable;
    }

    public static Drawable getDrawable() {
        return drawable;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentImageZoomBinding.inflate(inflater, container, false);

        setCircularImageView();
        setFloatingButtons();

        return binding.getRoot();
    }

    private void setFloatingButtons() {
        FloatingActionButton flModifyImage = binding.flModifyImage;
        FloatingActionButton flDeleteImage = binding.flDeleteImage;

        flModifyImage.setOnClickListener(view -> {
            Intent imagePicker = new Intent(Intent.ACTION_PICK);
            imagePicker.setType("image/*");
            imagePicker.putExtra(Intent.EXTRA_MIME_TYPES, IMAGE_MIME_TYPES);
            startActivityForResult(imagePicker, GALLERY_ZOOM_REQUEST_CODE);
        });

        flDeleteImage.setOnClickListener(view -> {
            MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(
                Objects.requireNonNull(getActivity()));
            alertDialog.setTitle(R.string.delete_pet_image_title);
            alertDialog.setMessage(R.string.delete_pet_image_text);
            alertDialog.setPositiveButton(R.string.affirmative_response, (dialog, which) -> {
                setDrawable(getResources().getDrawable(R.drawable.single_paw));
            });
            alertDialog.setNegativeButton(R.string.negative_response, null);
            alertDialog.show();
        });
    }

    private void setCircularImageView() {
        CircularImageView circularImageView = binding.displayedImage;
        circularImageView.setDrawable(drawable);
        circularImageView.setRadius(RADIUS);
    }

    public void setDrawable(Drawable drawable) {
        ImageZoom.drawable = drawable;
        setCircularImageView();
    }
}

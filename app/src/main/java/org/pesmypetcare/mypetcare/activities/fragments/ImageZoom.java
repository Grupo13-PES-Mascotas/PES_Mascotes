package org.pesmypetcare.mypetcare.activities.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.pesmypetcare.mypetcare.activities.views.CircularImageView;
import org.pesmypetcare.mypetcare.databinding.FragmentImageZoomBinding;

public class ImageZoom extends Fragment {
    private static final String[] IMAGE_MIME_TYPES = {"image/jpeg", "image/png"};
    public static final int GALLERY_ZOOM_REQUEST_CODE = 0;
    private static final float RADIUS = 1000.0f;

    private FragmentImageZoomBinding binding;
    private Drawable drawable;
    private CircularImageView circularImageView;
    private FloatingActionButton flModifyImage;
    private FloatingActionButton flDeleteImage;
    private InfoPetFragment infoPetFragment;
    private ImageZoomCommunication communication;

    public ImageZoom(Drawable drawable, InfoPetFragment infoPetFragment) {
        this.drawable = drawable;
        this.infoPetFragment = infoPetFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentImageZoomBinding.inflate(inflater, container, false);
        communication = (ImageZoomCommunication) getActivity();

        setCircularImageView();
        setFloatingButtons();

        return binding.getRoot();
    }

    private void setFloatingButtons() {
        flModifyImage = binding.flModifyImage;
        flDeleteImage = binding.flDeleteImage;

        flModifyImage.setOnClickListener(view -> {
            Intent imagePicker = new Intent(Intent.ACTION_PICK);
            imagePicker.setType("image/*");
            imagePicker.putExtra(Intent.EXTRA_MIME_TYPES, IMAGE_MIME_TYPES);
            startActivityForResult(imagePicker, GALLERY_ZOOM_REQUEST_CODE);
        });

        flDeleteImage.setOnClickListener(view -> {
            InfoPetFragment.setPetProfileDrawable(drawable);
            communication.changeToPetInformation(new InfoPetFragment());
        });
    }

    private void setCircularImageView() {
        circularImageView = binding.displayedImage;
        circularImageView.setDrawable(drawable);
        circularImageView.setRadius(RADIUS);
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        setCircularImageView();
    }
}

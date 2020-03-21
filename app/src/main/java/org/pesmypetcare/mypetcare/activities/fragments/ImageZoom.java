package org.pesmypetcare.mypetcare.activities.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentImageZoomBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageZoom extends Fragment {
    private static final String[] IMAGE_MIME_TYPES = {"image/jpeg", "image/png"};
    private static final int GALLERY_REQUEST_CODE = 1;

    private FragmentImageZoomBinding binding;
    private Bitmap image;
    private ImageView imageView;
    private FloatingActionButton flModifyImage;
    private FloatingActionButton flDeleteImage;

    public ImageZoom(Bitmap image) {
        this.image = image;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentImageZoomBinding.inflate(inflater, container, false);
        imageView = binding.displayedImage;

        setUpFloatingActionButtons();
        setImageBitmap(image);

        return binding.getRoot();
    }

    private void setUpFloatingActionButtons() {
        flModifyImage = binding.flModifyImage;
        flDeleteImage = binding.flDeleteImage;

        flModifyImage.setOnClickListener(view -> {
            Intent imagePicker = new Intent(Intent.ACTION_PICK);
            imagePicker.setType("image/*");
            imagePicker.putExtra(Intent.EXTRA_MIME_TYPES, IMAGE_MIME_TYPES);
            startActivityForResult(imagePicker, GALLERY_REQUEST_CODE);
        });
    }

    public void setImageBitmap(Bitmap image) {
        imageView.setImageBitmap(image);
    }
}

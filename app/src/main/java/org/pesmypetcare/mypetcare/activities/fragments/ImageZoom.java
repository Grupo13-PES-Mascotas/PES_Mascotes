package org.pesmypetcare.mypetcare.activities.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentImageZoomBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageZoom extends Fragment {
    private FragmentImageZoomBinding binding;
    private Bitmap image;
    private ImageView imageView;

    public ImageZoom(Bitmap image) {
        this.image = image;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentImageZoomBinding.inflate(inflater, container, false);
        imageView = binding.displayedImage;

        imageView.setImageBitmap(image);

        return binding.getRoot();
    }
}

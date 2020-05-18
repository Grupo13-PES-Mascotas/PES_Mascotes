package org.pesmypetcare.mypetcare.utilities.androidservices;

import android.content.Intent;

/**
 * @author Albert Pinto
 */
public class GalleryService {
    private static final String[] IMAGE_MIME_TYPES = {"image/jpeg", "image/png"};

    private GalleryService() {
        // Private constructor
    }

    /**
     * Gets the gallery intent.
     * @return The gallery intent
     */
    public static Intent getGalleryIntent() {
        Intent imagePicker = new Intent(Intent.ACTION_PICK);
        imagePicker.setType("image/*");
        imagePicker.putExtra(Intent.EXTRA_MIME_TYPES, IMAGE_MIME_TYPES);
        return imagePicker;
    }
}

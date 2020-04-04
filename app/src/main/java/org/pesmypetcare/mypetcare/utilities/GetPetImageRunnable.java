package org.pesmypetcare.mypetcare.utilities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;

import java.io.IOException;

public class GetPetImageRunnable implements Runnable {
    private int actual;
    private String username;
    private String petName;
    private Bitmap defaultBitmap;

    public GetPetImageRunnable(int actual, String username, String petName, Bitmap defaultBitmap) {
        this.actual = actual;
        this.username = username;
        this.petName = petName;
        this.defaultBitmap = defaultBitmap;
    }

    @Override
    public void run() {
        Bitmap petImage = null;

        try {
            byte[] bytes = ImageManager.readImage(ImageManager.PROFILE_IMAGES_PATH, username + '_' + petName);
            petImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (IOException e) {
            petImage = defaultBitmap;
        } finally {
            MainActivity.setPetBitmapImage(actual, petImage);
        }
    }
}

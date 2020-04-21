package org.pesmypetcare.mypetcare.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

        System.out.println("Start reading image " + actual);

        try {
            byte[] bytes = ImageManager.readImage(ImageManager.PET_PROFILE_IMAGES_PATH, username + '_' + petName);
            petImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (IOException e) {
            MainActivity.incrementCountNotImage(actual);
        } finally {
            MainActivity.setPetBitmapImage(actual, petImage);
        }

        System.out.println("Finish reading image " + actual);
    }
}

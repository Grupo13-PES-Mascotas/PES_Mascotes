package org.pesmypetcare.mypetcare.activities.communication;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.pesmypetcare.mypetcare.features.pets.Pet;

public interface InfoPetCommunication {

    void makeZoomImage(Drawable drawable);

    void updatePetImage(Pet pet, Bitmap newImage);
}

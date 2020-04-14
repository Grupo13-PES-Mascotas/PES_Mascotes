package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;

public interface InfoPetCommunication {

    /**
     * Makes a zoom to an image using the drawable of it.
     * @param drawable Drawable of the picture from which the zoom has to be made
     */
    void makeZoomImage(Drawable drawable);

    /**
     * Updates the image of the given pet.
     * @param pet The pet from which we want to set the image
     * @param newImage The bitmap of the new image to display
     */
    void updatePetImage(Pet pet, Bitmap newImage);

    /**
     * Delete the given pet.
     * @param myPet The pet  which we want to delete
     */
    void deletePet(Pet myPet) throws UserIsNotOwnerException;

    /**
     * Updates the given pet.
     * @param pet The pet
     */
    void updatePet(Pet pet) throws UserIsNotOwnerException;

    /**
     * Changes to main view.
     */
    void changeToMainView();

    /**
     * Add a weight for a date.
     * @param pet The pet
     * @param newWeight The new weight
     * @param date The date
     */
    void addWeightForDate(Pet pet, double newWeight, String date);

    /**
     * Delete the weight of a date.
     * @param pet The pet
     * @param date The date
     */
    void deleteWeightForDate(Pet pet, String date);

    /**
     * Add a wash frequency for a date.
     * @param pet The pet
     * @param newWashFrequency The new wash frequency
     * @param date The date
     */
    void addWashFrequencyForDate(Pet pet, int newWashFrequency, String date);

    /**
     * Delete the wash frequency for a date.
     * @param pet The pet
     * @param date Teh date
     */
    void deleteWashFrequencyForDate(Pet pet, String date);
}

package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.pesmypetcare.mypetcare.features.pets.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Meals;
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
     * Adds a new meal to the pet.
     * @param pet The pet to which we want to add the meal
     * @param meal The meal that has to be added
     */
    void addPetMeal(Pet pet, Meals meal) throws MealAlreadyExistingException;

    /**
     * Updates the data of a meal of a pet.
     * @param pet The pet to which we want to update the meal
     * @param meal The updated meal
     * @param newDate The new date of the meal
     */
    void updatePetMeal(Pet pet, Meals meal, String newDate, boolean updatesDate);

    /**
     * Deletes a meal from a pet.
     * @param pet The pet from which the meal has to be deleted
     * @param meal The meal that has to be deleted from the pet
     */
    void deletePetMeal(Pet pet, Meals meal);

    /**
     * Obtains all the meals from a pet.
     * @param pet The pet from which we want to obtain all the meals
     */
    void obtainAllPetMeals(Pet pet);
}

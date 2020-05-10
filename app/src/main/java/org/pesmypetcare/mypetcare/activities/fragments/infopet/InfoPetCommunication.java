package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.pesmypetcare.mypetcare.features.pets.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.List;

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
     * @param updatesDate True if the date has to be updated or false otherwise
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


    /**
     * Adds a new medication to the pet.
     * @param pet The pet from which the medication has to be added
     * @param medication The medication that has to be added to the pet
     */
    void addPetMedication(Pet pet, Medication medication) throws MedicationAlreadyExistingException;

    /**
     * Updates the data of a medication of a pet.
     * @param pet The pet from which we want to update the medication
     * @param medication The updated medication
     * @param newDate The new date of the medication
     * @param updatesDate True if the date has to be updated or false otherwise
     * @param newName The new name of the medication
     * @param updatesName True if the name has to be updated or false otherwise
     */
    void updatePetMedication(Pet pet, Medication medication, String newDate, boolean updatesDate, String newName,
                             boolean updatesName);

    /**
     * Deletes a medication from a pet.
     * @param pet The pet from which the medication has to be deleted
     * @param medication The meal that has to be deleted
     */
    void deletePetMedication(Pet pet, Medication medication);

    /**
     * Obtains all the medications from a pet.
     * @param pet The pet from which we want to obtain all the medications
     */
    void obtainAllPetMedications(Pet pet);

    /**
     * Add an exercise to the pet.
     * @param pet The pet
     * @param exerciseName The exercise name
     * @param exerciseDescription The exercise description
     * @param startExerciseDateTime The exercise start DateTime
     * @param endExerciseDateTime The exercise end DateTime
     */
    void addExercise(Pet pet, String exerciseName, String exerciseDescription, DateTime startExerciseDateTime,
                     DateTime endExerciseDateTime);

    /**
     * Remove an exercise to the pet.
     * @param pet The pet
     * @param dateTime The start
     */
    void removeExercise(Pet pet, DateTime dateTime);

    /**
     * Update an exercise of the pet.
     * @param pet The pet
     * @param txtExerciseName The exercise name
     * @param txtDescription The exercise description
     * @param startExerciseDateTime The exercise start DateTime
     * @param endExerciseDateTime The exercise end DateTime
     */
    void updateExercise(Pet pet, String txtExerciseName, String txtDescription, DateTime originalStartDateTime,
                        DateTime startExerciseDateTime, DateTime endExerciseDateTime);

    /**
     * Get the actual user pets.
     * @return The user pets
     */
    List<Pet> getUserPets();

    /**
     * Asks for a permission.
     * @param permission The permission to ask for
     */
    void askForPermission(String permission);

    /**
     * Start the walking of the pets.
     * @param walkingPetNames The names of the pets that go for a walk
     */
    void startWalking(List<String> walkingPetNames);

    /**
     * Check whether the user is walking or not.
     * @return True if the user is walking
     */
    boolean isWalking();

    /**
     * End the current walking.
     * @param name The name of the walking
     * @param description The description of the walking
     */
    void endWalking(String name, String description);

    /**
     * Cancel the current walking.
     */
    void cancelWalking();
}

package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.pesmypetcare.mypetcare.features.pets.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.pets.VetVisit;

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
     * Add a vet visit to the given pet.
     * @param pet The pet to whom the vet visit has to be added
     * @param vetVisit The vet visit to add to the pet
     */
    void addPetVetVisit(Pet pet, VetVisit vetVisit);

    /**
     * Updates a vet visit from a pet.
     * @param pet The pet from which the vet visit has to be updated
     * @param vetVisit The vet visit that has to be updated
     * @param newDate The new date of the vet visit
     * @param updatesDate True if the date has to be updated or false otherwise
     */
    void updatePetVetVisit(Pet pet, VetVisit vetVisit, String newDate, boolean updatesDate);

    /**
     * Deletes a vet visit from a pet.
     * @param pet The pet from which the vet visit has to be deleted
     * @param vetVisit The vet visit that has to be deleted from the given pet
     */
    void deletePetVetVisit(Pet pet, VetVisit vetVisit);
}

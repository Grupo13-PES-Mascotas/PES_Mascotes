package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.pets.events.meals.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.events.meals.Meals;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.illness.Illness;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.vaccination.Vaccination;
import org.pesmypetcare.mypetcare.features.pets.events.medication.Medication;
import org.pesmypetcare.mypetcare.features.pets.events.medication.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.events.vetvisit.VetVisit;
import org.pesmypetcare.mypetcare.features.pets.events.wash.Wash;
import org.pesmypetcare.mypetcare.features.pets.events.wash.WashAlreadyExistingException;

import java.util.List;

/**
 * @author Daniel Clemente
 */
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

    /**
     * Obtains all the vet visits from a pet.
     * @param pet The pet from which we want to obtain all the vet visits
     */
    void obtainAllPetVetVisits(Pet pet);
    
    /**
     * Adds a new wash to the pet.
     * @param pet The pet to which we want to add the meal
     * @param wash The wash that has to be added
     */
    void addPetWash(Pet pet, Wash wash) throws WashAlreadyExistingException;

    /**
     * Updates the data of a wash of a pet.
     * @param pet The pet to which we want to update the wash
     * @param wash The updated wash
     * @param newDate The new date of the wash
     * @param updatesDate True if the date has to be updated or false otherwise
     */
    void updatePetWash(Pet pet, Wash wash, String newDate, boolean updatesDate);

    /**
     * Deletes a wash from a pet.
     * @param pet The pet from which the wash has to be deleted
     * @param wash The wash that has to be deleted from the pet
     */
    void deletePetWash(Pet pet, Wash wash);

    /**
     * Obtains all the wash from a pet.
     * @param pet The pet from which we want to obtain all the washes
     */
    void obtainAllPetWashes(Pet pet);

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
     * @param endExerciseDateTime The exercise end DateTime-
     */
    void updateExercise(Pet pet, String txtExerciseName, String txtDescription, DateTime originalStartDateTime,
                        DateTime startExerciseDateTime, DateTime endExerciseDateTime);

    /**
     * Add a vaccination to the pet.
     * @param pet The pet to whom the vaccination has to be added
     * @param vaccinationDescription The description of the vaccination that has to be added to the pet
     * @param vaccinationDate The date of the vaccination that has to be added to the pet
     */
    void addPetVaccination(Pet pet, String vaccinationDescription, DateTime vaccinationDate);

    /**
     * Updates a vaccination of the pet.
     * @param pet The pet from whom we have to update the vaccination
     * @param vaccination The vaccination with the body updated
     * @param newDate The new date of the vaccination
     * @param updatesDate A boolean that indicates whether the date has to be updated or not
     */
    void updatePetVaccination(Pet pet, Vaccination vaccination, String newDate, boolean updatesDate);

    /**
     * Deletes a vaccination from the given pet.
     * @param pet The pet from where the vaccination has to be deleted
     * @param vaccination The vaccination that has to be deleted
     */
    void deletePetVaccination(Pet pet, Vaccination vaccination);

    /**
     * Obtains all vaccinations from a given pet.
     * @param pet The pet from where we have to obtain all the vaccinations
     */
    void obtainAllPetVaccinations(Pet pet);

    /**
     * Add a pet illness to the pet.
     * @param pet The pet to whom the illness has to be added
     * @param description The description of the illness that has to be added
     * @param type The type of the illness that has to be added
     * @param severity The severity of the illness that has to be added
     * @param startDate The startDate of the illness that has to be added
     * @param endDate The endDate of the illness that has to be added
     */
    void addPetIllness(Pet pet, String description, String type, String severity, DateTime startDate, DateTime endDate);

    /**
     * Updates an illness of the pet.
     * @param pet The pet from whom we have to update the illness
     * @param illness The illness with the body updated
     * @param newDate The new date of the illness
     * @param updatesDate A boolean that indicates whether the date has to be updated or not
     */
    void updatePetIllness(Pet pet, Illness illness, String newDate, boolean updatesDate);

    /**
     * Obtains all illnesses from a given pet.
     * @param pet The pet from where we have to obtain all the illnesses
     */
    void obtainAllPetIllnesses(Pet pet);

    /**
     * Deletes an illness from the given pet.
     * @param pet The pet from where the illness has to be deleted
     * @param illness The illness that has to be deleted
     */
    void deletePetIllness(Pet pet, Illness illness);

    /**
     * Get the actual user pets.
     * @return The user pets
     */
    List<Pet> getUserPets();

    /**
     * Asks for a permission.
     * @param permission The permission to ask for
     */
    void askForPermission(String... permission);

    /**
     * Start the walking of the pets.
     * @param walkingPetNames The names of the pets that go for a walk
     */
    void startWalk(List<String> walkingPetNames);

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
    void endWalk(String name, String description);

    /**
     * Cancel the current walking.
     */
    void cancelWalking();
}

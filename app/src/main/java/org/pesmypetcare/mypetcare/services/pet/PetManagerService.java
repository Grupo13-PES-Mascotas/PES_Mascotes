package org.pesmypetcare.mypetcare.services.pet;

import android.graphics.Bitmap;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.Exercise;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.walk.Walk;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.pet.Weight;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author Albert Pinto
 */
public interface PetManagerService {

    /**
     * Updates the pet.
     * @param pet The pet to update
     */
    void updatePet(Pet pet);

    /**
     * Registers a new pet to a user.
     * @param user The user to whom the pet has to be registered
     * @param pet The pet to be registered to the user
     * @return True if the register has been done without any problems
     * @throws PetAlreadyExistingException The pet already belongs to the user
     */
    boolean registerNewPet(User user, Pet pet) throws ExecutionException, InterruptedException;

    /**
     * Updates the image of the pet.
     * @param user The owner of the pet
     * @param pet The pet from which the image will be updated
     * @param newPetImage The new image for the pet
     */
    void updatePetImage(User user, Pet pet, Bitmap newPetImage);

    /**
     * Delete the pet.
     * @param pet The pet to delete
     * @param user The owner of the pet
     */
    void deletePet(Pet pet, User user);

    /**
     * Delete a user.
     * @param user The user
     */
    void deletePetsFromUser(User user);

    /**
     * Find all the pets from a user.
     * @param user The user who wants to get his pets
     * @return The pets that belongs to the user
     */
    List<Pet> findPetsByOwner(User user) throws PetRepeatException;

    /**
     * Get all the images of the pets from the user.
     * @param user The user which owns the pets
     * @return The images of the pets from the server
     */
    Map<String, byte[]> getAllPetsImages(User user);

    /**
     * Add a event to a pet.
     * @param pet The pet
     * @param event The event
     */
    void registerNewEvent(Pet pet, Event event) throws ExecutionException, InterruptedException;

    /**
     * Delete a event from a pet.
     * @param pet The pet
     * @param event The event
     */
    void deleteEvent(Pet pet, Event event) throws ExecutionException, InterruptedException;

    /**
     * Update the weight for a pet.
     * @param user The user
     * @param pet The pet
     * @param newWeight The new weight
     * @param dateTime The dateTime
     */
    void addWeight(User user, Pet pet, double newWeight, DateTime dateTime) throws ExecutionException,
        InterruptedException;

    /**
     * Delete the weight of a date.
     * @param user The user
     * @param pet The pet
     * @param dateTime The dateTime
     */
    void deletePetWeight(User user, Pet pet, DateTime dateTime) throws ExecutionException, InterruptedException;

    /**
     * Update the wash frequency for a pet.
     * @param user The user
     * @param pet The pet
     * @param newWashFrequency The new wash frequency
     * @param dateTime The date time of the new wash frequency
     */
    void addWashFrequency(User user, Pet pet, int newWashFrequency, DateTime dateTime) throws ExecutionException,
        InterruptedException;

    /**
     * Delete the pet wash frequency of a date.
     * @param user The user
     * @param pet The pet
     * @param dateTime The dateTime
     */
    void deletePetWashFrequency(User user, Pet pet, DateTime dateTime) throws ExecutionException, InterruptedException;

    /**
     * Add an exercise to the pet.
     * @param user The user
     * @param pet The pet
     * @param exercise The exercise
     */
    void addExercise(User user, Pet pet, Exercise exercise) throws ExecutionException, InterruptedException;

    /**
     * Delete an exercise of the pet.
     * @param user The user
     * @param pet The pet
     * @param dateTime The DateTime of the exercise
     */
    void deleteExercise(User user, Pet pet, DateTime dateTime) throws ExecutionException, InterruptedException;

    /**
     * Update the exercise of the pet.
     * @param user The user
     * @param pet The pet
     * @param originalDateTime The original DateTime
     * @param exercise The exercise
     */
    void updateExercise(User user, Pet pet, DateTime originalDateTime, Exercise exercise) throws ExecutionException,
            InterruptedException;

    /**
     * Add the walking to the pet.
     * @param user The user
     * @param pet The pet
     * @param walk The walking
     */
    void addWalking(User user, Pet pet, Walk walk) throws ExecutionException, InterruptedException;

    /**
     * Get all the exercises.
     * @param user The user
     * @param pet The pet
     * @return All the exercises of the pet
     */
    List<Exercise> getAllExercises(User user, Pet pet) throws ExecutionException, InterruptedException;

    /**
     * Get all the weights.
     * @param user The user
     * @param pet The pet
     * @return All the weights
     */
    List<Weight> getAllWeights(User user, Pet pet);

    byte[] getPetImage(User user, Pet pet);
}

package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
    boolean registerNewPet(User user, Pet pet);

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
    void registerNewEvent(Pet pet, Event event);

    /**
     * Delete a event from a pet.
     * @param pet The pet
     * @param event The event
     */
    void deleteEvent(Pet pet, Event event);

    /**
     * Add a periodic notification event to a pet.
     * @param pet The pet which the user want to add the periodic notification
     * @param event The event to add
     * @param user The user who want to add the periodic notification
     * @param period The period
     */
    void registerNewPeriodicNotification(User user, Pet pet, Event event, int period) throws ParseException;

    /**
     * Delete a periodic notification event from a pet
     * @param user The user who want to delete the periodic notification
     * @param pet The pet which the user want to delete the periodic notification
     * @param event The event to delete
     * @throws ParseException
     */
    void deletePeriodicEvent(User user, Pet pet, Event event) throws ParseException;
}

package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;

public interface PetManagerService {

    /**
     * Updates the pet.
     * @param pet The pet to update
     */
    void updatePet(Pet pet);

    /**
     * Registers a new pet to a user.
     * @param username The user to whom the pet has to be registered
     * @param pet The pet to be registered to the user
     * @return True if the register has been done without any problems
     * @throws PetAlreadyExistingException The pet already belongs to the user
     */
    boolean registerNewPet(String username, Pet pet) throws PetAlreadyExistingException;

    /**
     * Updates the image of the pet.
     * @param username The owner of the pet
     * @param petName The pet from which the image will be updated
     * @param newPetImage The new image for the pet
     */
    void updatePetImage(String username, String petName, Bitmap newPetImage);

    /**
     * Delete the pet.
     * @param pet The pet to delete
     * @param username The owner of the pet
     */
    void deletePet(Pet pet, String username);
}

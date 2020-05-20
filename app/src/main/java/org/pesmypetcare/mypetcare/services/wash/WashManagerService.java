package org.pesmypetcare.mypetcare.services.wash;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.wash.Wash;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Enric Hernando
 */
public interface WashManagerService {

    /**
     * Method that creates a new wash and assigns it to the indicated pet of the indicated user.
     * @param user The owner of the pet
     * @param pet The pet to which we want to add the wash
     * @param wash The wash that has to be added to the pet
     */
    void createWash(User user, Pet pet, Wash wash) throws ExecutionException, InterruptedException;

    /**
     * Method that removes a wash from the indicated pet.
     * @param user The owner of the pet
     * @param pet The pet from which we want to delete the wash
     * @param wash The wash that has to be deleted from the pet
     */
    void deleteWash(User user, Pet pet, Wash wash) throws ExecutionException, InterruptedException;

    /**
     * Method that obtains all the washes of the indicated pet.
     * @param user The owner of the pet
     * @param pet The pet from which we want to obtain all the washes
     * @return A list containing all the wash of the pet
     */
    List<Wash> findWashesByPet(User user, Pet pet) throws ExecutionException, InterruptedException;

    /**
     * Method that updates the body of the indicated wash.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the wash
     * @param wash The updated wash
     */
    void updateWashBody(User user, Pet pet, Wash wash) throws ExecutionException, InterruptedException;

    /**
     * Method that updates the date of the indicated wash.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the wash
     * @param newDate The new date of the wash
     * @param oldDate The old date of the wash
     */
    void updateWashDate(User user, Pet pet, String newDate, String oldDate) throws ExecutionException, InterruptedException;

    /**
     * Method that deletes all the washes from the indicated pet.
     * @param user The owner of the pet.
     * @param pet The pet from which we want to delete all the washes
     */
    void deleteWashesFromPet(User user, Pet pet) throws ExecutionException, InterruptedException;
}

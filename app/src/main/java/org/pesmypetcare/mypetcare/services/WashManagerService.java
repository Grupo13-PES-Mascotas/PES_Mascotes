package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Wash;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;

/**
 * @author Enric Hernando
 */
public interface WashManagerService {

    /**
     * Method that creates a new meal and assigns it to the indicated pet of the indicated user.
     * @param user The owner of the pet
     * @param pet The pet to which we want to add the meal
     * @param wash The wash that has to be added to the pet
     */
    void createWash(User user, Pet pet, Wash wash);

    /**
     * Method that removes a wash from the indicated pet.
     * @param user The owner of the pet
     * @param pet The pet from which we want to delete the meal
     * @param wash The wash that has to be deleted from the pet
     */
    void deleteWash(User user, Pet pet, Wash wash);

    /**
     * Method that obtains all the washes of the indicated pet.
     * @param user The owner of the pet
     * @param pet The pet from which we want to obtain all the meals
     * @return A list containing all the wash of the pet
     */
    List<Wash> findWashesByPet(User user, Pet pet);

    /**
     * Method that updates the body of the indicated meal.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the meal
     * @param wash The updated wash
     */
    void updateWashBody(User user, Pet pet, Wash wash);

    /**
     * Method that updates the date of the indicated meal.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the meal
     * @param newDate The new date of the meal
     * @param oldDate The old date of the meal
     */
    void updateWashDate(User user, Pet pet, String newDate, String oldDate);

    /**
     * Method that deletes all the washes from the indicated pet.
     * @param user The owner of the pet.
     * @param pet The pet from which we want to delete all the washes
     */
    void deleteWashesFromPet(User user, Pet pet);
}

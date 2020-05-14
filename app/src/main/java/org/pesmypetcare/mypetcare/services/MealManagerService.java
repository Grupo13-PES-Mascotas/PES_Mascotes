package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface MealManagerService {

    /**
     * Method that creates a new meal and assigns it to the indicated pet of the indicated user.
     * @param user The owner of the pet
     * @param pet The pet to which we want to add the meal
     * @param meal The meal that has to be added to the pet
     */
    void createMeal(User user, Pet pet, Meals meal) throws ExecutionException, InterruptedException;

    /**
     * Method that updates the body of the indicated meal.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the meal
     * @param meal The updated meal
     */
    void updateMealBody(User user, Pet pet, Meals meal) throws ExecutionException, InterruptedException;

    /**
     * Method that updates the date of the indicated meal.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the meal
     * @param newDate The new date of the meal
     * @param oldDate The old date of the meal
     */
    void updateMealKey(User user, Pet pet, String newDate, String oldDate) throws ExecutionException,
            InterruptedException;

    /**
     * Method that removes a meal from the indicated pet.
     * @param user The owner of the pet
     * @param pet The pet from which we want to delete the meal
     * @param meal The meal that has to be deleted from the pet
     */
    void deleteMeal(User user, Pet pet, Meals meal) throws ExecutionException, InterruptedException;

    /**
     * Method that obtains all the meals of the indicated pet.
     * @param user The owner of the pet
     * @param pet The pet from which we want to obtain all the meals
     * @return A list containing all the meal of the pet
     */
    List<Meals> findMealsByPet(User user, Pet pet) throws ExecutionException, InterruptedException;

    /**
     * Method that deletes all the meals from the indicated pet.
     * @param user The owner of the pet.
     * @param pet The pet from which we want to delete all the meals
     */
    void deleteMealsFromPet(User user, Pet pet) throws ExecutionException, InterruptedException;
}

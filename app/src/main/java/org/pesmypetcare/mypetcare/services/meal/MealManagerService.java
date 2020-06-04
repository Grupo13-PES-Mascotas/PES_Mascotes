package org.pesmypetcare.mypetcare.services.meal;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.meals.Meals;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;

/**
 * @author Xavier Campos
 */
public interface MealManagerService {

    /**
     * Method that creates a new meal and assigns it to the indicated pet of the indicated user.
     * @param user The owner of the pet
     * @param pet The pet to which we want to add the meal
     * @param meal The meal that has to be added to the pet
     */
    void createMeal(User user, Pet pet, Meals meal);

    /**
     * Method that updates the body of the indicated meal.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the meal
     * @param meal The updated meal
     */
    void updateMealBody(User user, Pet pet, Meals meal);

    /**
     * Method that updates the date of the indicated meal.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the meal
     * @param newDate The new date of the meal
     * @param oldDate The old date of the meal
     */
    void updateMealKey(User user, Pet pet, String newDate, String oldDate);

    /**
     * Method that removes a meal from the indicated pet.
     * @param user The owner of the pet
     * @param pet The pet from which we want to delete the meal
     * @param meal The meal that has to be deleted from the pet
     */
    void deleteMeal(User user, Pet pet, Meals meal);

    /**
     * Method that obtains all the meals of the indicated pet.
     * @param user The owner of the pet
     * @param pet The pet from which we want to obtain all the meals
     * @return A list containing all the meal of the pet
     */
    List<Meals> findMealsByPet(User user, Pet pet);

    /**
     * Method that deletes all the meals from the indicated pet.
     * @param user The owner of the pet.
     * @param pet The pet from which we want to delete all the meals
     */
    void deleteMealsFromPet(User user, Pet pet);
}

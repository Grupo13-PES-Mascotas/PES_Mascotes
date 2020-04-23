package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MealManagerService;

public class TrDeleteMeal {
    private MealManagerService mealManagerService;
    private User user;
    private Pet pet;
    private Meals meal;

    public TrDeleteMeal(MealManagerService mealManagerService) {
        this.mealManagerService = mealManagerService;
    }

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet from which we want to delete the meal.
     * @param pet The pet from which we want to delete the meal
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the meal that has to be deleted.
     * @param meal The meal that has to be deleted
     */
    public void setMeal(Meals meal) {
        this.meal = meal;
    }

    /**
     * Executes the transaction.
     */
    public void execute() {
        mealManagerService.deleteMeal(user, pet, meal);
        pet.deleteEvent(meal);
    }
}

package org.pesmypetcare.mypetcare.controllers.meals;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.meals.Meals;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.meal.MealManagerService;

/**
 * @author Xavier Campos
 */
public class TrUpdateMeal {
    private MealManagerService mealManagerService;
    private User user;
    private Pet pet;
    private Meals meal;
    private String newDate;
    private boolean updatesDate;

    public TrUpdateMeal(MealManagerService mealManagerService) {
        this.mealManagerService = mealManagerService;
        this.updatesDate = false;
    }

    /**
     * Setter if the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet from which we want to update the meal.
     * @param pet The setter of the pet from which we want to update the meal
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of updated meal.
     * @param meal The updated meal
     */
    public void setMeal(Meals meal) {
        this.meal = meal;
    }

    /**
     * Setter of the new date of the meal.
     * @param newDate The new date of the meal
     */
    public void setNewDate(String newDate) {
        this.updatesDate = true;
        this.newDate = newDate;
    }

    /**
     * Execute the transaction.
     */
    public void execute() {
        mealManagerService.updateMealBody(user, pet, meal);
        if (updatesDate) {
            mealManagerService.updateMealKey(user, pet, newDate, meal.getDateTime().toString());
        }
    }
}

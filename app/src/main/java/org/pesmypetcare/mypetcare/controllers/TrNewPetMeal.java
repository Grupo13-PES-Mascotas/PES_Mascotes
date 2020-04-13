package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MealManagerService;

public class TrNewPetMeal {
    private MealManagerService mealManagerService;
    private User user;
    private Pet pet;
    private Meals meal;
    private Boolean result;

    public TrNewPetMeal(MealManagerService mealManagerService) {
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
     * Setter of the pet to whom the meal will be added.
     * @param pet The pet to whom the meal will be added
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the meal that has to be added to the pet.
     * @param meal The meal that has to be added to the pet
     */
    public void setMeal(Meals meal) {
        this.meal = meal;
    }

    /**
     * Getter of the result of the transaction.
     * @return The result of the transaction
     */
    public boolean getResult() {
        return result;
    }

    /**
     * Execute the transaction.
     */
    public void execute() throws MealAlreadyExistingException {
        result = false;
        if (mealHasAlreadyBeenAdded()) {
            throw new MealAlreadyExistingException();
        }
        pet.addEvent(meal);
        mealManagerService.createMeal(user, pet, meal);
        result = true;
    }

    /**
     * Method responsible for checking if the meal has already been added to the pet.
     * @return True if the meal has already been added or false otherwise
     */
    private boolean mealHasAlreadyBeenAdded() {
        return pet.getMealEvents().contains(meal);
    }


}

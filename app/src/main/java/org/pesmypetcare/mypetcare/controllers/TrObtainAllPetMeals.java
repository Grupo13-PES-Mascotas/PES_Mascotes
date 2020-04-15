package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MealManagerService;

import java.util.List;

public class TrObtainAllPetMeals {
    private MealManagerService mealManagerService;
    private User user;
    private Pet pet;
    private List<Meals> result;

    public TrObtainAllPetMeals(MealManagerService mealManagerService) {
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
     * Setter of the pet from which we want to obtain all the meals.
     * @param pet The pet from which we want to obtain all the meals
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Getter of the meals of the pet.
     * @return The meals of the pet
     */
    public List<Meals> getResult() {
        return result;
    }

    /**
     * Method responsible for executing the transaction.
     */
    public void execute() {
        result = mealManagerService.findMealsByPet(user, pet);
        for (Event e:result) {
            pet.addEvent(e);
        }
    }
}

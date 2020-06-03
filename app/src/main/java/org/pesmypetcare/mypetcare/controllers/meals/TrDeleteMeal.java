package org.pesmypetcare.mypetcare.controllers.meals;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.meals.Meals;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.googlecalendar.GoogleCalendarService;
import org.pesmypetcare.mypetcare.services.meal.MealManagerService;

/**
 * @author Xavier Campos
 */
public class TrDeleteMeal {
    private MealManagerService mealManagerService;
    private GoogleCalendarService googleCalendarService;
    private User user;
    private Pet pet;
    private Meals meal;

    public TrDeleteMeal(MealManagerService mealManagerService, GoogleCalendarService googleCalendarService) {
        this.mealManagerService = mealManagerService;
        this.googleCalendarService = googleCalendarService;
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
     * @throws NotPetOwnerException The user is not the owner of the pet
     */
    public void execute() throws NotPetOwnerException {
        if (!user.getUsername().equals(pet.getOwner().getUsername())) {
            throw new NotPetOwnerException();
        }
        mealManagerService.deleteMeal(user, pet, meal);
        googleCalendarService.deleteEvent(pet, meal);
        pet.deleteEvent(meal);
    }
}

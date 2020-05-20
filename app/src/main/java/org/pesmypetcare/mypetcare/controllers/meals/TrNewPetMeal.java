package org.pesmypetcare.mypetcare.controllers.meals;

import org.pesmypetcare.httptools.exceptions.InvalidFormatException;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.pets.events.meals.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.events.meals.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.googlecalendar.GoogleCalendarService;
import org.pesmypetcare.mypetcare.services.meal.MealManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos
 */
public class TrNewPetMeal {
    private MealManagerService mealManagerService;
    private GoogleCalendarService googleCalendarService;
    private User user;
    private Pet pet;
    private Meals meal;
    private Boolean result;

    public TrNewPetMeal(MealManagerService mealManagerService, GoogleCalendarService googleCalendarService) {
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
    public boolean isResult() {
        return result;
    }

    /**
     * Execute the transaction.
     */
    public void execute() throws MealAlreadyExistingException, InterruptedException, ExecutionException,
        InvalidFormatException {
        result = false;

        if (mealHasAlreadyBeenAdded()) {
            throw new MealAlreadyExistingException();
        }
        pet.addEvent(meal);
        mealManagerService.createMeal(user, pet, meal);
        googleCalendarService.registerNewEvent(pet, meal);
        result = true;
    }

    /**
     * Method responsible for checking if the meal has already been added to the pet.
     * @return True if the meal has already been added or false otherwise
     */
    private boolean mealHasAlreadyBeenAdded() {
        for (Event e : pet.getMealEvents()) {
            Meals m = (Meals) e;
            if (m.getDateTime().compareTo(meal.getMealDate()) == 0) {
                return true;
            }
        }
        return false;
    }
}

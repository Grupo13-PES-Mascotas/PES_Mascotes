package org.pesmypetcare.mypetcare.controllers.meals;

import org.pesmypetcare.mypetcare.services.googlecalendar.GoogleCalendarAdapter;
import org.pesmypetcare.mypetcare.services.meal.MealManagerAdapter;

/**
 * @author Albert Pinto
 */
public class MealsControllersFactory {
    private MealsControllersFactory() {
        // Private constructor
    }

    /**
     * Create the transaction for adding a new pet meal.
     * @return The transaction for adding a new pet meal
     */
    public static TrNewPetMeal createTrNewPetMeal() {
        return new TrNewPetMeal(new MealManagerAdapter(), new GoogleCalendarAdapter());
    }

    /**
     * Create the transaction for obtaining all pet meals.
     * @return The transaction for obtaining all pet meals
     */
    public static TrObtainAllPetMeals createTrObtainAllPetMeals() {
        return new TrObtainAllPetMeals(new MealManagerAdapter());
    }

    /**
     * Create the transaction for deleting a meal.
     * @return The transaction for deleting a meal
     */
    public static TrDeleteMeal createTrDeleteMeal() {
        return new TrDeleteMeal(new MealManagerAdapter(), new GoogleCalendarAdapter());
    }

    /**
     * Create the transaction for updating a meal.
     * @return The transaction for updating a meal
     */
    public static TrUpdateMeal createTrUpdateMeal() {
        return new TrUpdateMeal(new MealManagerAdapter());
    }
}

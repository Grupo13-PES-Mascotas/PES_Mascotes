package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MealManagerService;

public class TrUpdateMeal {
    private MealManagerService mealManagerService;
    private User user;
    private Pet pet;
    private Meals meal;
    private String newDate;
    private boolean updatesDate;

    public TrUpdateMeal (MealManagerService mealManagerService) {
        this.mealManagerService = mealManagerService;
        this.updatesDate = false;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setMeal(Meals meal) {
        this.meal = meal;
    }

    public void setNewDate(String newDate) {
        this.updatesDate = true;
        this.newDate = newDate;
    }

    public void execute() {
        mealManagerService.updateMealBody(user, pet, meal);
        if (updatesDate) {
            mealManagerService.updateMealDate(user, pet, newDate, meal.getDateTime());
        }
    }
}

package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.meals.TrDeleteMeal;
import org.pesmypetcare.mypetcare.controllers.meals.TrNewPetMeal;
import org.pesmypetcare.mypetcare.features.pets.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMealManagerService;
import org.pesmypetcare.usermanagerliblib.datacontainers.DateTime;

import static org.junit.Assert.assertEquals;

public class TestTrDeleteMeal {
    private User user;
    private Pet linux;
    private Meals originalMeal;
    private Meals secondMeal;
    private TrNewPetMeal trNewPetMeal;
    private TrDeleteMeal trDeleteMeal;
    private StubMealManagerService stubMealManagerService;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
        linux = new Pet("Linux");
        originalMeal = getTestMeal();
        secondMeal = getTestMeal2();
        stubMealManagerService = new StubMealManagerService();
        trNewPetMeal = new TrNewPetMeal(stubMealManagerService);
        trDeleteMeal = new TrDeleteMeal(stubMealManagerService);
    }

    @Test
    public void shouldDeleteMeal() throws MealAlreadyExistingException {
        trNewPetMeal.setUser(user);
        trNewPetMeal.setPet(linux);
        trNewPetMeal.setMeal(originalMeal);
        trNewPetMeal.execute();
        trDeleteMeal.setUser(user);
        trDeleteMeal.setPet(linux);
        trDeleteMeal.setMeal(originalMeal);
        trDeleteMeal.execute();
        assertEquals("Should be equal", 0, StubMealManagerService.nMeals);
    }

    @Test
    public void shouldDeleteAllMeals() throws MealAlreadyExistingException {
        trNewPetMeal.setUser(user);
        trNewPetMeal.setPet(linux);
        trNewPetMeal.setMeal(originalMeal);
        trNewPetMeal.execute();
        trNewPetMeal.setMeal(secondMeal);
        trNewPetMeal.execute();
        stubMealManagerService.deleteMealsFromPet(user, linux);
        assertEquals("Should be equal", 0, StubMealManagerService.nMeals);
    }

    private Meals getTestMeal() {
        DateTime date = null;
        try {
            date = DateTime.Builder.build(2011, 2, 26, 15, 23, 56);
        } catch (org.pesmypetcare.usermanagerliblib.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }
        assert date != null;
        return new Meals(date, 52d, "Linux meal");
    }

    private Meals getTestMeal2() {
        DateTime date = null;
        try {
            date = DateTime.Builder.build(2001, 2, 26, 15, 23, 56);
        } catch (org.pesmypetcare.usermanagerliblib.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }
        assert date != null;
        return new Meals(date, 21d, "Linux diner");
    }
}

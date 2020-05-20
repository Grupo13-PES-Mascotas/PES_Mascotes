package org.pesmypetcare.mypetcare.controllers.meals;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.exceptions.InvalidFormatException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.meals.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.events.meals.Meals;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubGoogleCalendarService;
import org.pesmypetcare.mypetcare.services.StubMealManagerService;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrDeleteMeal {
    private User user;
    private Pet linux;
    private Meals originalMeal;
    private Meals secondMeal;
    private TrNewPetMeal trNewPetMeal;
    private TrDeleteMeal trDeleteMeal;
    private StubMealManagerService stubMealManagerService;
    private StubGoogleCalendarService stubGoogleCalendarService;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
        linux = new Pet("Linux");
        linux.setOwner(user);
        originalMeal = getTestMeal();
        secondMeal = getTestMeal2();
        stubMealManagerService = new StubMealManagerService();
        stubGoogleCalendarService = new StubGoogleCalendarService();
        trNewPetMeal = new TrNewPetMeal(stubMealManagerService, stubGoogleCalendarService);
        trDeleteMeal = new TrDeleteMeal(stubMealManagerService, stubGoogleCalendarService);
    }

    @Test
    public void shouldDeleteMeal() throws MealAlreadyExistingException, InterruptedException, ExecutionException,
        InvalidFormatException, NotPetOwnerException {
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
    public void shouldDeleteAllMeals() throws MealAlreadyExistingException, InterruptedException, ExecutionException,
        InvalidFormatException {
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
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        return new Meals(date, 52d, "Linux meal");
    }

    private Meals getTestMeal2() {
        DateTime date = null;
        try {
            date = DateTime.Builder.build(2001, 2, 26, 15, 23, 56);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        return new Meals(date, 21d, "Linux diner");
    }
}

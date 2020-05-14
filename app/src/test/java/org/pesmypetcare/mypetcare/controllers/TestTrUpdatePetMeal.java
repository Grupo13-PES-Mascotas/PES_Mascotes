package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.meals.TrNewPetMeal;
import org.pesmypetcare.mypetcare.controllers.meals.TrUpdateMeal;
import org.pesmypetcare.mypetcare.features.pets.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubGoogleCalendarService;
import org.pesmypetcare.mypetcare.services.StubMealManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class TestTrUpdatePetMeal {
    private User user;
    private Pet linux;
    private Meals originalMeal;
    private TrUpdateMeal trUpdateMeal;
    private TrNewPetMeal trNewPetMeal;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
        linux = new Pet("Linux");
        originalMeal = getTestMeal();
        trUpdateMeal = new TrUpdateMeal(new StubMealManagerService());
        trNewPetMeal = new TrNewPetMeal(new StubMealManagerService(), new StubGoogleCalendarService());
    }

    @Test
    public void shouldUpdateMealBody() throws MealAlreadyExistingException, InterruptedException, ExecutionException,
        InvalidFormatException {
        trNewPetMeal.setUser(user);
        trNewPetMeal.setPet(linux);
        trNewPetMeal.setMeal(originalMeal);
        trNewPetMeal.execute();
        originalMeal.setKcal(21d);
        originalMeal.setMealName("Linux diner");
        trUpdateMeal.setUser(user);
        trUpdateMeal.setPet(linux);
        trUpdateMeal.setMeal(originalMeal);
        trUpdateMeal.execute();
        assertEquals("Should be equal", StubMealManagerService.currentMeal, originalMeal);
    }

    @Test
    public void shouldUpdateMealDate() throws MealAlreadyExistingException,
        org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException, ExecutionException, InterruptedException {
        trNewPetMeal.setUser(user);
        trNewPetMeal.setPet(linux);
        trNewPetMeal.setMeal(originalMeal);
        trNewPetMeal.execute();
        originalMeal.setMealDate(DateTime.Builder.build(2011, 5, 27, 11, 15, 1));
        trUpdateMeal.setUser(user);
        trUpdateMeal.setPet(linux);
        trUpdateMeal.setMeal(originalMeal);
        trUpdateMeal.execute();
        assertEquals("Should be equal", StubMealManagerService.currentMeal, originalMeal);
    }

    private Meals getTestMeal() {
        DateTime date = null;
        try {
            date = DateTime.Builder.build(2020, 2, 26, 15, 23, 56);
        } catch (org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }
        assert date != null;
        return new Meals(date, 52d, "Linux meal");
    }
}

package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.washes.TrDeleteWash;
import org.pesmypetcare.mypetcare.controllers.washes.TrNewPetWash;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Wash;
import org.pesmypetcare.mypetcare.features.pets.WashAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubGoogleCalendarService;
import org.pesmypetcare.mypetcare.services.StubMealManagerService;
import org.pesmypetcare.mypetcare.services.StubWashManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Enric Hernando
 */
public class TestTrDeleteWash {
    private User user;
    private Pet linux;
    private Wash originalWash;
    private Wash secondWash;
    private TrNewPetWash trNewPetWash;
    private TrDeleteWash trDeleteWash;
    private StubWashManagerService stubWashManagerService;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
        linux = new Pet("Linux");
        originalWash = getTestWash();
        secondWash = getTestWash2();
        stubWashManagerService = new StubWashManagerService();
        trNewPetWash = new TrNewPetWash(stubWashManagerService, new StubGoogleCalendarService());
        trDeleteWash = new TrDeleteWash(stubWashManagerService, new StubGoogleCalendarService());
    }

    @Test
    public void shouldDeleteWash() throws WashAlreadyExistingException, InterruptedException, ExecutionException,
        InvalidFormatException {
        trNewPetWash.setUser(user);
        trNewPetWash.setPet(linux);
        trNewPetWash.setWash(originalWash);
        trNewPetWash.execute();
        trDeleteWash.setUser(user);
        trDeleteWash.setPet(linux);
        trDeleteWash.setWash(originalWash);
        trDeleteWash.execute();
        assertEquals("Should be equal", 0, StubWashManagerService.nWash);
    }

    @Test
    public void shouldDeleteAllWashes() throws WashAlreadyExistingException, InterruptedException, ExecutionException,
        InvalidFormatException {
        trNewPetWash.setUser(user);
        trNewPetWash.setPet(linux);
        trNewPetWash.setWash(originalWash);
        trNewPetWash.execute();
        trNewPetWash.setWash(secondWash);
        trNewPetWash.execute();
        stubWashManagerService.deleteWashesFromPet(user, linux);
        assertEquals("Should be equal", 0, StubMealManagerService.nMeals);
    }

    private Wash getTestWash() {
        DateTime date = null;
        try {
            date = DateTime.Builder.build(2011, 2, 26, 15, 23, 56);
        } catch (org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }
        assert date != null;
        return new Wash(date, 52, "Linux Wash");
    }

    private Wash getTestWash2() {
        DateTime date = null;
        try {
            date = DateTime.Builder.build(2001, 2, 26, 15, 23, 56);
        } catch (org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }
        assert date != null;
        return new Wash(date, 21, "Linux Wash2");
    }
}

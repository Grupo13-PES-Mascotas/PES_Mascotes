package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.washes.TrNewPetWash;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Wash;
import org.pesmypetcare.mypetcare.features.pets.WashAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubGoogleCalendarService;
import org.pesmypetcare.mypetcare.services.StubWashManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

/**
 * @author Enric Hernando
 */
public class TestTrNewPetWash {
    private static final int YEAR = 2020;
    private static final int DAY = 26;
    private static final int HOUR = 15;
    private static final int MINUTES = 23;
    private static final int SECONDS = 56;
    private User user;
    private Pet linux;
    private TrNewPetWash trNewPetWash;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
        linux = new Pet("Linux");
        trNewPetWash = new TrNewPetWash(new StubWashManagerService(), new StubGoogleCalendarService());
    }

    @Test
    public void shouldAddOneWash() throws WashAlreadyExistingException, InterruptedException, ExecutionException,
        InvalidFormatException {
        Wash wash = getTestWash();
        trNewPetWash.setUser(user);
        trNewPetWash.setPet(linux);
        trNewPetWash.setWash(wash);
        trNewPetWash.execute();
        boolean result = trNewPetWash.isResult();
        assertTrue("should communicate with service to add a wash", result);
    }

    @Test(expected = WashAlreadyExistingException.class)
    public void shouldNotAddIfExisting() throws WashAlreadyExistingException, InterruptedException,
        ExecutionException, InvalidFormatException {
        Wash wash = getTestWash();
        trNewPetWash.setUser(user);
        trNewPetWash.setPet(linux);
        trNewPetWash.setWash(wash);
        trNewPetWash.execute();
        trNewPetWash.execute();
    }

    private Wash getTestWash() {
        DateTime date = null;
        try {
            date = DateTime.Builder.build(YEAR, 2, DAY, HOUR, MINUTES, SECONDS);
        } catch (org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }
        assert date != null;
        return new Wash(date, MINUTES, "Linux wash");
    }
}

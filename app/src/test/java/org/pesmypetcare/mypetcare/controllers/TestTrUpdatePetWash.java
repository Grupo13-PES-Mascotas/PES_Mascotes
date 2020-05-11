package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.washes.TrNewPetWash;
import org.pesmypetcare.mypetcare.controllers.washes.TrUpdateWash;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Wash;
import org.pesmypetcare.mypetcare.features.pets.WashAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubWashManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author Enric Hernando
 */
public class TestTrUpdatePetWash {

    private User user;
    private Pet linux;
    private Wash originalWash;
    private TrUpdateWash trUpdateWash;
    private TrNewPetWash trNewPetWash;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
        linux = new Pet("Linux");
        originalWash = getTestWash();
        trUpdateWash = new TrUpdateWash(new StubWashManagerService());
        trNewPetWash = new TrNewPetWash(new StubWashManagerService());
    }

    @Test
    public void shouldUpdateWashBody() throws WashAlreadyExistingException {
        trNewPetWash.setUser(user);
        trNewPetWash.setPet(linux);
        trNewPetWash.setWash(originalWash);
        trNewPetWash.execute();
        originalWash.setDuration(21);
        originalWash.setWashDescription("Linux wash");
        trUpdateWash.setUser(user);
        trUpdateWash.setPet(linux);
        trUpdateWash.setWash(originalWash);
        trUpdateWash.execute();
        assertEquals("Should be equal", StubWashManagerService.currentWash, originalWash);
    }

    @Test
    public void shouldUpdateWashDate() throws WashAlreadyExistingException,
            org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException {
        trNewPetWash.setUser(user);
        trNewPetWash.setPet(linux);
        trNewPetWash.setWash(originalWash);
        trNewPetWash.execute();
        originalWash.setWashDate(DateTime.Builder.build(2011, 5, 27, 11, 15, 1));
        trUpdateWash.setUser(user);
        trUpdateWash.setPet(linux);
        trUpdateWash.setWash(originalWash);
        trUpdateWash.execute();
        assertEquals("Should be equal", StubWashManagerService.currentWash, originalWash);
    }

    private Wash getTestWash() {
        DateTime date = null;
        try {
            date = DateTime.Builder.build(2020, 2, 26, 15, 23, 56);
        } catch (org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }
        assert date != null;
        return new Wash(date, 52, "Linux Wash");
    }
}

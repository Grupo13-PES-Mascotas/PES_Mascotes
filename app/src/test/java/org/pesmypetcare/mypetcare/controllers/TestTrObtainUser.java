package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertNotNull;

public class TestTrObtainUser {
    private TrObtainUser trObtainUser;

    @Before
    public void setUp() {
        trObtainUser = new TrObtainUser(new StubUserManagerService(), new StubPetManagerService());
    }

    @Test
    public void shouldObtainUser() throws PetRepeatException {
        trObtainUser.setUsername("johnDoe");
        trObtainUser.execute();
        User user = trObtainUser.getResult();
        assertNotNull("should communicate with service to obtain a user", user);
    }
}

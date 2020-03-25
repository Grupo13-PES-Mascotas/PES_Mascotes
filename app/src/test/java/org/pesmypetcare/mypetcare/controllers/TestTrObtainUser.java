package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.UserNotExistingException;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertNotNull;

public class TestTrObtainUser {
    private TrObtainUser trObtainUser;
    private final String MIKE = "Mike";

    @Before
    public void setUp() {
        trObtainUser = new TrObtainUser(new StubUserManagerService(), new StubPetManagerService());
    }

    @Test
    public void shouldObtainUser() throws UserNotExistingException {
        trObtainUser.setUsername("johnDoe");
        trObtainUser.execute();
        User user = trObtainUser.getResult();
        assertNotNull("should communicate with service to obtain a user", user);
    }

    @Test(expected = UserNotExistingException.class)
    public void shouldNotObtainUserIfNotExisting() throws UserNotExistingException {
        trObtainUser.setUsername(MIKE);
        trObtainUser.execute();
    }
}

package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestTrExistsUsername {
    private User user;
    private TrExistsUsername trExistsUsername;
    private final String USERNAME = "enric";

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "ndekdme");
        trExistsUsername = new TrExistsUsername(new StubUserManagerService());
    }

    @Test
    public void shouldReturnFalse() {
        trExistsUsername.setNewUsername(USERNAME);
        trExistsUsername.execute();
        boolean addingResult = trExistsUsername.isResult();
        assertFalse("should communicate with service to verify the username", addingResult);
    }

    @Test
    public void shouldReturnTrue() {
        trExistsUsername.setNewUsername("johnDoe");
        trExistsUsername.execute();
        boolean addingResult = trExistsUsername.isResult();
        assertTrue("should communicate with service to verify the username", addingResult);
    }
}

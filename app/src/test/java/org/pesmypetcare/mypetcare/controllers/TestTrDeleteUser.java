package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.users.NotValidUserException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMealManagerService;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertTrue;

public class TestTrDeleteUser {
    private User user;
    private TrDeleteUser trDeleteUser;
    private final String PASSWORD = "12En)(";

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", PASSWORD);
        trDeleteUser = new TrDeleteUser(new StubUserManagerService(), new StubPetManagerService(), new StubMealManagerService());
    }

    @Test
    public void shouldDeleteUser() throws NotValidUserException {
        trDeleteUser.setUser(this.user);
        trDeleteUser.execute();
        boolean addingResult = trDeleteUser.isResult();
        assertTrue("should communicate with service to delete the user", addingResult);
    }

    @Test (expected = NotValidUserException.class)
    public void shouldNotDeleteIfNotExists() throws NotValidUserException {
        trDeleteUser.setUser(new User("Manuel", "manuel@gmail.com", "123"));
        trDeleteUser.execute();
    }
}

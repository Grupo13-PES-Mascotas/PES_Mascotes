package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.user.TrExistsUsername;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestTrExistsUsername {
    private TrExistsUsername trExistsUsername;
    private final String USERNAME = "johnDoe";

    @Before
    public void setUp() {
        trExistsUsername = new TrExistsUsername(new StubUserManagerService());
    }

    @Test
    public void shouldReturnFalse() {
        trExistsUsername.setNewUsername("enric");
        trExistsUsername.execute();
        boolean addingResult = trExistsUsername.isResult();
        assertFalse("should communicate with service to verify the username is not repeated",
                addingResult);
    }

    @Test
    public void shouldReturnTrue() {
        trExistsUsername.setNewUsername(USERNAME);
        trExistsUsername.execute();
        boolean addingResult = trExistsUsername.isResult();
        assertTrue("should communicate with service to verify the username is repeated", addingResult);
    }
}

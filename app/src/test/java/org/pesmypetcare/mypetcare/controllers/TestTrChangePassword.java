package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.user.TrChangePassword;
import org.pesmypetcare.mypetcare.features.users.SamePasswordException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertTrue;

public class TestTrChangePassword {
    private User user;
    private TrChangePassword trChangePassword;
    private final String PASSWORD = "12En)(";

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", PASSWORD);
        trChangePassword = new TrChangePassword(new StubUserManagerService());
    }

    @Test
    public void shouldChangeUserPassword() throws SamePasswordException {
        trChangePassword.setUser(this.user);
        trChangePassword.setNewPassword("Ab12!@");
        trChangePassword.execute();
        boolean addingResult = trChangePassword.isResult();
        assertTrue("should communicate with service to change the password", addingResult);
    }

    @Test(expected = SamePasswordException.class)
    public void shouldNotChangePasswordIfIsTheCurrent() throws SamePasswordException {
        trChangePassword.setUser(this.user);
        trChangePassword.setNewPassword(PASSWORD);
    }
}

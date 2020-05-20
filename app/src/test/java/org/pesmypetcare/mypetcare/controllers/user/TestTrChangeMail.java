package org.pesmypetcare.mypetcare.controllers.user;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertEquals;

/**
 * @author Daniel Clemente
 */
public class TestTrChangeMail {
    private User user;
    private TrChangeMail trChangeMail;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "123456");
        trChangeMail = new TrChangeMail(new StubUserManagerService());
    }

    @Test
    public void shouldChangeMailCorrect() {
        String newMail = "hola@gmail.com";
        trChangeMail.setUser(user);
        trChangeMail.setMail(newMail);
        trChangeMail.execute();
        assertEquals("Should change mail", user.getEmail(), newMail);
    }
}

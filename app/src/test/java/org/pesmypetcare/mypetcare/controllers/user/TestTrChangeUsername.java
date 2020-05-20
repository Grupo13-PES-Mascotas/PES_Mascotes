package org.pesmypetcare.mypetcare.controllers.user;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.user.TrChangeUsername;
import org.pesmypetcare.mypetcare.features.users.SameUsernameException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertTrue;

/**
 * @author Enric Hernando
 */
public class TestTrChangeUsername {
    private User user;
    private TrChangeUsername trChangeUsername;
    private final String USERNAME = "johnDoe";

    @Before
    public void setUp() {
        user = new User(USERNAME, "johndoe@gmail.com", "12En)(");
        trChangeUsername = new TrChangeUsername(new StubUserManagerService());
    }

    @Test
    public void shouldChangeUserUsername() throws SameUsernameException {
        trChangeUsername.setUser(this.user);
        trChangeUsername.setNewUsername("michael");
        trChangeUsername.execute();
        boolean addingResult = trChangeUsername.isResult();
        assertTrue("should communicate with service to change the username", addingResult);
    }

    @Test(expected = SameUsernameException.class)
    public void shouldNotChangeUsernameIfIsTheCurrent() throws SameUsernameException {
        trChangeUsername.setUser(this.user);
        trChangeUsername.setNewUsername(USERNAME);
    }
}

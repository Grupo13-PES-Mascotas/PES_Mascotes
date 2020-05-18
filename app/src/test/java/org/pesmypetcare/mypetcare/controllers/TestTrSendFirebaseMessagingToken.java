package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.user.EmptyMessagingTokenException;
import org.pesmypetcare.mypetcare.controllers.user.TrSendFirebaseMessagingToken;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertTrue;

/**
 * @author Albert Pinto
 */
public class TestTrSendFirebaseMessagingToken {
    private User user;
    private TrSendFirebaseMessagingToken trSendFirebaseMessagingToken;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        trSendFirebaseMessagingToken = new TrSendFirebaseMessagingToken(new StubUserManagerService());
    }

    @Test(expected = EmptyMessagingTokenException.class)
    public void shouldNotTheTokenBeEmpty() throws EmptyMessagingTokenException {
        trSendFirebaseMessagingToken.setUser(user);
        trSendFirebaseMessagingToken.setToken("");
        trSendFirebaseMessagingToken.execute();
    }

    @Test
    public void shouldSentTheMessagingTokenToTheServer() throws EmptyMessagingTokenException {
        trSendFirebaseMessagingToken.setUser(user);
        trSendFirebaseMessagingToken.setToken("5678");
        trSendFirebaseMessagingToken.execute();

        assertTrue("Should add the token", true);
    }
}

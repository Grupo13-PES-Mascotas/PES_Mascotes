package org.pesmypetcare.mypetcare.controllers.user;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.user.UserManagerService;

/**
 * @author Albert Pinto
 */
public class TrSendFirebaseMessagingToken {
    private UserManagerService userManagerService;
    private User user;
    private String token;

    public TrSendFirebaseMessagingToken(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    /**
     * Set the user.
     * @param user The user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the token.
     * @param token The token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Execute the transaction.
     * @throws EmptyMessagingTokenException The messaging token is empty
     */
    public void execute() throws EmptyMessagingTokenException {
        if ("".equals(token)) {
            throw new EmptyMessagingTokenException();
        }

        userManagerService.sendFirebaseMessagingToken(user, token);
    }
}

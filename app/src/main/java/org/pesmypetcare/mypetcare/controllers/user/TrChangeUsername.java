package org.pesmypetcare.mypetcare.controllers.user;

import org.pesmypetcare.mypetcare.features.users.SameUsernameException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.UserManagerService;

public class TrChangeUsername {
    private UserManagerService userManagerService;
    private User user;
    private String newUsername;
    private boolean result;

    public TrChangeUsername(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    /**
     * Set the user that will change the username.
     * @param user The user of the change
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the new username.
     * @param newUsername The new username
     * @throws SameUsernameException The user has already the same username
     */
    public void setNewUsername(String newUsername) throws SameUsernameException {
        if (newUsername.equals(this.user.getUsername())) {
            throw new SameUsernameException();
        }
        this.newUsername = newUsername;
    }

    /**
     * Execute the transaction.
     */
    public void execute() {
        result = false;
        userManagerService.changeUsername(user, newUsername);
        result = true;
    }

    /**
     * Get the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return this.result;
    }
}

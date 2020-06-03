package org.pesmypetcare.mypetcare.controllers.user;

import org.pesmypetcare.mypetcare.services.user.UserManagerService;

/**
 * @author Enric Hernando
 */
public class TrExistsUsername {
    private UserManagerService userManagerService;
    private String newUsername;
    private boolean result;

    public TrExistsUsername(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    /**
     * Set the new username.
     * @param newUsername The new password
     */
    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    /**
     * Execute the transaction.
     */
    public void execute() {
        result = userManagerService.usernameExists(newUsername);
    }

    /**
     * Get the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return this.result;
    }
}

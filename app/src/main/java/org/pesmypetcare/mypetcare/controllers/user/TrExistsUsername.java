package org.pesmypetcare.mypetcare.controllers.user;

import org.pesmypetcare.mypetcare.services.user.UserManagerService;

import java.util.concurrent.ExecutionException;

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
        try {
            result = userManagerService.usernameExists(newUsername);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return this.result;
    }
}

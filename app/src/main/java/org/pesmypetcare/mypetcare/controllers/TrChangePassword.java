package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.users.SamePasswordException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.UserManagerService;

public class TrChangePassword {
    private UserManagerService userManagerService;
    private User user;
    private String newPassword;
    private boolean result;

    public TrChangePassword(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    /**
     * Set the user that will change the password.
     * @param user The user of the change
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the new password.
     * @param newPassword The new password
     * @throws SamePasswordException The user has already the same password
     */
    public void setNewPassword(String newPassword) throws SamePasswordException {
        if (newPassword.equals(this.user.getPasswd())) {
            throw new SamePasswordException();
        }
        this.newPassword = newPassword;
    }

    /**
     * Execute the transaction.
     */
    public void execute() {
        result = false;
        userManagerService.changePassword(this.user, this.newPassword);
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

package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.users.UserAlreadyExistingException;
import org.pesmypetcare.mypetcare.services.UserManagerService;

public class TrRegisterNewUser {
    private UserManagerService userManagerService;
    private String username;
    private String email;
    private String password;
    private boolean result;

    public TrRegisterNewUser(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    /**
     * Set the username of the user that will be registered.
     * @param username The name of the user that wants to be registered
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the email of the user that will be registered.
     * @param email The email of the user that wants to be registered
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the password of the user that will be registered.
     * @param password The password of the user that wants to be registered
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Execute the transaction.
     * @throws UserAlreadyExistingException The pet has already been registered by the user
     */
    public void execute() throws UserAlreadyExistingException {
        result = false;
        if (userHasAlreadyBeenRegistered()) {
            throw new UserAlreadyExistingException();
        }
        userManagerService.registerNewUser(this.username, this.email, this.password);
        result = true;
    }

    /**
     * Checks if the user had been registered.
     * @return True if the user already exists, false otherwise
     */
    private boolean userHasAlreadyBeenRegistered() {
        return userManagerService.userExists(this.username);
    }

    /**
     * Get the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return this.result;
    }
}

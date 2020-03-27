package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.users.NotValidUserException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.mypetcare.services.UserManagerService;

public class TrDeleteUser {
    private UserManagerService userManagerService;
    private PetManagerService petManagerService;
    private User user;
    private Boolean result;

    public TrDeleteUser(UserManagerService userManagerService, PetManagerService petManagerService) {
        this.userManagerService = userManagerService;
        this.petManagerService = petManagerService;
    }

    /**
     * Set the user to be deleted.
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Execute the transaction.
     * @throws NotValidUserException The user doesn't exist
     */
    public void execute() throws NotValidUserException {
        result = false;
        if (!userHasAlreadyBeenRegistered()) {
            throw new NotValidUserException();
        }
        userManagerService.deleteUser(this.user);
        petManagerService.deletePetsFromUser(this.user);
        result = true;
    }

    /**
     * Checks whether the user is registered.
     * @return True if the user is registered
     */
    private boolean userHasAlreadyBeenRegistered() {
        return userManagerService.userExists(this.user.getUsername());
    }

    /**
     * Get the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return this.result;
    }
}

package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.UserNotExistingException;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.mypetcare.services.UserManagerService;

public class TrObtainUser {
    private UserManagerService userManagerService;
    private PetManagerService petManagerService;
    private String username;
    private User result;

    public TrObtainUser(UserManagerService userManagerService, PetManagerService petManagerService) {
        this.userManagerService = userManagerService;
        this.petManagerService = petManagerService;
    }

    /**
     * Set the username of the user that will be registered.
     * @param username The name of the user that wants to be registered
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Execute the transaction.
     * @throws UserNotExistingException The pet has already been registered by the user
     */
    public void execute() throws UserNotExistingException {
        if (!userHasAlreadyBeenRegistered()) {
            throw new UserNotExistingException();
        }

        result = userManagerService.findUserByUsername(username);
        result.setPets(petManagerService.findPetsByOwner(username));
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
    public User getResult() {
        return result;
    }
}

package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.UserNotExistingException;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.mypetcare.services.UserManagerService;

import java.util.ArrayList;

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
     * @throws PetRepeatException The user has already this pet registered.
     */
    public void execute() throws PetRepeatException {
        result = userManagerService.findUserByUsername(username);
        result.setPets((ArrayList<Pet>) petManagerService.findPetsByOwner(username));
    }

    /**
     * Get the result of the transaction.
     * @return The result of the transaction
     */
    public User getResult() {
        return result;
    }
}

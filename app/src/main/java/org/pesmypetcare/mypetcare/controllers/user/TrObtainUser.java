package org.pesmypetcare.mypetcare.controllers.user;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.pet.PetManagerService;
import org.pesmypetcare.mypetcare.services.user.UserManagerService;

import java.util.ArrayList;

/**
 * @author Enric Hernando
 */
public class TrObtainUser {
    private UserManagerService userManagerService;
    private PetManagerService petManagerService;
    private String uid;
    private String token;
    private User result;

    public TrObtainUser(UserManagerService userManagerService, PetManagerService petManagerService) {
        this.userManagerService = userManagerService;
        this.petManagerService = petManagerService;
    }

    /**
     * Set the uid of the user that has to be obtained.
     * @param uid The uid of the user that has to be obtained
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Set the token of the current user.
     * @param token The token of the current user
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Execute the transaction.
     * @throws PetRepeatException The user has already this pet registered.
     */
    public void execute() throws PetRepeatException, MyPetCareException {
        result = userManagerService.findUserByUsername(uid, token);
        //result.setToken(token);
        result.setPets((ArrayList<Pet>) petManagerService.findPetsByOwner(result));
    }

    /**
     * Get the result of the transaction.
     * @return The result of the transaction
     */
    public User getResult() {
        return result;
    }
}

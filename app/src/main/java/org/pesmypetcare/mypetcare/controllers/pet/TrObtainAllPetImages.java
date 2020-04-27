package org.pesmypetcare.mypetcare.controllers.pet;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;

import java.util.Map;

public class TrObtainAllPetImages {
    private PetManagerService petManagerService;
    private User user;
    private Map<String, byte[]> result;

    public TrObtainAllPetImages(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    /**
     * Set the user that owns the pet.
     * @param user The user that owns the pets
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Execute the transaction.
     */
    public void execute() {
        result = petManagerService.getAllPetsImages(user);
    }

    /**
     * Get the result of the transaction.
     * @return
     */
    public Map<String, byte[]> getResult() {
        return result;
    }
}

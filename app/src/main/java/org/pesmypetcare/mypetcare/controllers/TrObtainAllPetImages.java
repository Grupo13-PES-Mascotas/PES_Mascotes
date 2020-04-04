package org.pesmypetcare.mypetcare.controllers;

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

    public void setUser(User user) {
        this.user = user;
    }

    public void execute() {
        result = petManagerService.getAllPetsImages(user);
    }

    public Map<String, byte[]> getResult() {
        return result;
    }
}

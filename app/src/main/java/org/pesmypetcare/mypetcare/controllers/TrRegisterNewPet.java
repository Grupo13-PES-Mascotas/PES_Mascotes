package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;

public class TrRegisterNewPet {
    private PetManagerService petManagerService;
    private User user;
    private Pet pet;
    private boolean result;

    public TrRegisterNewPet(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void execute() throws PetAlreadyExistingException {
        result = false;
        if (user.getPets().contains(pet)) {
            throw new PetAlreadyExistingException();
        }

        user.addPet(pet);
        petManagerService.registerNewPet(user.getUsername(), pet);
        result = true;
    }

    public boolean getResult() {
        return result;
    }
}

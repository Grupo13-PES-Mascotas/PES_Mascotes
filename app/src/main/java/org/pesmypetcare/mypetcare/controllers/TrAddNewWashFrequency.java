package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;

public class TrAddNewWashFrequency {
    private PetManagerService petManagerService;
    private User user;
    private Pet pet;
    private int newWashFrequency;

    public TrAddNewWashFrequency(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setNewWashFrequency(int newWashFrequency) {
        this.newWashFrequency = newWashFrequency;
    }

    public void execute() throws NotPetOwnerException {
        if (!pet.isOwner(user)) {
            throw new NotPetOwnerException();
        }

        petManagerService.updateWashFrequency(user, pet, newWashFrequency);
        pet.setWashFrequency(newWashFrequency);
    }
}

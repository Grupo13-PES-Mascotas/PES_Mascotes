package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;

public class TrAddNewWeight {
    private PetManagerService petManagerService;
    private User user;
    private Pet pet;
    private double newWeight;

    public TrAddNewWeight(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setNewWeight(double newWeight) {
        this.newWeight = newWeight;
    }

    public void execute() throws NotPetOwnerException {
        if (!pet.getOwner().equals(user)) {
            throw new NotPetOwnerException();
        }

        petManagerService.updateWeight(user, pet, newWeight);
        pet.setWeight(newWeight);
    }
}

package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

public class TrDeleteWeight {
    private PetManagerService petManagerService;
    private User user;
    private Pet pet;
    private DateTime dateTime;

    public TrDeleteWeight(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void execute() throws NotPetOwnerException {
        if (!pet.getOwner().equals(user)) {
            throw new NotPetOwnerException();
        }

        petManagerService.deletePetWeight(user, pet, dateTime);
        pet.deleteWeightForDate(dateTime);
    }
}

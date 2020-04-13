package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

public class TrAddNewWeight {
    private PetManagerService petManagerService;
    private User user;
    private Pet pet;
    private double newWeight;

    private DateTime dateTime;

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

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void execute() throws NotPetOwnerException {
        if (!pet.isOwner(user)) {
            throw new NotPetOwnerException();
        }

        petManagerService.updateWeight(user, pet, newWeight, dateTime);
        pet.setWeightForDate(newWeight, dateTime);
    }
}

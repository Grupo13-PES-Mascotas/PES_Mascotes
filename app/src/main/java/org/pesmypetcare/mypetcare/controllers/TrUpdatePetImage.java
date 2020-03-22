package org.pesmypetcare.mypetcare.controllers;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;

public class TrUpdatePetImage {
    private PetManagerService petManagerService;
    private User user;
    private Pet pet;
    private Bitmap newPetImage;

    public TrUpdatePetImage(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setNewPetImage(Bitmap newPetImage) {
        this.newPetImage = newPetImage;
    }

    public void execute() throws NotPetOwnerException {
        if (isNotOwner()) {
            throw new NotPetOwnerException();
        }

        petManagerService.updatePetImage(user.getUsername(), pet.getName(), newPetImage);
        pet.setProfileImage(newPetImage);
    }

    private boolean isNotOwner() {
        return !pet.getOwner().equals(user);
    }
}

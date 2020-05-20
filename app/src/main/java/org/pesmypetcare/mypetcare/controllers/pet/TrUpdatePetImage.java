package org.pesmypetcare.mypetcare.controllers.pet;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.pet.PetManagerService;

/**
 * @author Albert Pinto
 */
public class TrUpdatePetImage {
    private PetManagerService petManagerService;
    private User user;
    private Pet pet;
    private Bitmap newPetImage;

    public TrUpdatePetImage(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    /**
     * Set the user that wants to update the image of the pet.
     * @param user The user that wants to update the image of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the pet to which the image will be updated.
     * @param pet The pet to which the image will be updated
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Set the new image of the pet.
     * @param newPetImage The new image of the pet
     */
    public void setNewPetImage(Bitmap newPetImage) {
        this.newPetImage = newPetImage;
    }

    /**
     * Executes the transaction.
     * @throws NotPetOwnerException The pet does not belong to the user
     */
    public void execute() throws NotPetOwnerException {
        if (!isOwner()) {
            throw new NotPetOwnerException();
        }

        pet.setProfileImage(newPetImage);
        petManagerService.updatePetImage(user, pet, newPetImage);
    }

    /**
     * Check whether the pet belongs to the user.
     * @return True if the pet does not belong to the user
     */
    private boolean isOwner() {
        return pet.getOwner().equals(user);
    }
}

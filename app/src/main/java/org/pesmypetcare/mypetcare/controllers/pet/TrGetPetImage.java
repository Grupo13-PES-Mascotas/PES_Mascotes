package org.pesmypetcare.mypetcare.controllers.pet;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.pet.PetManagerService;

/**
 * @author Albert Pinto
 */
public class TrGetPetImage {
    private PetManagerService petManagerService;
    private User user;
    private Pet pet;
    private byte[] result;

    public TrGetPetImage(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void execute() throws NotPetOwnerException {
        if (!pet.isOwner(user)) {
            throw new NotPetOwnerException();
        }

        result = petManagerService.getPetImage(user, pet);
    }

    public byte[] getResult() {
        return result;
    }
}

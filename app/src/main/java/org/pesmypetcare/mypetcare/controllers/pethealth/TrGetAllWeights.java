package org.pesmypetcare.mypetcare.controllers.pethealth;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.pet.PetManagerService;
import org.pesmypetcare.usermanager.datacontainers.pet.Weight;

import java.util.List;

/**
 * @author Albert Pinto
 */
public class TrGetAllWeights {
    private User user;
    private Pet pet;
    private PetManagerService petManagerService;

    public TrGetAllWeights(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    /**
     * Set the user.
     * @param user The user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the pet.
     * @param pet The pet to set
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Execute the transaction.
     * @throws NotPetOwnerException The pet does not belong to the owner
     */
    public void execute() throws NotPetOwnerException {
        if (!pet.isOwner(user)) {
            throw new NotPetOwnerException();
        }

        List<Weight> weights = petManagerService.getAllWeights(user, pet);

        for (Weight weight : weights) {
            pet.setWeightForDate(weight.getBody().getValue(), DateTime.Builder.buildFullString(weight.getKey()));
        }
    }
}

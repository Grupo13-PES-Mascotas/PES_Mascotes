package org.pesmypetcare.mypetcare.controllers.exercise;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.Exercise;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.pet.PetManagerService;

import java.util.List;

/**
 * @author Albert Pinto
 */
public class TrGetAllExercises {
    private PetManagerService petManagerService;
    private User user;
    private Pet pet;
    private List<Exercise> result;

    public TrGetAllExercises(PetManagerService petManagerService) {
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
     * @throws NotPetOwnerException The user is not the owner of the pet
     */
    public void execute() throws NotPetOwnerException {
        if (!pet.isOwner(user)) {
            throw new NotPetOwnerException();
        }

        result = petManagerService.getAllExercises(user, pet);
    }

    /**
     * Get the result.
     * @return The result of the transaction
     */
    public List<Exercise> getResult() {
        return result;
    }
}

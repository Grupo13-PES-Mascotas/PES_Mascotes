package org.pesmypetcare.mypetcare.controllers.exercise;

import org.pesmypetcare.mypetcare.features.pets.events.exercise.Exercise;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.pet.PetManagerService;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public void setUser(User user) {
        this.user = user;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void execute() throws NotPetOwnerException, ExecutionException, InterruptedException {
        if (!pet.isOwner(user)) {
            throw new NotPetOwnerException();
        }

        result = petManagerService.getAllExercises(user, pet);
    }

    public List<Exercise> getResult() {
        return result;
    }
}

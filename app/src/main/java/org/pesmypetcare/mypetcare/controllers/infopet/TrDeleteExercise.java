package org.pesmypetcare.mypetcare.controllers.infopet;

import org.pesmypetcare.mypetcare.features.pets.Exercise;
import org.pesmypetcare.mypetcare.features.pets.NotExistingExerciseException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

/**
 * @author Albert Pinto
 */
public class TrDeleteExercise {
    private PetManagerService petManagerService;
    private User user;
    private Pet pet;
    private DateTime dateTime;

    public TrDeleteExercise(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setExerciseDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void execute() throws NotPetOwnerException, NotExistingExerciseException {
        if (!pet.isOwner(user)) {
            throw new NotPetOwnerException();
        } else if (!pet.containsEvent(dateTime, Exercise.class)) {
            throw new NotExistingExerciseException();
        }

        petManagerService.deleteExercise(pet, dateTime);
        pet.deleteExerciseForDate(dateTime);
    }
}

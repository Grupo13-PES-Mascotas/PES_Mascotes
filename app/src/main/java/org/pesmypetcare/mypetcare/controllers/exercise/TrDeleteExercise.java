package org.pesmypetcare.mypetcare.controllers.exercise;

import org.pesmypetcare.mypetcare.features.pets.Exercise;
import org.pesmypetcare.mypetcare.features.pets.NotExistingExerciseException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Walk;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.concurrent.ExecutionException;

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

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet to whom the exercise will be deleted.
     * @param pet The pet to whom the exercise will be deleted
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the  date and time of the exercise that will be deleted.
     * @param dateTime The date and time of the exercise that will be deleted
     */
    public void setExerciseDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Execute the transaction.
     */
    public void execute() throws NotPetOwnerException, NotExistingExerciseException, ExecutionException, InterruptedException {
        if (!pet.isOwner(user)) {
            throw new NotPetOwnerException();
        } else if (!pet.containsEvent(dateTime, Exercise.class) && !pet.containsEvent(dateTime, Walk.class)) {
            throw new NotExistingExerciseException();
        }

        if (pet.containsEvent(dateTime, Walk.class)) {
            for (Pet userPet : user.getPets()) {
                if (userPet.containsEvent(dateTime, Walk.class)) {
                    petManagerService.deleteExercise(user, userPet, dateTime);
                    userPet.deleteExerciseForDate(dateTime);
                }
            }
        } else {
            petManagerService.deleteExercise(user, pet, dateTime);
            pet.deleteExerciseForDate(dateTime);
        }
    }
}

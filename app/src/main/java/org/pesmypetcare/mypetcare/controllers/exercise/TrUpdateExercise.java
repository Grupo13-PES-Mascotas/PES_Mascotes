package org.pesmypetcare.mypetcare.controllers.exercise;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Exercise;
import org.pesmypetcare.mypetcare.features.pets.InvalidPeriodException;
import org.pesmypetcare.mypetcare.features.pets.NotExistingExerciseException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Albert Pinto
 */
public class TrUpdateExercise {
    private PetManagerService petManagerService;
    private User user;
    private Pet pet;
    private String exerciseName;
    private String exerciseDescription;
    private DateTime originalDateTime;
    private DateTime startDateTime;
    private DateTime endDateTime;
    
    public TrUpdateExercise(PetManagerService petManagerService) {
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
     * Setter of the pet to whom the exercise will be updated.
     * @param pet The pet to whom the exercise will be updated
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the name of the exercise that will be updated.
     * @param exerciseName The name of the exercise that will be updated
     */
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    /**
     * Setter of the description of the exercise that will be updated.
     * @param exerciseDescription The description of the exercise that will be updated
     */
    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    /**
     * Setter of the original date and time of the exercise that will be updated.
     * @param originalDateTime The original date and time of the exercise that will be updated
     */
    public void setOriginalStartDateTime(DateTime originalDateTime) {
        this.originalDateTime = originalDateTime;
    }

    /**
     * Setter of the start date and time of the exercise that will be updated.
     * @param startDateTime The start date and time of the exercise that will be updated
     */
    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Setter of the end date and time of the exercise that will be updated.
     * @param endDateTime The end date and time of the exercise that will be updated
     */
    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Execute the transaction.
     */
    public void execute() throws NotPetOwnerException, NotExistingExerciseException, InvalidPeriodException, ExecutionException, InterruptedException {
        if (!pet.isOwner(user)) {
            throw new NotPetOwnerException();
        } else if (startDateTime.compareTo(endDateTime) > 0 || isDifferentDate(startDateTime, endDateTime)) {
            throw new InvalidPeriodException();
        } else if (!isFound()) {
            throw new NotExistingExerciseException();
        }

        Exercise exercise = new Exercise(exerciseName, exerciseDescription, startDateTime, endDateTime);
        petManagerService.updateExercise(user, pet, originalDateTime, exercise);
        pet.deleteExerciseForDate(originalDateTime);
        pet.addExercise(exercise);
    }

    /**
     * Method responsible for checking if the exercise exists.
     * @return True if the exercise exists
     */
    private boolean isFound() {
        List<Event> exercises = pet.getEventsByClass(Exercise.class);
        boolean found = false;

        for (Event event : exercises) {
            if (event.getDateTime().compareTo(originalDateTime) == 0) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Method responsible for checking if the start and end date are different.
     * @return True if the dates are different
     */
    private boolean isDifferentDate(DateTime startDateTime, DateTime endDateTime) {
        return startDateTime.getYear() != endDateTime.getYear() || startDateTime.getMonth() != endDateTime.getMonth()
            || startDateTime.getDay() != endDateTime.getDay();
    }
}

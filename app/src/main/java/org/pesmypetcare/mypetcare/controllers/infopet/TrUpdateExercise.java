package org.pesmypetcare.mypetcare.controllers.infopet;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Exercise;
import org.pesmypetcare.mypetcare.features.pets.InvalidPeriodException;
import org.pesmypetcare.mypetcare.features.pets.NotExistingExerciseException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.List;

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

    public void setUser(User user) {
        this.user = user;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public void setOriginalStartDateTime(DateTime originalDateTime) {
        this.originalDateTime = originalDateTime;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void execute() throws NotPetOwnerException, NotExistingExerciseException, InvalidPeriodException {
        if (!pet.isOwner(user)) {
            throw new NotPetOwnerException();
        } else if (startDateTime.compareTo(endDateTime) > 0 || isDifferentDate(startDateTime, endDateTime)) {
            throw new InvalidPeriodException();
        } else if (!isFound()) {
            throw new NotExistingExerciseException();
        }

        Exercise exercise = new Exercise(exerciseName, exerciseDescription, startDateTime, endDateTime);
        petManagerService.updateExercise(user, pet, exercise);
        pet.deleteExerciseForDate(originalDateTime);
        pet.addExercise(exercise);
    }

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

    private boolean isDifferentDate(DateTime startDateTime, DateTime endDateTime) {
        return startDateTime.getYear() != endDateTime.getYear() || startDateTime.getMonth() != endDateTime.getMonth()
            || startDateTime.getDay() != endDateTime.getDay();
    }
}

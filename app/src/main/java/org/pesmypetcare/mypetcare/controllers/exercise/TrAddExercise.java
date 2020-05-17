package org.pesmypetcare.mypetcare.controllers.exercise;

import org.pesmypetcare.httptools.exceptions.InvalidFormatException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Exercise;
import org.pesmypetcare.mypetcare.features.pets.InvalidPeriodException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.GoogleCalendarService;
import org.pesmypetcare.mypetcare.services.PetManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Albert Pinto
 */
public class TrAddExercise {
    private PetManagerService petManagerService;
    private GoogleCalendarService googleCalendarService;
    private User user;
    private Pet pet;
    private String exerciseName;
    private String exerciseDescription;
    private DateTime startDateTime;
    private DateTime endDateTime;

    public TrAddExercise(PetManagerService petManagerService, GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
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
     * Setter of the pet to whom the exercise will be added.
     * @param pet The pet to whom the exercise will be added
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the name of the exercise that will be added.
     * @param exerciseName The name of the exercise that will be added
     */
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    /**
     * Setter of the description of the exercise that will be added.
     * @param exerciseDescription The description of the exercise that will be added
     */
    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    /**
     * Setter of the start date and time of the exercise that will be added.
     * @param startDateTime The start date and time of the exercise that will be added
     */
    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Setter of the end date and time of the exercise that will be added.
     * @param endDateTime The end date and time of the exercise that will be added
     */
    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Execute the transaction.
     */
    public void execute() throws NotPetOwnerException, InvalidPeriodException, ExecutionException,
        InterruptedException, InvalidFormatException {
        if (!pet.isOwner(user)) {
            throw new NotPetOwnerException();
        } else if (startDateTime.compareTo(endDateTime) > 0 || isDifferentDate(startDateTime, endDateTime)) {
            throw new InvalidPeriodException();
        }

        Exercise exercise = new Exercise(exerciseName, exerciseDescription, startDateTime, endDateTime);
        petManagerService.addExercise(user, pet, exercise);
        pet.addExercise(exercise);
        googleCalendarService.registerNewEvent(pet, exercise);
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

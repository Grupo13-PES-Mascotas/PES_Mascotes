package org.pesmypetcare.mypetcare.controllers.exercise;

import com.google.android.gms.maps.model.LatLng;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.events.InvalidPeriodException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.walk.Walk;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.pet.PetManagerService;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Albert Pinto
 */
public class TrAddWalk {
    private PetManagerService petManagerService;
    private User user;
    private List<Pet> pets;
    private String name;
    private String description;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private List<LatLng> coordinates;
    
    public TrAddWalk(PetManagerService petManagerService) {
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
     * Set the pets.
     * @param pets The pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    /**
     * Set the name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the description.
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the start DateTime.
     * @param startDateTime The start DateTime to set
     */
    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Set the end DateTime.
     * @param endDateTime The end DateTime to set
     */
    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Set the coordinates.
     * @param coordinates The coordinates to set
     */
    public void setCoordinates(List<LatLng> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Execute the transaction.
     * @throws NotPetOwnerException The pet does not belong to the owner
     * @throws InvalidPeriodException The period is invalid
     */
    public void execute() throws NotPetOwnerException, InvalidPeriodException, ExecutionException,
        InterruptedException {
        for (Pet pet : pets) {
            if (!pet.isOwner(user)) {
                throw new NotPetOwnerException();
            }
        }

        if (startDateTime.compareTo(endDateTime) > 0 || isDifferentDate(startDateTime, endDateTime)) {
            throw new InvalidPeriodException();
        }

        Walk walk = new Walk(name, description, startDateTime, endDateTime, coordinates);

        for (Pet pet : pets) {
            petManagerService.addWalking(user, pet, walk);
            pet.addExercise(walk);
        }
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

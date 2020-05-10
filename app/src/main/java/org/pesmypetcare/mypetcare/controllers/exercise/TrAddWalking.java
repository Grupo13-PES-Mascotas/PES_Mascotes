package org.pesmypetcare.mypetcare.controllers.exercise;

import com.google.android.gms.maps.model.LatLng;

import org.pesmypetcare.mypetcare.features.pets.InvalidPeriodException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Walking;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.List;

/**
 * @author Albert Pinto
 */
public class TrAddWalking {
    private PetManagerService petManagerService;
    private User user;
    private List<Pet> pets;
    private String name;
    private String description;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private List<LatLng> coordinates;
    
    public TrAddWalking(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setCoordinates(List<LatLng> coordinates) {
        this.coordinates = coordinates;
    }

    public void execute() throws NotPetOwnerException, InvalidPeriodException {
        for (Pet pet : pets) {
            if (!pet.isOwner(user)) {
                throw new NotPetOwnerException();
            }
        }

        if (startDateTime.compareTo(endDateTime) > 0 || isDifferentDate(startDateTime, endDateTime)) {
            throw new InvalidPeriodException();
        }

        Walking walking = new Walking(name, description, startDateTime, endDateTime, coordinates);

        for (Pet pet : pets) {
            pet.addExercise(walking);
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

package org.pesmypetcare.mypetcare.controllers.washes;

import org.pesmypetcare.httptools.exceptions.InvalidFormatException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.pets.events.wash.Wash;
import org.pesmypetcare.mypetcare.features.pets.events.wash.WashAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.googlecalendar.GoogleCalendarService;
import org.pesmypetcare.mypetcare.services.wash.WashManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Enric Hernando
 */
public class TrNewPetWash {
    private WashManagerService washManagerService;
    private GoogleCalendarService googleCalendarService;
    private User user;
    private Pet pet;
    private Wash wash;
    private Boolean result;

    public TrNewPetWash(WashManagerService washManagerService, GoogleCalendarService googleCalendarService) {
        this.washManagerService = washManagerService;
        this.googleCalendarService = googleCalendarService;
    }

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet to whom the meal will be added.
     * @param pet The pet to whom the meal will be added
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the wash that has to be added to the pet.
     * @param wash The wash that has to be added to the pet
     */
    public void setWash(Wash wash) {
        this.wash = wash;
    }

    /**
     * Getter of the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Execute the transaction.
     */
    public void execute() throws WashAlreadyExistingException, InterruptedException, ExecutionException,
        InvalidFormatException {
        result = false;
        if (mealHasAlreadyBeenAdded()) {
            throw new WashAlreadyExistingException();
        }
        pet.addEvent(wash);
        washManagerService.createWash(user, pet, wash);
        googleCalendarService.registerNewEvent(pet, wash);
        result = true;
    }

    /**
     * Method responsible for checking if the wash has already been added to the pet.
     * @return True if the wash has already been added or false otherwise
     */
    private boolean mealHasAlreadyBeenAdded() {
        for (Event e : pet.getWashEvents()) {
            Wash m = (Wash) e;
            if (m.getDateTime().compareTo(wash.getDateTime()) == 0) {
                return true;
            }
        }
        return false;
    }
}

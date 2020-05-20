package org.pesmypetcare.mypetcare.controllers.event;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.googlecalendar.GoogleCalendarService;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

/**
 * @author Daniel Clemente
 */

public class TrNewPeriodicNotification {
    private GoogleCalendarService googleCalendarService;
    private Pet pet;
    private Event event;
    private boolean result;
    private int period;
    private User user;

    public TrNewPeriodicNotification(GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
    }

    /**
     * Set the user that wants to add a periodic notification.
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user; }

    /**
     * Set the pet that the user wants to add a periodic notification.
     * @param selectedPet The pet which the user want to add a periodic notification
     */
    public void setPet(Pet selectedPet) {
        this.pet = selectedPet;
    }

    /**
     * Set the periodic event that the user wants to add.
     * @param event The period event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Set the period of the periodic notification that will be added.
     * @param period the period selected
     */
    public void setPeriodicity(int period) {
        this.period = period;
    }

    /**
     * Get the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Execute the transaction.
     * @throws UserIsNotOwnerException The user is not the owner of the pet
     * @throws ParseException The date of the event is not in correct format
     */
    public void execute() throws ParseException, UserIsNotOwnerException, ExecutionException, InterruptedException {
        result = false;
        if (pet.getOwner() != user) {
            throw new UserIsNotOwnerException();
        }
        pet.addPeriodicNotification(event, period);
        googleCalendarService.registerNewPeriodicNotification(user, pet, event, period);
        result = true;
    }
}

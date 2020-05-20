package org.pesmypetcare.mypetcare.controllers.event;

import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.googlecalendar.GoogleCalendarService;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

/**
 * @author Daniel Clemente
 */
public class TrDeletePeriodicNotification {
    private GoogleCalendarService googleCalendarService;
    private Pet pet;
    private Event event;
    private boolean result;
    private User user;

    public TrDeletePeriodicNotification(GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
    }

    /**
     * Set the user that wants to delete a periodic notification.
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the pet that the user wants to delete a periodic notification.
     * @param selectedPet The pet which the user want to delete a periodic notification
     */
    public void setPet(Pet selectedPet) {
        this.pet = selectedPet;
    }

    /**
     * Set the periodic event that the user wants to delete.
     * @param event The period event
     */
    public void setEvent(Event event) {
        this.event = event;
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
        pet.deletePeriodicNotification(event);
        googleCalendarService.deletePeriodicEvent(user, pet, event);
        result = true;
    }
}

package org.pesmypetcare.mypetcare.controllers.event;

import org.pesmypetcare.httptools.exceptions.InvalidFormatException;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.services.GoogleCalendarService;

import java.util.concurrent.ExecutionException;

public class TrNewPersonalEvent {
    private GoogleCalendarService googleCalendarService;
    private Pet pet;
    private Event event;
    private boolean result;

    public TrNewPersonalEvent(GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
    }

    /**
     * Set the pet that the user wants to add a event.
     * @param pet The pet
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Set the event that the user wants to add.
     * @param event The event
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
     */
    public void execute() throws ExecutionException, InterruptedException, InvalidFormatException {
        result = false;
        pet.addEvent(event);
        googleCalendarService.registerNewEvent(pet, event);
        result = true;
    }
}

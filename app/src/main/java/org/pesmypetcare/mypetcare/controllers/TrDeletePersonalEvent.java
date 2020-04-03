package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.services.PetManagerService;

public class TrDeletePersonalEvent {
    private PetManagerService petManagerService;
    private Pet pet;
    private Event event;
    private boolean result;

    public TrDeletePersonalEvent(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
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
    public void execute() {
        result = false;
        pet.addEvent(event);
        petManagerService.deleteEvent(pet, event);
        result = true;
    }
}

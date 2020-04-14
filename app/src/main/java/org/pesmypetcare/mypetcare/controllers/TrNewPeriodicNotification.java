package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.services.PetManagerService;

public class TrNewPeriodicNotification {
    private PetManagerService petManagerService;
    private Pet pet;
    private Event event;
    private boolean result;

    public TrNewPeriodicNotification(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setPet(Pet selectedPet) {
    }

    public void setEvent(Event event) {
    }
}

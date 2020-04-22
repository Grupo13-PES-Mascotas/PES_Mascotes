package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.services.PetManagerService;

import java.text.ParseException;

/**
 * @author Daniel Clemente
 */
public class TrDeletePeriodicNotification {
    private PetManagerService petManagerService;
    private Pet pet;
    private Event event;
    private boolean result;
    private int period;
    private int periodDay;

    public TrDeletePeriodicNotification(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setPet(Pet selectedPet) {
        this.pet = selectedPet;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setPeriodicity(int period, int periodDay) {
        this.period = period;
        this.periodDay = periodDay;
    }

    public boolean isResult() {
        return result;
    }

    public void execute() throws ParseException {
        result = false;
        pet.deletePeriodicNotification(event.getDateTime(), period, event.getDescription());
        result = true;
    }

}

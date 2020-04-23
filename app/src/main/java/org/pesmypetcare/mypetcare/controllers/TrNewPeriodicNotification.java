package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;

import java.text.ParseException;

public class TrNewPeriodicNotification {
    private PetManagerService petManagerService;
    private Pet pet;
    private Event event;
    private boolean result;
    private int period;
    private User user;

    public TrNewPeriodicNotification(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setUser(User user) { this.user = user; }


    public void setPet(Pet selectedPet) {
        this.pet = selectedPet;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setPeriodicity(int period) {
        this.period = period;
    }

    public boolean isResult() {
        return result;
    }

    public void execute() throws ParseException, UserIsNotOwnerException {
        result = false;
        if (pet.getOwner() != user) {
            throw new UserIsNotOwnerException();
        }
        pet.addPeriodicNotification(event, period);
        petManagerService.registerNewPeriodicNotification(user, pet, event, period);
        result = true;
    }
}

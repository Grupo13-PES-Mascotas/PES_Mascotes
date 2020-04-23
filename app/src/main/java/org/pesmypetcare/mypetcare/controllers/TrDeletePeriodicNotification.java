package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
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
    private User user;

    public TrDeletePeriodicNotification(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPet(Pet selectedPet) {
        this.pet = selectedPet;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isResult() {
        return result;
    }

    public void execute() throws ParseException, UserIsNotOwnerException {
        result = false;
        if (pet.getOwner() != user) {
            throw new UserIsNotOwnerException();
        }
        pet.deletePeriodicNotification(event);
        petManagerService.deletePeriodicEvent(user, pet, event);
        result = true;
    }

}

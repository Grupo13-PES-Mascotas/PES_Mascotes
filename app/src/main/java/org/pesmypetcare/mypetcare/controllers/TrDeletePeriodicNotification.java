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

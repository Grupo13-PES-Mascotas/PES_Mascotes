package org.pesmypetcare.mypetcare.controllers.washes;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.wash.Wash;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.wash.WashManagerService;

/**
 * @author Enric Hernando
 */
public class TrUpdateWash {
    private WashManagerService washManagerService;
    private User user;
    private Pet pet;
    private Wash wash;
    private String newDate;
    private boolean updatesDate;

    public TrUpdateWash(WashManagerService washManagerService) {
        this.washManagerService = washManagerService;
        this.updatesDate = false;
    }

    /**
     * Setter if the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet from which we want to update the wash.
     * @param pet The setter of the pet from which we want to update the wash
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of updated wash.
     * @param wash The updated wash
     */
    public void setWash(Wash wash) {
        this.wash = wash;
    }

    /**
     * Setter of the new date of the wash.
     * @param newDate The new date of the wash
     */
    public void setNewDate(String newDate) {
        this.updatesDate = true;
        this.newDate = newDate;
    }

    /**
     * Execute the transaction.
     */
    public void execute() {
        washManagerService.updateWashBody(user, pet, wash);
        if (updatesDate) {
            washManagerService.updateWashDate(user, pet, newDate, wash.getDateTime().toString());
        }
    }
}

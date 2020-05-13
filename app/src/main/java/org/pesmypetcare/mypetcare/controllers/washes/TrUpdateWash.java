package org.pesmypetcare.mypetcare.controllers.washes;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Wash;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.WashManagerService;

import java.util.concurrent.ExecutionException;

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
     * Setter of the pet from which we want to update the meal.
     * @param pet The setter of the pet from which we want to update the meal
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
        try {
            washManagerService.updateWashBody(user, pet, wash);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (updatesDate) {
            try {
                washManagerService.updateWashDate(user, pet, newDate, wash.getDateTime().toString());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

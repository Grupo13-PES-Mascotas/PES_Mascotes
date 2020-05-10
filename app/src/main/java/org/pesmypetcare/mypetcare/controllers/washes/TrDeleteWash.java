package org.pesmypetcare.mypetcare.controllers.washes;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Wash;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.WashManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Enric Hernando
 */
public class TrDeleteWash {
    private WashManagerService washManagerService;
    private User user;
    private Pet pet;
    private Wash wash;

    public TrDeleteWash(WashManagerService washManagerService) {
        this.washManagerService = washManagerService;
    }

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet from which we want to delete the meal.
     * @param pet The pet from which we want to delete the meal
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the wash that has to be deleted.
     * @param wash The wash that has to be deleted
     */
    public void setWash(Wash wash) {
        this.wash = wash;
    }

    /**
     * Executes the transaction.
     */
    public void execute() {
        try {
            washManagerService.deleteWash(user, pet, wash);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        pet.deleteEvent(wash);
    }
}

package org.pesmypetcare.mypetcare.controllers.user;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotValidUserException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.meal.MealManagerService;
import org.pesmypetcare.mypetcare.services.medication.MedicationManagerService;
import org.pesmypetcare.mypetcare.services.pet.PetManagerService;
import org.pesmypetcare.mypetcare.services.user.UserManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Enric Hernando
 */
public class TrDeleteUser {
    private UserManagerService userManagerService;
    private PetManagerService petManagerService;
    private MealManagerService mealManagerService;
    private MedicationManagerService medicationManagerService;
    private User user;
    private Boolean result;

    public TrDeleteUser(UserManagerService userManagerService, PetManagerService petManagerService,
                        MealManagerService mealManagerService, MedicationManagerService medicationManagerService) {
        this.userManagerService = userManagerService;
        this.petManagerService = petManagerService;
        this.mealManagerService = mealManagerService;
        this.medicationManagerService = medicationManagerService;
    }

    /**
     * Set the user to be deleted.
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Execute the transaction.
     * @throws NotValidUserException The user doesn't exist
     */
    public void execute() throws NotValidUserException, ExecutionException, InterruptedException {
        result = false;
        if (!userHasAlreadyBeenRegistered()) {
            throw new NotValidUserException();
        }
        for (Pet p:user.getPets()) {
            mealManagerService.deleteMealsFromPet(user, p);
            medicationManagerService.deleteMedicationsFromPet(user, p);
        }
        userManagerService.deleteUser(user);
        petManagerService.deletePetsFromUser(user);
        result = true;
    }

    /**
     * Checks whether the user is registered.
     * @return True if the user is registered
     */
    private boolean userHasAlreadyBeenRegistered() {
        return userManagerService.userExists(user);
    }

    /**
     * Get the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return this.result;
    }
}

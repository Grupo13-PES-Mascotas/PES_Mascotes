package org.pesmypetcare.mypetcare.controllers.pet;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.meal.MealManagerService;
import org.pesmypetcare.mypetcare.services.medication.MedicationManagerService;
import org.pesmypetcare.mypetcare.services.pet.PetManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Albert Pinto
 */
public class TrDeletePet {
    private PetManagerService petManagerService;
    private MealManagerService mealManagerService;
    private MedicationManagerService medicationManagerService;
    private User user;
    private Pet pet;

    public TrDeletePet(PetManagerService petManagerService, MealManagerService mealManagerService,
                       MedicationManagerService medicationManagerService) {
        this.petManagerService = petManagerService;
        this.mealManagerService = mealManagerService;
        this.medicationManagerService = medicationManagerService;
    }

    /**
     * Set the user to whom the pet will be deleted.
     * @param user The user that wants to delete a pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the pet that the user wants to delete.
     * @param pet The pet that will be deleted
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Execute the transaction.
     * @throws UserIsNotOwnerException The user is not the owner of the pet
     */
    public void execute() throws UserIsNotOwnerException, ExecutionException, InterruptedException {
        if (pet.getOwner() != user) {
            throw new UserIsNotOwnerException();
        }
        mealManagerService.deleteMealsFromPet(user, pet);
        medicationManagerService.deleteMedicationsFromPet(user, pet);
        petManagerService.deletePet(pet, user);
        user.deletePet(pet);
    }
}

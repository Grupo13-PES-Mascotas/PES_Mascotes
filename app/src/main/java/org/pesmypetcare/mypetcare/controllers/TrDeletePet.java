package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MealManagerService;
import org.pesmypetcare.mypetcare.services.PetManagerService;

public class TrDeletePet {
    private PetManagerService petManagerService;
    private MealManagerService mealManagerService;
    private User user;
    private Pet pet;

    public TrDeletePet(PetManagerService petManagerService, MealManagerService mealManagerService) {
        this.petManagerService = petManagerService;
        this.mealManagerService = mealManagerService;
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
    public void execute() throws UserIsNotOwnerException {
        if (pet.getOwner() != user) {
            throw new UserIsNotOwnerException();
        }

        mealManagerService.deleteMealsFromPet(user, pet);
        petManagerService.deletePet(pet, user);
        user.deletePet(pet);
    }
}

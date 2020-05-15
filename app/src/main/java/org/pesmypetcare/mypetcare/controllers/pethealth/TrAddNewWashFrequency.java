package org.pesmypetcare.mypetcare.controllers.pethealth;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.concurrent.ExecutionException;

public class TrAddNewWashFrequency {
    private PetManagerService petManagerService;
    private User user;
    private Pet pet;
    private DateTime dateTime;
    private int newWashFrequency;

    public TrAddNewWashFrequency(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    /**
     * Set the user to whom the pet will be registered.
     * @param user The user that wants to register a new pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the pet that the user wants to register.
     * @param pet The pet that will be registered
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Set the dateTime.
     * @param dateTime The dateTime to set
     */
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Set the new wash frequency.
     * @param newWashFrequency The new wash frequency to set
     */
    public void setNewWashFrequency(int newWashFrequency) {
        this.newWashFrequency = newWashFrequency;
    }

    /**
     * Execute the transaction.
     * @throws NotPetOwnerException The pet does not belong to the user
     */
    public void execute() throws NotPetOwnerException, ExecutionException, InterruptedException {
        if (!pet.isOwner(user)) {
            throw new NotPetOwnerException();
        }

        petManagerService.addWashFrequency(user, pet, newWashFrequency, dateTime);
        pet.setWashFrequencyForDate(newWashFrequency, dateTime);
    }
}

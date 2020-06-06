package org.pesmypetcare.mypetcare.controllers.pet;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.googlecalendar.GoogleCalendarService;
import org.pesmypetcare.mypetcare.services.pet.PetManagerService;

/**
 * @author Albert Pinto
 */
public class TrRegisterNewPet {
    private PetManagerService petManagerService;
    private GoogleCalendarService googleCalendarService;
    private User user;
    private Pet pet;
    private boolean result;

    public TrRegisterNewPet(PetManagerService petManagerService, GoogleCalendarService googleCalendarService) {
        this.petManagerService = petManagerService;
        this.googleCalendarService = googleCalendarService;
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
     * Execute the transaction.
     * @throws PetAlreadyExistingException The pet has already been registered by the user
     */
    public void execute() throws PetAlreadyExistingException {
        result = false;
        if (petHasAlreadyBeenRegistered()) {
            throw new PetAlreadyExistingException();
        }

        user.addPet(pet);
        petManagerService.registerNewPet(user, pet);
        googleCalendarService.newSecondaryCalendar(pet);
        result = true;
    }

    /**
     *Checks whether the pet has already been registered by the user.
     * @return True if the pet has already been registered by the user
     */
    private boolean petHasAlreadyBeenRegistered() {
        return user.getPets().contains(pet);
    }

    /**
     * Get the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return result;
    }
}

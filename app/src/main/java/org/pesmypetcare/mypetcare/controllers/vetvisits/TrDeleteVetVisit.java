package org.pesmypetcare.mypetcare.controllers.vetvisits;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.vetvisit.VetVisit;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.googlecalendar.GoogleCalendarService;
import org.pesmypetcare.mypetcare.services.vetvisits.VetVisitsManagerService;

/**
 * @author Xavier Campos
 */
public class TrDeleteVetVisit {
    private VetVisitsManagerService vetVisitsManagerService;
    private GoogleCalendarService googleCalendarService;
    private User user;
    private Pet pet;
    private VetVisit vetVisit;
    private boolean result;

    public TrDeleteVetVisit(VetVisitsManagerService vetVisitsManagerService,
                            GoogleCalendarService googleCalendarService) {
        this.vetVisitsManagerService = vetVisitsManagerService;
        this.googleCalendarService = googleCalendarService;
    }

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet from which the vet visit has to be removed.
     * @param pet The pet from which the vet visit has to be removed
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the vet visit that has to be removed from the pet.
     * @param vetVisit The vet visit that has to be removed from the pet
     */
    public void setVetVisit(VetVisit vetVisit) {
        this.vetVisit = vetVisit;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the vet visit was removed successfully or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     * @throws NotPetOwnerException The user is not the owner of the pet
     */
    public void execute() throws NotPetOwnerException {
        result = false;
        if (!pet.getOwner().getUsername().equals(user.getUsername())) {
            throw new NotPetOwnerException();
        }
        vetVisitsManagerService.deleteVetVisit(user, pet, vetVisit);
        pet.deleteEvent(vetVisit);
        googleCalendarService.deleteEvent(pet, vetVisit);
        result = true;
    }
}

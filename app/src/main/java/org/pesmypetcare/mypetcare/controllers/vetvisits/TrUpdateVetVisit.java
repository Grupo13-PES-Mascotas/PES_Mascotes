package org.pesmypetcare.mypetcare.controllers.vetvisits;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.vetvisit.VetVisit;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.vetvisits.VetVisitsManagerService;

/**
 * @author Xavier Campos
 */
public class TrUpdateVetVisit {
    private VetVisitsManagerService vetVisitsManagerService;
    private User user;
    private Pet pet;
    private VetVisit vetVisit;
    private String newDate;
    private boolean updatesDate;
    private boolean result;

    public TrUpdateVetVisit(VetVisitsManagerService vetVisitsManagerService) {
        this.vetVisitsManagerService = vetVisitsManagerService;
        this.updatesDate = false;
    }

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet from which the vet visit has to be updated.
     * @param pet The pet from which the vet visit has to be updated
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the updated vet visit.
     * @param vetVisit The updated vet visit
     */
    public void setVetVisit(VetVisit vetVisit) {
        this.vetVisit = vetVisit;
    }

    /**
     * Setter of the new date of the vet visit.
     * @param newDate The new date of the vet visit
     */
    public void setNewDate(String newDate) {
        this.updatesDate = true;
        this.newDate = newDate;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the vet visit was updated successfully or false otherwise
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
        if (!user.getUsername().equals(pet.getOwner().getUsername())) {
            throw new NotPetOwnerException();
        }
        vetVisitsManagerService.updateVetVisitBody(user, pet, vetVisit);
        if (updatesDate) {
            vetVisitsManagerService.updateVetVisitKey(user, pet, newDate, vetVisit.getVisitDate());
        }
    }
}

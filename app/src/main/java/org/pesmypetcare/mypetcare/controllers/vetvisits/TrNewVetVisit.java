package org.pesmypetcare.mypetcare.controllers.vetvisits;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.VetVisit;
import org.pesmypetcare.mypetcare.features.pets.VetVisitAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.VetVisitsManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos
 */
public class TrNewVetVisit {
    private VetVisitsManagerService vetVisitsManagerService;
    private User user;
    private Pet pet;
    private VetVisit vetVisit;
    private boolean result;

    public TrNewVetVisit(VetVisitsManagerService vetVisitsManagerService) {
        this.vetVisitsManagerService = vetVisitsManagerService;
    }

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet to whom the vet visit will be added.
     * @param pet The pet to whom the vet visit will be added
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the vet visit that has to be added to the pet.
     * @param vetVisit The vet visit that has to be added to the pet
     */
    public void setVetVisit(VetVisit vetVisit) {
        this.vetVisit = vetVisit;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the meal was added successfully or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     */
    public void execute() throws VetVisitAlreadyExistingException, NotPetOwnerException, ExecutionException,
        InterruptedException {
        result = false;
        if (!pet.getOwner().getUsername().equals(user.getUsername())) {
            throw new NotPetOwnerException();
        }
        vetVisitsManagerService.createVetVisit(user, pet, vetVisit);
        pet.addEvent(vetVisit);
        result = true;
    }
}

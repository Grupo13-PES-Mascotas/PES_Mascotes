package org.pesmypetcare.mypetcare.controllers.vetvisits;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.VetVisit;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.VetVisitsManagerService;

import java.util.List;

/**
 * @author Xavier Campos
 */
public class TrObtainAllVetVisits {
    private VetVisitsManagerService vetVisitsManagerService;
    private User user;
    private Pet pet;
    private List<VetVisit> result;

    public TrObtainAllVetVisits (VetVisitsManagerService vetVisitsManagerService) {
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
     * Setter of the pet from which we want to obtain all the vet visits.
     * @param pet The pet from which we want to obtain all the vet visits
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Getter of the result of the transaction.
     * @return The list of all the vet visits of the pet
     */
    public List<VetVisit> getResult() {
        return result;
    }

    /**
     * Executes the transaction.
     */
    public void execute() {
        result = vetVisitsManagerService.findVetVisitsByPet(user, pet);
        for (Event e:result) {
            pet.addEvent(e);
        }
    }
}

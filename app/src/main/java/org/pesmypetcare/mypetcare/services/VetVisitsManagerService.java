package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.VetVisit;
import org.pesmypetcare.mypetcare.features.pets.VetVisitAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.List;

/**
 * @author Xavier Campos
 */
public interface VetVisitsManagerService {

    /**
     * Obtains all the vet visits for the given pet.
     * @param user The owner of the pet
     * @param pet The pet from which we want to obtain all the vet visits
     * @return A list containing all the vet visits of the pet
     */
    List<VetVisit> findVetVisitsByPet(User user, Pet pet);

    /**
     * Creates and adds a new vet visit to the given pet.
     * @param user The owner of the pet
     * @param pet The pet where the vet visits has to be added
     * @param vetVisit The vet visit that has to be added to the pet
     */
    void createVetVisit(User user, Pet pet, VetVisit vetVisit) throws VetVisitAlreadyExistingException;

    /**
     * Deletes the vet visit from the given pet.
     * @param user The owner of the pet
     * @param pet The pet from where the vet visit has to be removed
     * @param vetVisit The vet visit that has to be removed from the pet
     */
    void deleteVetVisit(User user, Pet pet, VetVisit vetVisit);

    /**
     * Updates the key of the visit date for the given visit date.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the visit
     * @param newDate The new date to set to the visit
     * @param visitDate The current date of the visit
     */
    void updateVetVisitKey(User user, Pet pet, String newDate, DateTime visitDate);

    /**
     * Updates the body of the given visit.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the visit
     * @param vetVisit The vet visit with the updated body
     */
    void updateVetVisitBody(User user, Pet pet, VetVisit vetVisit);
}

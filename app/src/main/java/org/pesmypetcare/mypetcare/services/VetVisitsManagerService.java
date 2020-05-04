package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.VetVisit;
import org.pesmypetcare.mypetcare.features.users.User;

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
    void createVetVisit(User user, Pet pet, VetVisit vetVisit);
}

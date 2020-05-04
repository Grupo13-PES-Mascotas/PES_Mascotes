package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.VetVisit;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;

/**
 * @author Xavier Campos
 */
public class VetVisitsAdapter implements VetVisitsManagerService {
    @Override
    public List<VetVisit> findVetVisitsByPet(User user, Pet pet) {
        //Not implemented yet
        return null;
    }

    @Override
    public void createVetVisit(User user, Pet pet, VetVisit vetVisit) {
        // Not implemented yet
    }

    @Override
    public void deleteVetVisit(User user, Pet pet, VetVisit vetVisit) {
        //Not implemented yet
    }
}

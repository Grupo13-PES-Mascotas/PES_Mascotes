package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.users.User;

/**
 * @author Xavier Campos
 */
public interface MedicalProfileManagerService {

    /**
     * Creates and adds the given vaccination to the given pet.
     * @param user        The owner of the pet
     * @param pet         The pet to whom the vaccination has to be added
     * @param vaccination The vaccination that has to be added to the pet
     */
    void createVaccination(User user, Pet pet, Vaccination vaccination);
}

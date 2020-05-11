package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xavier Campos
 */
public class MedicalProfileManagerAdapter implements MedicalProfileManagerService {
    @Override
    public void createVaccination(User user, Pet pet, Vaccination vaccination) {
        // Not implemented yet
    }

    @Override
    public List<Vaccination> findVaccinationsByPet(User user, Pet pet) {
        // Not implemented yet
        return new ArrayList<>();
    }

    @Override
    public void deleteVaccination(User user, Pet pet, Vaccination vaccination) {
        // Not implemented yet
    }

    @Override
    public void updateVaccinationKey(User user, Pet pet, String newDate, DateTime vaccinationDate) {
        // Not implemented yet
    }

    @Override
    public void updateVaccinationBody(User user, Pet pet, Vaccination vaccination) {
        // Not implemented yet
    }
}

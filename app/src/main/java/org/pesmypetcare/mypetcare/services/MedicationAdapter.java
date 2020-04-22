package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;

/**
 * @author Xavier Campos
 */
public class MedicationAdapter implements MedicationManagerService {
    @Override
    public void createMedication(User user, Pet pet, Medication medication) throws MedicationAlreadyExistingException {
        //Not implemented
    }

    @Override
    public void updateMedicationBody(User user, Pet pet, Medication medication) {
        //Not implemented
    }

    @Override
    public void updateMedicationDate(User user, Pet pet, String newDate, String oldDate) {
        //Not implemented
    }

    @Override
    public void deleteMedication(User user, Pet pet, Medication medication) {
        //Not implemented
    }

    @Override
    public void deleteMedicationsFromPet(User user, Pet pet) {
        //Not implemented
    }

    @Override
    public List<Medication> findMedicationsByPet(User user, Pet pet) {
        //Not implemented
        return null;
    }
}

package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xavier Campos
 */
public class StubMedicationService implements MedicationManagerService{
    private static List<Medication> medications;

    static {
        medications = new ArrayList<>();
        StubMedicationService.medications.add(new Medication("Capstar", 3, 2, 25,
            DateTime.Builder.buildDateString("2020-04-15")));
        StubMedicationService.medications.add(new Medication("Espiruvet", 1, 1, 14,
            DateTime.Builder.buildDateString("2020-01-12")));
        StubMedicationService.medications.add(new Medication("Effipro Duo", 1, 2, 7,
            DateTime.Builder.buildDateString("2018-11-02")));
        StubMedicationService.medications.add(new Medication("Prevender", 1, 0.5, 31,
            DateTime.Builder.buildDateString("2019-05-22")));
    }

    @Override
    public void createMedication(User user, Pet pet, Medication medication) throws MedicationAlreadyExistingException {
        for (Medication staticMed : medications) {
            if (staticMed == medication) {
                throw new MedicationAlreadyExistingException();
            }
        }
        medications.add(medication);
    }

    @Override
    public void updateMedicationBody(User user, Pet pet, Medication medication) {
        for (Medication staticMed : medications) {
            if (staticMed.getMedicationDate() == medication.getMedicationDate()) {
                staticMed.setMedicationName(medication.getMedicationName());
                staticMed.setMedicationQuantity(medication.getMedicationQuantity());
                staticMed.setMedicationFrequency(medication.getMedicationFrequency());
                staticMed.setMedicationDuration(medication.getMedicationDuration());
            }
        }
    }

    @Override
    public void updateMedicationDate(User user, Pet pet, String newDate, String oldDate) {
        for (Medication staticMed : medications) {
            if (staticMed.getMedicationDate().toString().equals(oldDate)) {
                staticMed.setMedicationDate(DateTime.Builder.buildFullString(newDate));
            }
        }
    }

    @Override
    public void deleteMedication(User user, Pet pet, Medication medication) {
        medications.remove(medication);
    }

    @Override
    public void deleteMedicationsFromPet(User user, Pet pet) {
        medications.clear();
    }

    @Override
    public List<Medication> findMedicationsByPet(User user, Pet pet) {
        return medications;
    }
}

package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.medication.Medication;
import org.pesmypetcare.mypetcare.features.pets.events.medication.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.medication.MedicationManagerService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xavier Campos
 */
public class StubMedicationService implements MedicationManagerService {
    private static List<Medication> medications;
    private static Medication currentMedication;
    private static final int MEDICATION_DURATION = 25;
    private static final int MEDICATION_DURATION1 = 14;
    private static final int MEDICATION_DURATION2 = 7;
    private static final int MEDICATION_DURATION3 = 31;
    private static final int MEDICATION_FREQUENCY = 5;

    static {
        medications = new ArrayList<>();
        StubMedicationService.medications.add(new Medication("Capstar", 2, 2, MEDICATION_DURATION,
            DateTime.Builder.buildDateString("2020-04-15")));
        StubMedicationService.medications.add(new Medication("Espiruvet", 1, 1, MEDICATION_DURATION1,
            DateTime.Builder.buildDateString("2020-01-12")));
        StubMedicationService.medications.add(new Medication("Effipro Duo", 1, 2, MEDICATION_DURATION2,
            DateTime.Builder.buildDateString("2018-11-02")));
        StubMedicationService.medications.add(new Medication("Prevender", 1, MEDICATION_FREQUENCY, MEDICATION_DURATION3,
            DateTime.Builder.buildDateString("2019-05-22")));
    }

    @Override
    public void createMedication(User user, Pet pet, Medication medication) throws MedicationAlreadyExistingException {
        for (Medication staticMed : medications) {
            if (staticMed.equals(medication)) {
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
        currentMedication = medication;
    }

    @Override
    public void updateMedicationKey(User user, Pet pet, String newDate, String oldDate, String oldName,
                                    String newName) {
        for (Medication staticMed : medications) {
            if (staticMed.getMedicationDate().toString().equals(oldDate)) {
                staticMed.setMedicationDate(DateTime.Builder.buildFullString(newDate));
                staticMed.setMedicationName(newName);
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

    /**
     * Returns the number of medications.
     * @return The number of medications
     */
    public int nMedications() {
        return medications.size();
    }

    /**
     * Returns the medication that is being updated.
     * @return The medication that is being updated
     */
    public static Medication getCurrentMedication() {
        return currentMedication;
    }
}

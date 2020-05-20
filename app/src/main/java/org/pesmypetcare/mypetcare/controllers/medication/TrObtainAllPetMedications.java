package org.pesmypetcare.mypetcare.controllers.medication;

import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.pets.events.medication.Medication;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.medication.MedicationManagerService;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos
 */
public class TrObtainAllPetMedications {
    private MedicationManagerService medicationManagerService;
    private User user;
    private Pet pet;
    private List<Medication> result;

    public TrObtainAllPetMedications(MedicationManagerService medicationManagerService) {
        this.medicationManagerService = medicationManagerService;
    }

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet from which we want to obtain all the medications.
     * @param pet The pet from which we want to obtain all the medications
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Getter of the medications of the pet.
     * @return The medications of the pet
     */
    public List<Medication> getResult() {
        return result;
    }

    /**
     * Method responsible for executing the transaction.
     */
    public void execute() throws ExecutionException, InterruptedException {
        result = medicationManagerService.findMedicationsByPet(user, pet);
        for (Event e:result) {
            pet.addEvent(e);
        }
    }
}

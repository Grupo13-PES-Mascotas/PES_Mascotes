package org.pesmypetcare.mypetcare.controllers.medication;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MedicationManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos
 */
public class TrNewPetMedication {
    private MedicationManagerService medicationManagerService;
    private User user;
    private Pet pet;
    private Medication medication;
    private boolean result;

    public TrNewPetMedication(MedicationManagerService medicationManagerService) {
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
     * Setter of the pet to whom the meal will be added.
     * @param pet The pet to whom the meal will be added.
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the medication that has to be added to the pet.
     * @param medication The medication that has to be added to the pet
     */
    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    /**
     * Getter of the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     */
    public void execute() throws MedicationAlreadyExistingException, ExecutionException, InterruptedException {
        result = false;
        if (medicationHasAlreadyBeenAdded()) {
            throw new MedicationAlreadyExistingException();
        }
        pet.addEvent(medication);
        medicationManagerService.createMedication(user, pet, medication);
        result = true;
    }

    /**
     * Method responsible for checking if the medication has already been added to the pet.
     * @return True if the medication has already been added or false otherwise
     */
    private boolean medicationHasAlreadyBeenAdded() {
        boolean found = false;
        for (Event e : pet.getMedicationEvents()) {
            if (e.getDateTime().compareTo(medication.getDateTime()) == 0
                && ((Medication) e).getMedicationName().equals(medication.getMedicationName())) {
                found = true;
                break;
            }
        }
        return found;
    }
}

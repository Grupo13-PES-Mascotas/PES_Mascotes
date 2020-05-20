package org.pesmypetcare.mypetcare.controllers.medication;

import org.pesmypetcare.mypetcare.features.pets.events.medication.Medication;
import org.pesmypetcare.mypetcare.features.pets.events.medication.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.medication.MedicationManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos
 */
public class TrUpdateMedication {
    private MedicationManagerService medicationManagerService;
    private User user;
    private Pet pet;
    private Medication medication;
    private String newDate;
    private boolean updatesDate;
    private String newName;
    private boolean updatesName;

    public TrUpdateMedication(MedicationManagerService medicationManagerService) {
        this.medicationManagerService = medicationManagerService;
        this.updatesDate = false;
        this.updatesName = false;
    }

    /**
     * Setter if the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet from which we want to update the meal.
     * @param pet The setter of the pet from which we want to update the meal
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of updated medication.
     * @param medication The updated medication
     */
    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    /**
     * Setter of the new date of the medication.
     * @param newDate The new date of the medication
     */
    public void setNewDate(String newDate) {
        this.updatesDate = true;
        this.newDate = newDate;
    }

    /**
     * Setter of the new name of the medication.
     * @param newName The new name of the medication
     */
    public void setNewName(String newName) {
        this.updatesName = true;
        this.newName = newName;
    }

    /**
     * Execute the transaction.
     */
    public void execute() throws InterruptedException, ExecutionException, MedicationAlreadyExistingException {
        medicationManagerService.updateMedicationBody(user, pet, medication);
        if (updatesDate || updatesName) {
            if (!updatesDate) {
                newDate = medication.getDateTime().toString();
            }
            if (!updatesName) {
                newName = medication.getMedicationName();
            }
            medicationManagerService.updateMedicationKey(user, pet, newDate, medication.getDateTime().toString(),
                newName, medication.getMedicationName());
        }
    }
}

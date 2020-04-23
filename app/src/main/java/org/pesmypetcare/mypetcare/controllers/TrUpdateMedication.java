package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MedicationManagerService;

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

    public TrUpdateMedication(MedicationManagerService medicationManagerService) {
        this.medicationManagerService = medicationManagerService;
        this.updatesDate = false;
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
     * Execute the transaction.
     */
    public void execute() {
        medicationManagerService.updateMedicationBody(user, pet, medication);
        if (updatesDate) {
            medicationManagerService.updateMedicationDate(user, pet, newDate, medication.getDateTime().toString());
        }
    }
}

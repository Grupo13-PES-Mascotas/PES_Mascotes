package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MedicationManagerService;

/**
 * @author Xavier Campos
 */
public class TrDeleteMedication {
    private MedicationManagerService medicationManagerService;
    private User user;
    private Pet pet;
    private Medication medication;

    public TrDeleteMedication(MedicationManagerService medicationManagerService) {
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
     * Setter of the pet from which we want to delete the meal.
     * @param pet The pet from which we want to delete the meal
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the medication that has to be deleted.
     * @param medication The medication that has to be deleted
     */
    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    /**
     * Executes the transaction.
     */
    public void execute() {
        medicationManagerService.deleteMedication(user, pet, medication);
        pet.deleteEvent(medication);
    }
}

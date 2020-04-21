package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;

/**
 * @author Xavier Campos
 */
public interface MedicationManagerService {

    /**
     * Method that creates a new medication and assigns it to the indicated pet of the indicated user.
     * @param user The owner of the pet
     * @param pet The pet to which we want to add the medication
     * @param medication The medication that has to be added to the pet
     */
    void createMedication(User user, Pet pet, Medication medication) throws MedicationAlreadyExistingException;

    /**
     * Method that updates the body of the indicated medication.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the medication
     * @param medication The updated medication
     */
    void updateMedicationBody(User user, Pet pet, Medication medication);

    /**
     * Method that updates the date of the indicated medication.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the medication
     * @param newDate The new date of the medication
     * @param oldDate The old date of the medication
     */
    void updateMedicationDate(User user, Pet pet, String newDate, String oldDate);

    /**
     * Method that removes a medication from the indicated pet.
     * @param user The owner of the pet
     * @param pet The pet from which we want to delete the medication
     * @param medication The medication that has to be deleted from the pet
     */
    void deleteMedication(User user, Pet pet, Medication medication);

    /**
     * Method that deletes all the medications from the indicated pet.
     * @param user The owner of the pet.
     * @param pet The pet from which we want to delete all the medications
     */
    void deleteMedicationsFromPet(User user, Pet pet);

    /**
     * Method that obtains all the medications of the indicated pet.
     * @param user The owner of the pet
     * @param pet The pet from which we want to obtain all the medications
     * @return A list containing all the medications of the pet
     */
    List<Medication> findMedicationsByPet(User user, Pet pet);
}

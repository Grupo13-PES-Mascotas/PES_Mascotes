package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Illness;
import org.pesmypetcare.mypetcare.features.pets.IllnessAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.pets.VaccinationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos
 */
public interface MedicalProfileManagerService {

    /**
     * Creates and adds the given vaccination to the given pet.
     * @param user The owner of the pet
     * @param pet  The pet to whom the vaccination has to be added
     * @param vaccination The vaccination that has to be added to the pet
     */
    void createVaccination(User user, Pet pet, Vaccination vaccination) throws VaccinationAlreadyExistingException, ExecutionException, InterruptedException;

    /**
     * Obtains all the vaccinations of the pet.
     * @param user The owner of the pet
     * @param pet The pet from whom we want to obtain all the vaccinations
     * @return A list containing all the vaccinations of the pet
     */
    List<Vaccination> findVaccinationsByPet(User user, Pet pet) throws ExecutionException, InterruptedException;

    /**
     * Deletes a vaccination from the given pet.
     * @param user The owner of the pet
     * @param pet The pet from whom we want to delete all the vaccinations
     * @param vaccination The vaccination that has to be deleted
     */
    void deleteVaccination(User user, Pet pet, Vaccination vaccination) throws ExecutionException, InterruptedException;

    /**
     * Updates the vaccination key.
     * @param user The owner of the pet
     * @param pet The pet from whom we want to update the vaccination key
     * @param newDate The new date that has to be set to the vaccination
     * @param vaccinationDate The new old date of the vaccination
     */
    void updateVaccinationKey(User user, Pet pet, String newDate, DateTime vaccinationDate) throws ExecutionException, InterruptedException;

    /**
     * Updates the vaccination body.
     * @param user The owner of the pet
     * @param pet The pet from whom we want to update the body
     * @param vaccination The vaccination that has to be updated
     */
    void updateVaccinationBody(User user, Pet pet, Vaccination vaccination) throws ExecutionException, InterruptedException;

    /**
     * Creates and adds the given illness to the given pet.
     * @param user The owner of the pet
     * @param pet The pet to whom the illness has to be added
     * @param illness The illness that has to be added to the pet
     */
    void createIllness(User user, Pet pet, Illness illness) throws IllnessAlreadyExistingException;
}

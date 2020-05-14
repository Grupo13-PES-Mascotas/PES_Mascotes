package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.pets.VaccinationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MedicalProfileManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos
 */
public class TrAddNewVaccination {
    private MedicalProfileManagerService medicalProfileManagerService;
    private User user;
    private Pet pet;
    private Vaccination vaccination;
    private boolean result;

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet to whom the vaccine has to be added.
     * @param pet The pet to whom the vaccine has to be added
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the vaccination that has to be added to the pet.
     * @param vaccination The vaccination that has to be added to the pet
     */
    public void setVaccination(Vaccination vaccination) {
        this.vaccination = vaccination;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the transaction was successful or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    public void execute() throws NotPetOwnerException, VaccinationAlreadyExistingException, ExecutionException,
        InterruptedException {
        result = false;
        if (!user.getUsername().equals(pet.getOwner().getUsername())) {
            throw new NotPetOwnerException();
        }
        medicalProfileManagerService.createVaccination(user, pet, vaccination);
        pet.addEvent(vaccination);
        result = true;
    }
}

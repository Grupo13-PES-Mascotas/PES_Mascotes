package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MedicalProfileManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos
 */
public class TrUpdatePetVaccination {
    private MedicalProfileManagerService medicalProfileManagerService;
    private User user;
    private Pet pet;
    private Vaccination vaccination;
    private String newDate;
    private boolean updatesDate;

    public TrUpdatePetVaccination(MedicalProfileManagerService medicalProfileManagerService) {
        this.medicalProfileManagerService = medicalProfileManagerService;
        this.updatesDate = false;
    }

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet from which the vaccination has to be updated.
     * @param pet The pet from which the vaccination has to be updated
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the updated vaccination.
     * @param vaccination The updated vaccination
     */
    public void setVaccination(Vaccination vaccination) {
        this.vaccination = vaccination;
    }

    /**
     * Setter of the new date of the vaccination.
     * @param newDate The new date of the vaccination
     */
    public void setNewDate(String newDate) {
        this.newDate = newDate;
        this.updatesDate = true;
    }

    /**
     * Executes the transaction.
     */
    public void execute() throws NotPetOwnerException, ExecutionException, InterruptedException {
        if (!user.getUsername().equals(pet.getOwner().getUsername())) {
            throw new NotPetOwnerException();
        }
        medicalProfileManagerService.updateVaccinationBody(user, pet, vaccination);
        if (updatesDate) {
            medicalProfileManagerService.updateVaccinationKey(user, pet, newDate, vaccination.getVaccinationDate());
        }
    }
}

package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MedicalProfileManagerService;

/**
 * @author Xavier Campos
 */
public class TrDeletePetVaccination {
    private MedicalProfileManagerService medicalProfileManagerService;
    private User user;
    private Pet pet;
    private Vaccination vaccination;
    private boolean result;

    public TrDeletePetVaccination(MedicalProfileManagerService medicalProfileManagerService) {
        this.medicalProfileManagerService = medicalProfileManagerService;
    }

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet from where the vaccination has to be deleted.
     * @param pet The pet from where the vaccination has to be deleted
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the vaccination that has to be deleted from the pet.
     * @param vaccination The vaccination that has to be deleted from the pet
     */
    public void setVaccination(Vaccination vaccination) {
        this.vaccination = vaccination;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the delete was successful or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     */
    public void execute() {
        result = false;
        medicalProfileManagerService.deleteVaccination(user, pet, vaccination);
        pet.deleteEvent(vaccination);
        result = true;
    }
}

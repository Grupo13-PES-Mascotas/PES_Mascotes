package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.GoogleCalendarService;
import org.pesmypetcare.mypetcare.services.MedicalProfileManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos
 */
public class TrDeletePetVaccination {
    private MedicalProfileManagerService medicalProfileManagerService;
    private GoogleCalendarService googleCalendarService;
    private User user;
    private Pet pet;
    private Vaccination vaccination;
    private boolean result;

    public TrDeletePetVaccination(MedicalProfileManagerService medicalProfileManagerService,
                                  GoogleCalendarService googleCalendarService) {
        this.medicalProfileManagerService = medicalProfileManagerService;
        this.googleCalendarService = googleCalendarService;
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
    public void execute() throws NotPetOwnerException, ExecutionException, InterruptedException {
        result = false;
        if (!user.getUsername().equals(pet.getOwner().getUsername())) {
            throw new NotPetOwnerException();
        }
        medicalProfileManagerService.deleteVaccination(user, pet, vaccination);
        pet.deleteEvent(vaccination);
        googleCalendarService.deleteEvent(pet, vaccination);
        result = true;
    }
}

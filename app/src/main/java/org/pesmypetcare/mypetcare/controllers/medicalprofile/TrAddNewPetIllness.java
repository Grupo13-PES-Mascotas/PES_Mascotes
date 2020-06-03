package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.illness.Illness;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.illness.IllnessAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.googlecalendar.GoogleCalendarService;
import org.pesmypetcare.mypetcare.services.medicalprofile.MedicalProfileManagerService;

/**
 * @author Xavier Campos
 */
public class TrAddNewPetIllness {
    private MedicalProfileManagerService medicalProfileManagerService;
    private GoogleCalendarService googleCalendarService;
    private User user;
    private Pet pet;
    private Illness illness;
    private boolean result;

    public TrAddNewPetIllness(MedicalProfileManagerService medicalProfileManagerService,
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
     * Setter of the pet to whom the illness has to be added.
     * @param pet The pet to whom the illness has to be added
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the illness that has to be added to the pet.
     * @param illness The illness that has to be added to the pet
     */
    public void setIllness(Illness illness) {
        this.illness = illness;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the adding was successful or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     * @throws NotPetOwnerException The user is not the owner of the pet
     * @throws IllnessAlreadyExistingException The illness already exists
     */
    public void execute() throws NotPetOwnerException, IllnessAlreadyExistingException {
        result = false;
        if (!user.getUsername().equals(pet.getOwner().getUsername())) {
            throw new NotPetOwnerException();
        }
        medicalProfileManagerService.createIllness(user, pet, illness);
        pet.addEvent(illness);
        googleCalendarService.registerNewEvent(pet, illness);
        result = true;
    }
}

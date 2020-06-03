package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.illness.Illness;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.medicalprofile.MedicalProfileManagerService;

/**
 * @author Xavier Campos
 */
public class TrUpdatePetIllness {
    private MedicalProfileManagerService medicalProfileManagerService;
    private User user;
    private Pet pet;
    private Illness illness;
    private String newDate;
    private boolean updatesDate;

    public TrUpdatePetIllness(MedicalProfileManagerService medicalProfileManagerService) {
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
     * Setter of the pet from which the illness has to be updated.
     * @param pet The pet from which the illness has to be updated
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the updated illness.
     * @param illness The updated illness
     */
    public void setIllness(Illness illness) {
        this.illness = illness;
    }

    /**
     * Setter of the new date of the illness.
     * @param newDate The new date of the illness
     */
    public void setNewDate(String newDate) {
        this.newDate = newDate;
        this.updatesDate = true;
    }

    /**
     * Executes the transaction.
     */
    public void execute() throws NotPetOwnerException {
        if (!user.getUsername().equals(pet.getOwner().getUsername())) {
            throw new NotPetOwnerException();
        }
        medicalProfileManagerService.updateIllnessBody(user, pet, illness);
        if (updatesDate) {
            medicalProfileManagerService.updateIllnessKey(user, pet, newDate, illness.getDateTime());
        }
    }
}

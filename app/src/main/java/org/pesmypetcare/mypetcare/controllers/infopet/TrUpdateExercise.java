package org.pesmypetcare.mypetcare.controllers.infopet;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

/**
 * @author Albert Pinto
 */
public class TrUpdateExercise {
    private PetManagerService petManagerService;
    
    public TrUpdateExercise(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
    }

    public void setUser(User johnSmith) {
    }

    public void setPet(Pet pet) {
    }

    public void setExerciseName(String frisbee) {
    }

    public void setExerciseDescription(String playing_at_the_beach) {
    }

    public void setStartDateTime(DateTime buildFullString) {
    }

    public void setEndDateTime(DateTime buildFullString) {
    }

    public void execute() throws NotPetOwnerException {
        throw new NotPetOwnerException();
    }
}

package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Illness;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MedicalProfileManagerService;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Enric Hernando
 */
public class TrObtainAllPetIllness {
    private MedicalProfileManagerService medicalProfileManagerService;
    private User user;
    private Pet pet;
    private List<Illness> result;

    public TrObtainAllPetIllness(MedicalProfileManagerService medicalProfileManagerService) {
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
     * Setter of the pet from whom we have to obtain all the illness.
     * @param pet The pet from whom we have to obtain all the illness
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Getter of the result of the transaction.
     * @return A list containing all the illnesses of the pet
     */
    public List<Illness> getResult() {
        return result;
    }

    /**
     * Executes the transaction.
     * @throws NotPetOwnerException Exception thrown when the user is not the owner of the pet
     * @throws ExecutionException Exception thrown when there is an exception during the execution
     * @throws InterruptedException Exception thrown when there is an interruption during the exception
     */
    public void execute() throws NotPetOwnerException, ExecutionException, InterruptedException {
        if (!user.getUsername().equals(pet.getOwner().getUsername())) {
            throw new NotPetOwnerException();
        }
        result = medicalProfileManagerService.findIllnessesByPet(user, pet);
        for (Event e : result) {
            pet.addEvent(e);
        }
    }
}

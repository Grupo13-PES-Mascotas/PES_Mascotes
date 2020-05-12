package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.MedicalProfileManagerService;

import java.util.List;

/**
 * @author Xavier Campos
 */
public class TrObtainAllPetVaccinations {
    private MedicalProfileManagerService medicalProfileManagerService;
    private User user;
    private Pet pet;
    private List<Vaccination> result;

    public TrObtainAllPetVaccinations(MedicalProfileManagerService medicalProfileManagerService) {
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
     * Setter of the pet from whom we have to obtain all the vaccinations.
     * @param pet The pet from whom we have to obtain all the vaccinations
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Getter of the result of the transaction.
     * @return A list containing all the vaccinations of the pet
     */
    public List<Vaccination> getResult() {
        return result;
    }

    public void execute() throws NotPetOwnerException {
        if(!user.getUsername().equals(pet.getOwner().getUsername())) {
            throw new NotPetOwnerException();
        }
        result = medicalProfileManagerService.findVaccinationsByPet(user, pet);
        for (Event e : result) {
            pet.addEvent(e);
        }
    }
}

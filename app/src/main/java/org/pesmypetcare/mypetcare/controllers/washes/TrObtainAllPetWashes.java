package org.pesmypetcare.mypetcare.controllers.washes;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.pets.events.wash.Wash;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.wash.WashManagerService;

import java.util.List;

/**
 * @author Enric Hernando
 */
public class TrObtainAllPetWashes {
    private WashManagerService washManagerService;
    private User user;
    private Pet pet;
    private List<Wash> result;

    public TrObtainAllPetWashes(WashManagerService washManagerService) {
        this.washManagerService = washManagerService;
    }

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the pet from which we want to obtain all the washes.
     * @param pet The pet from which we want to obtain all the washes
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Getter of the wash of the pet.
     * @return The wash of the pet
     */
    public List<Wash> getResult() {
        return result;
    }

    /**
     * Method responsible for executing the transaction.
     */
    public void execute() {
        result = washManagerService.findWashesByPet(user, pet);
        for (Event e:result) {
            pet.addEvent(e);
        }
    }
}

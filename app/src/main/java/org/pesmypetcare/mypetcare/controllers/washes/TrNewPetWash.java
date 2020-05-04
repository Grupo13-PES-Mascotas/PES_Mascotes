package org.pesmypetcare.mypetcare.controllers.washes;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Wash;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.WashManagerService;

/**
 * @author Enric Hernando
 */
public class TrNewPetWash {
    private WashManagerService washManagerService;
    private User user;
    private Pet pet;
    private Wash wash;
    private Boolean result;

    public TrNewPetWash(WashManagerService washManagerService) {
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
     * Setter of the pet to whom the meal will be added.
     * @param pet The pet to whom the meal will be added
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Setter of the wash that has to be added to the pet.
     * @param wash The wash that has to be added to the pet
     */
    public void setMeal(Wash wash) {
        this.wash = wash;
    }

    /**
     * Getter of the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Execute the transaction.
     */
    public void execute() throws MealAlreadyExistingException {
        result = false;
        if (mealHasAlreadyBeenAdded()) {
            throw new MealAlreadyExistingException();
        }
        pet.addEvent(wash);
        //washManagerService.createWash(user, pet, wash);
        result = true;
    }

    /**
     * Method responsible for checking if the wash has already been added to the pet.
     * @return True if the wash has already been added or false otherwise
     */
    private boolean mealHasAlreadyBeenAdded() {
        for (Event e : pet.getWashEvents()) {
            Wash m = (Wash) e;
            if (m.getDateTime().compareTo(wash.getWashDate()) == 0) {
                return true;
            }
        }
        return false;
    }
}

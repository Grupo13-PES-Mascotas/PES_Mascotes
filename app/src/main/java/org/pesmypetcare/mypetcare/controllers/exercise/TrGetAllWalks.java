package org.pesmypetcare.mypetcare.controllers.exercise;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Walk;
import org.pesmypetcare.mypetcare.features.pets.WalkPets;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Clemente
 */
public class TrGetAllWalks {
    private User user;
    private List<WalkPets> result;

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Execute the transaction.
     */
    public void execute() {
        result = new ArrayList<>();

        for (Pet pet : user.getPets()) {
            getWalksForPet(pet);
        }
    }

    /**
     * Get the walks for the pet.
     * @param pet The pet
     */
    private void getWalksForPet(Pet pet) {
        List<Event> events = pet.getEventsByClass(Walk.class);

        for (Event event : events) {
            WalkPets actualWalkPets = new WalkPets((Walk) event);
            int index = result.indexOf(actualWalkPets);

            if (index >= 0) {
                result.get(index).addPet(pet);
            } else {
                actualWalkPets.addPet(pet);
                result.add(actualWalkPets);
            }
        }
    }

    /**
     * Get the result.
     * @return The result
     */
    public List<WalkPets> getResult() {
        return result;
    }
}

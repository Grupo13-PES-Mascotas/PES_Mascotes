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
    private List<Walk> exercises;
    private List<WalkPets> result;

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }

    public void execute() {
        result = new ArrayList<>();

        for (Pet pet : user.getPets()) {
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

        /*ArrayList<Pet> pets = user.getPets();
        for (Pet pet : pets) {
            List<Event> events = pet.getEventsByClass(Walk.class);
            for (Event e : events) {
                WalkPets walk = new WalkPets((Walk) e);
                walk.addPet(pet);
                if (!result.contains(walk)) {
                    result.add(walk);
                }
            }
        }*/
    }

    public List<WalkPets> getResult() { return result; }

}

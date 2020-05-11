package org.pesmypetcare.mypetcare.controllers.exercise;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Walk;
import org.pesmypetcare.mypetcare.features.pets.WalkPets;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Clemente
 */
public class TrGetExercises {
    private PetManagerService petManagerService;
    private User user;
    List<Walk> exercises;
    List<WalkPets> result;

    public TrGetExercises(PetManagerService petManagerService) {
        this.petManagerService = petManagerService;
        result = new ArrayList<WalkPets>();
    }

    /**
     * Setter of the owner of the pet.
     * @param user The owner of the pet
     */
    public void setUser(User user) {
        this.user = user;
    }



    public void execute() {
        ArrayList<Pet> pets = user.getPets();
        for (Pet pet : pets) {
            List<Event> events = pet.getEventsByClass(Walk.class);
            for (Event e : events) {
                WalkPets walk = new WalkPets((Walk) e);
                walk.addPet(pet);
                if (!result.contains(walk)) {
                    result.add(walk);
                }
            }
        }
    }

    public List<WalkPets> getResult() { return result; }

}

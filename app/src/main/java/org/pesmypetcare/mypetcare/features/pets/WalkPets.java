package org.pesmypetcare.mypetcare.features.pets;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Albert Pinto
 */
public class WalkPets {
    private Walk walk;
    private List<Pet> pets;

    public WalkPets(Walk walk) {
        this.walk = walk;
        this.pets = new ArrayList<>();
    }

    /**
     * Add a pet to the pet walk
     * @param pet The pet to add
     */
    public void addPet(Pet pet) {
        pets.add(pet);
    }

    /**
     * Get the walk.
     * @return The walk
     */
    public Walk getWalk() {
        return walk;
    }

    /**
     * Get the pets.
     * @return The pets
     */
    public List<Pet> getPets() {
        return pets;
    }
}

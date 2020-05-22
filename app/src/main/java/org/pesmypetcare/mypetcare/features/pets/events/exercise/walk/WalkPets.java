package org.pesmypetcare.mypetcare.features.pets.events.exercise.walk;

import androidx.annotation.NonNull;

import org.pesmypetcare.mypetcare.features.pets.Pet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * Add a pet to the pet walk.
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

    @NonNull
    public String toString() {
        StringBuilder petNames = new StringBuilder();

        for (Pet pet : pets) {
            petNames.append(pet.getName()).append(", ");
        }

        petNames.deleteCharAt(petNames.length() - 1);
        petNames.deleteCharAt(petNames.length() - 1);

        return "{" + walk.getName() + ", {" + petNames.toString() + "}}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WalkPets walkPets = (WalkPets) o;
        return Objects.equals(walk, walkPets.walk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walk);
    }
}

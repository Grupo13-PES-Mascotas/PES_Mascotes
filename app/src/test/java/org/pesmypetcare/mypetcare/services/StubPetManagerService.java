package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StubPetManagerService implements PetManagerService {
    private Map<String, ArrayList<Pet>> data;

    public StubPetManagerService() {
        this.data = new HashMap<>();
    }

    @Override
    public void updatePet(Pet pet) {
        if (pet.getPreviousName() != null) {
            Objects.requireNonNull(data.get(pet.getOwner().getUsername())).remove(new Pet(pet.getPreviousName()));
        } else {
            Objects.requireNonNull(data.get(pet.getOwner().getUsername())).remove(pet);
        }
        this.registerNewPet(pet.getOwner().getUsername(), pet);
    }

    @Override
    public boolean registerNewPet(String username, Pet pet) {
        data.putIfAbsent(username, new ArrayList<>());
        Objects.requireNonNull(data.get(username)).add(pet);

        return true;
    }
}

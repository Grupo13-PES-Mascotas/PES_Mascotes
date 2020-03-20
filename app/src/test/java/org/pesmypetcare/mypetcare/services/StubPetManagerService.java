package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;

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
    public boolean registerNewPet(String username, Pet pet) {
        data.putIfAbsent(username, new ArrayList<>());

        Objects.requireNonNull(data.get(username)).add(pet);
        return true;
    }
}

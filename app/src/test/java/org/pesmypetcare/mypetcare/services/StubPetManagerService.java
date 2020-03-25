package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StubPetManagerService implements PetManagerService {
    private static final String JOHN_DOE = "johnDoe";
    private static final String DINKY = "Dinky";
    private Map<String, ArrayList<Pet>> data;

    public StubPetManagerService() {
        this.data = new HashMap<>();
        this.data.put(JOHN_DOE, new ArrayList<>());
        Objects.requireNonNull(this.data.get(JOHN_DOE)).add(new Pet(DINKY));
    }

    @Override
    public void updatePet(Pet pet) {
        if (pet.getPreviousName() != null) {
            Objects.requireNonNull(data.get(pet.getOwner().getUsername())).remove(new Pet(pet.getPreviousName()));
        } else if (Objects.requireNonNull(data.containsKey(pet.getOwner().getUsername()))) {
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

    @Override
    public void updatePetImage(String username, String petName, Bitmap newPetImage) {
        ArrayList<Pet> pets = data.get(username);
        int index = Objects.requireNonNull(pets).indexOf(new Pet(petName));
        pets.get(index).setProfileImage(newPetImage);
    }
    @Override
    public void deletePet(Pet pet, String username) {
        ArrayList<Pet> pets = data.get(username);
        assert pets != null;
        pets.remove(pet);
    }

    @Override
    public void deleteUser(User user) {
        data.remove(user.getUsername());
    }

    @Override
    public ArrayList<Pet> findPetsByOwner(String username) {
        return data.get(username);
    }
}

package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        this.registerNewPet(pet.getOwner(), pet);
    }

    @Override
    public boolean registerNewPet(User user, Pet pet) {
        data.putIfAbsent(user.getUsername(), new ArrayList<>());
        Objects.requireNonNull(data.get(user.getUsername())).add(pet);

        return true;
    }

    @Override
    public void updatePetImage(User user, Pet pet, Bitmap newPetImage) {
        ArrayList<Pet> pets = data.get(user.getUsername());
        int index = Objects.requireNonNull(pets).indexOf(pet);
        pets.get(index).setProfileImage(newPetImage);
    }
    @Override
    public void deletePet(Pet pet, User user) {
        ArrayList<Pet> pets = data.get(user.getUsername());
        assert pets != null;
        pets.remove(pet);
    }

    @Override
    public void deletePetsFromUser(User user) {
        data.remove(user.getUsername());
    }

    @Override
    public List<Pet> findPetsByOwner(User user) {
        return data.get(user.getUsername());
    }
}

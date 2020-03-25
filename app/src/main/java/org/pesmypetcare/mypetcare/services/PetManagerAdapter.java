package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.ArrayList;
import java.util.List;

public class PetManagerAdapter implements PetManagerService {
    @Override
    public void updatePet(Pet pet) {
        String name = pet.getName();
        String ownerUsername = pet.getOwner().getUsername();

        ServiceLocator.getInstance().getPetManagerService().updateSex(ownerUsername, name, pet.getGender().toString());
        ServiceLocator.getInstance().getPetManagerService().updateBirthday(ownerUsername, name, pet.getBirthDate());
        ServiceLocator.getInstance().getPetManagerService().updateWeight(ownerUsername, name, pet.getWeight());
        ServiceLocator.getInstance().getPetManagerService().updateRace(ownerUsername, name, pet.getBreed());
    }

    @Override
    public boolean registerNewPet(String username, Pet pet) throws PetAlreadyExistingException {
        ServiceLocator.getInstance().getPetManagerService().signUpPet(pet.getOwner().getUsername(), pet.getName(),
            pet.getGender().toString(), pet.getBreed(), pet.getBirthDate(), pet.getWeight());
        return true;
    }

    @Override
    public void updatePetImage(String username, String petName, Bitmap newPetImage) {
        // Not implemented yet
    }

    @Override
    public void deletePet(Pet pet, String username) {
        ServiceLocator.getInstance().getPetManagerService().deletePet(username, pet.getName());
    }

    @Override
    public void deletePetsFromUser(User user) {
        ArrayList<Pet> pets = user.getPets();

        for (Pet pet : pets) {
            ServiceLocator.getInstance().getPetManagerService().deletePet(user.getUsername(), pet.getName());
        }
    }

    @Override
    public List<Pet> findPetsByOwner(String username) {
        return new ArrayList<>();
    }
}

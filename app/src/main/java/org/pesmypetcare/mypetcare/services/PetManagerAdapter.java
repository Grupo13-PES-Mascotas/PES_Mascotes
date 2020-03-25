package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;

public class PetManagerAdapter implements PetManagerService {
    @Override
    public void updatePet(Pet pet) {

    }

    @Override
    public boolean registerNewPet(String username, Pet pet) throws PetAlreadyExistingException {
        return false;
    }

    @Override
    public void updatePetImage(String username, String petName, Bitmap newPetImage) {

    }

    @Override
    public void deletePet(Pet pet, String username) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public List<Pet> findPetsByOwner(String username) {
        return null;
    }
}

package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;

public interface PetManagerService {
  
    void updatePet(Pet pet);
  
    boolean registerNewPet(String username, Pet pet) throws PetAlreadyExistingException;

    void updatePetImage(String username, String petName, Bitmap newPetImage);
}

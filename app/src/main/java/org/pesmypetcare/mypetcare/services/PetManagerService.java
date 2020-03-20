package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;

public interface PetManagerService {

    void updatePet(Pet pet);
    boolean registerNewPet(String username, Pet pet);
}

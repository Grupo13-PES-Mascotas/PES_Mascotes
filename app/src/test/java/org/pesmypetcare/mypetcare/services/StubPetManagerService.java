package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StubPetManagerService implements PetManagerService {
    private Map<String, ArrayList<Pet>> data;

    public StubPetManagerService() {
        this.data = new HashMap<>();
    }
}

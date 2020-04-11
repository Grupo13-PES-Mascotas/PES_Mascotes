package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

public class TestTrAddNewStatistic {
    private Pet pet;

    @Before
    public void setUp() throws PetRepeatException {
        pet = getLinuxPet();
    }

    private Pet getLinuxPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Linux");
        pet.setGender(GenderType.Male);
        pet.setBirthDate("2 MAR 2020");
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }
}

package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestTrAddNewWashFrequency {
    private User user;
    private Pet pet;
    private TrAddNewWashFrequency trAddNewWashFrequency;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet();

        user.addPet(pet);
        trAddNewWashFrequency = new TrAddNewWashFrequency(new StubPetManagerService());
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotAddWeightToNonOwnerPet() throws NotPetOwnerException {
        trAddNewWashFrequency.setUser(new User("johnSmith", "johnSmith@gmail.com", "5678"));
        trAddNewWashFrequency.setPet(pet);
        trAddNewWashFrequency.setNewWashFrequency(2);
        trAddNewWashFrequency.execute();
    }

    @Test
    public void shouldAddWashFrequency() throws NotPetOwnerException {
        trAddNewWashFrequency.setUser(user);
        trAddNewWashFrequency.setPet(pet);
        trAddNewWashFrequency.setNewWashFrequency(2);
        trAddNewWashFrequency.execute();

        assertEquals("Should add wash frequency", 2, pet.getWashFrequency());
    }

    @Test
    public void shouldOnlyHaveOneWashFrequencyPerDay() throws NotPetOwnerException {
        trAddNewWashFrequency.setUser(user);
        trAddNewWashFrequency.setPet(pet);
        trAddNewWashFrequency.setNewWashFrequency(2);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        trAddNewWashFrequency.setNewWashFrequency(5);
        trAddNewWashFrequency.execute();

        assertEquals("Should only have one wash frequency per day", 1, pet.getHealthInfo().getWashFrequency().size());
    }

    private Pet getDinkyPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Dinky");
        pet.setGender(GenderType.Female);
        pet.setBirthDate("2 MAR 2020");
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }
}

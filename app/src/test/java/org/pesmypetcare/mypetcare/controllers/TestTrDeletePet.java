package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMealManagerService;
import org.pesmypetcare.mypetcare.services.StubMedicationService;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertFalse;

public class TestTrDeletePet {
    private User user;
    private Pet pet;
    private TrDeletePet trDeletePet;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet();
        user.addPet(pet);
        trDeletePet = new TrDeletePet(new StubPetManagerService(), new StubMealManagerService(),
            new StubMedicationService());
    }

    @Test(expected = UserIsNotOwnerException.class)
    public void shouldNotDeletePetIfNotOwner() throws UserIsNotOwnerException, ExecutionException,
        InterruptedException {
        User user2 = new User("Albert", "albert69@gmail.com", "6969");
        trDeletePet.setUser(user2);
        trDeletePet.setPet(pet);
        trDeletePet.execute();
    }

    @Test
    public void shouldDeletePet() throws UserIsNotOwnerException, ExecutionException, InterruptedException {
        trDeletePet.setUser(user);
        trDeletePet.setPet(pet);
        trDeletePet.execute();
        assertFalse("Should delete pet", user.getPets().contains(pet));
    }

    private Pet getDinkyPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Dinky");
        pet.setGender(GenderType.Male);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }
}

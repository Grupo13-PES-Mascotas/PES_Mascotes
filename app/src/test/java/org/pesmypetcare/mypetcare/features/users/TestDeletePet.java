package org.pesmypetcare.mypetcare.features.users;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.TrDeletePet;
import org.pesmypetcare.mypetcare.features.pets.Gender;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;

import static org.junit.Assert.assertFalse;

public class TestDeletePet {
    private User user;
    private Pet pet;
    private TrDeletePet trDeletePet;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "albert69@gmail.com", "123456");
        pet = getDinkyPet();
        user.addPet(pet);
        trDeletePet = new TrDeletePet(new StubPetManagerService());
    }

    @Test(expected = UserIsNotOwnerException.class)
    public void shouldNotDeletePetIfNotOwner() throws UserIsNotOwnerException {
        User user2 = new User("Albert", "albert69@gmail.com", "1234");
        trDeletePet.setUser(user2);
        trDeletePet.setPet(pet);
        trDeletePet.execute();
    }

    @Test
    public void shouldDeletePet() throws UserIsNotOwnerException {
        trDeletePet.setUser(user);
        trDeletePet.setPet(pet);
        trDeletePet.execute();
        assertFalse("Should delete pet", user.getPets().contains(pet));
    }

    private Pet getDinkyPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Dinky");
        pet.setGender(Gender.MALE);
        pet.setBirthDate("2 MAR 2020");
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }
}

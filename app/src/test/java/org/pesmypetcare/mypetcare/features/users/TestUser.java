package org.pesmypetcare.mypetcare.features.users;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.TrRegisterNewPet;
import org.pesmypetcare.mypetcare.features.pets.Gender;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;

import static org.junit.Assert.assertTrue;

public class TestUser {
    private User user;
    private TrRegisterNewPet trRegisterNewPet;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        trRegisterNewPet = new TrRegisterNewPet(new StubPetManagerService());
    }

    @Test
    public void shouldAddOnePet() throws PetRepeatException {
        Pet pet = getLinuxPet();
        user.addPet(pet);
        assertTrue("should add one pet", user.getPets().contains(pet));
    }

    @Test
    public void shouldCommunicateWithService() throws PetAlreadyExistingException, PetRepeatException {
        Pet pet = getLinuxPet();
        trRegisterNewPet.setUser(user);
        trRegisterNewPet.setPet(pet);
        trRegisterNewPet.execute();
        boolean addingResult = trRegisterNewPet.isResult();

        assertTrue("should communicate with service to add a pet", addingResult);
    }

    @Test(expected = PetAlreadyExistingException.class)
    public void shouldNotAddPetIfExisting() throws PetAlreadyExistingException, PetRepeatException {
        Pet pet = getLinuxPet();
        trRegisterNewPet.setUser(user);
        trRegisterNewPet.setPet(pet);
        trRegisterNewPet.execute();
        trRegisterNewPet.execute();
    }

    private Pet getLinuxPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Linux");
        pet.setGender(Gender.MALE);
        pet.setBirthDate("2 MAR 2020");
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }
}

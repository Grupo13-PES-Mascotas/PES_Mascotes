package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import static org.junit.Assert.assertTrue;

public class TestTrRegisterNewPet {
    private User user;
    private TrRegisterNewPet trRegisterNewPet;
    private final String PASSWORD = "12En)(";

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", PASSWORD);
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
        pet.setGender(GenderType.Male);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }
}

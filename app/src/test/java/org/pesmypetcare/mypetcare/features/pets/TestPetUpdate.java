package org.pesmypetcare.mypetcare.features.pets;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.TrUpdatePet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPetUpdate {
    private Pet pet;
    private User usr;
    private TrUpdatePet trUpdatePet;

    @Before
    public void setUp() {
        Bundle petInfo = new Bundle();
        petInfo.putString(Pet.BUNDLE_NAME, "Manolo");
        petInfo.putString(Pet.BUNDLE_BIRTH_DATE, "31/2/2000");
        petInfo.putString(Pet.BUNDLE_BREED, "MALE");
        petInfo.putFloat(Pet.BUNDLE_CALORIES, 500);
        petInfo.putString(Pet.BUNDLE_GENDER, "Manolo");
        petInfo.putString(Pet.BUNDLE_PATHOLOGIES, "Manolo");
        petInfo.putInt(Pet.BUNDLE_WASH, 2);
        petInfo.putFloat(Pet.BUNDLE_WEIGHT, 15);

        pet = new Pet(petInfo);

        usr = new User ( "Oriol", "er@gmail.com", "909090");
        usr.addPet(pet);
        trUpdatePet = new TrUpdatePet(new StubPetManagerService());
    }

    @Test
    public void shouldChangeGender() {
        pet.setGender(Gender.MALE);
        assertEquals(Gender.MALE, pet.getGender());
    }

    @Test
    public void shouldChangeWeight() {
        pet.setWeight(30);
        assertEquals(30, pet.getWeight(),0);
    }

    @Test
    public void shouldChangeName() {
        pet.setName("Ohio");
        assertEquals("Ohio", pet.getName());
    }

    @Test
    public void shouldChangeBreed() {
        pet.setBreed("Bulldog");
        assertEquals("Bulldog", pet.getBreed());
    }

    @Test
    public void shouldUpdatePetService() throws PetAlreadyExistingException, UserIsNotOwnerException {
        pet.setWeight(20);
        trUpdatePet.setUser(usr);
        trUpdatePet.setPet(pet);
        trUpdatePet.execute();
        boolean changeResult = trUpdatePet.getResult();

        assertTrue("should communicate with service to update a pet", changeResult);

    }

    @Test(expected = UserIsNotOwnerException.class)
    public void shouldNotUpdatePetIfNotOwner() throws PetAlreadyExistingException, UserIsNotOwnerException {
        User usr2 = new User ( "Gabi", "er2@gmail.com", "909020");
        trUpdatePet.setUser(usr2);
        trUpdatePet.setPet(pet);
        trUpdatePet.execute();
    }

    @Test(expected = PetAlreadyExistingException.class)
    public void shouldNotUpdatePetIfExisting() throws PetAlreadyExistingException, UserIsNotOwnerException {
        Pet linux = getAuxPet();
        usr.addPet(linux);
        pet.setName("Linux");
        trUpdatePet.setUser(usr);
        trUpdatePet.setPet(pet);
        trUpdatePet.execute();
    }

    private Pet getAuxPet() {
        Bundle petInfo = new Bundle();
        petInfo.putString(Pet.BUNDLE_NAME, "Linux");
        petInfo.putString(Pet.BUNDLE_BIRTH_DATE, "31/2/2000");
        petInfo.putString(Pet.BUNDLE_BREED, "MALE");
        petInfo.putFloat(Pet.BUNDLE_CALORIES, 500);
        petInfo.putString(Pet.BUNDLE_GENDER, "Bulldog");
        petInfo.putString(Pet.BUNDLE_PATHOLOGIES, "None");
        petInfo.putInt(Pet.BUNDLE_WASH, 2);
        petInfo.putFloat(Pet.BUNDLE_WEIGHT, 15);
        return new Pet(petInfo);
    }
}


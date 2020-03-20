package org.pesmypetcare.mypetcare.features.pets;

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
        pet = new Pet();
        pet.setName("Manolo");
        pet.setGender(Gender.FEMALE);
        pet.setBirthDate("2 MAR 2010");
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);

        usr = new User("johnDoe", "johndoe@gmail.com", "1234");
        trUpdatePet = new TrUpdatePet(new StubPetManagerService());
    }

    @Test
    public void shouldChangeGender() {
        usr.addPet(pet);
        pet.setGender(Gender.MALE);
        assertEquals(Gender.MALE, pet.getGender());
    }

    @Test
    public void shouldChangeWeight() {
        usr.addPet(pet);
        pet.setWeight(30);
        assertEquals(30, pet.getWeight(),0);
    }

    @Test
    public void shouldChangeName() {
        usr.addPet(pet);
        pet.setName("Ohio");
        assertEquals("Ohio", pet.getName());
    }

    @Test
    public void shouldChangeBreed() {
        usr.addPet(pet);
        pet.setBreed("Bulldog");
        assertEquals("Bulldog", pet.getBreed());
    }

    @Test
    public void shouldUpdatePetService() throws PetAlreadyExistingException, UserIsNotOwnerException {
        usr.addPet(pet);
        pet.setWeight(20);
        trUpdatePet.setUser(usr);
        trUpdatePet.setPet(pet);
        trUpdatePet.execute();
        boolean changeResult = trUpdatePet.getResult();

        assertTrue("should communicate with service to update a pet", changeResult);

    }

    @Test(expected = UserIsNotOwnerException.class)
    public void shouldNotUpdatePetIfNotOwner() throws PetAlreadyExistingException, UserIsNotOwnerException {
        usr.addPet(pet);
        User usr2 = new User ( "Gabi", "er2@gmail.com", "909020");
        trUpdatePet.setUser(usr2);
        trUpdatePet.setPet(pet);
        trUpdatePet.execute();
    }

    @Test(expected = PetAlreadyExistingException.class)
    public void shouldNotUpdatePetIfExisting() throws PetAlreadyExistingException, UserIsNotOwnerException {
        usr.addPet(pet);
        Pet linux = getAuxPet();
        usr.addPet(linux);
        pet.setName("Linux");
        trUpdatePet.setUser(usr);
        trUpdatePet.setPet(pet);
        trUpdatePet.execute();
    }

    private Pet getAuxPet() {
        Pet pet2 = new Pet();
        pet2.setName("Linux");
        pet2.setGender(Gender.MALE);
        pet2.setBirthDate("2 MAR 2020");
        pet2.setBreed("Husky");
        pet2.setRecommendedDailyKiloCalories(2);
        pet2.setWashFrequency(2);
        pet2.setWeight(2);
        return pet2;
    }
}


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
    private final String name = "Manolo";
    private final String husky = "Husky";
    private final String bulldog = "Bulldog";
    private final String linuxName = "Linux";
    private final String ohio = "Ohio";
    private final int number30 = 30;
    private final int number20 = 20;


    @Before
    public void setUp() throws PetRepeatException {
        usr = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = new Pet();
        pet.setName(name);
        pet.setGender(Gender.FEMALE);
        pet.setBirthDate("2 MAR 2010");
        pet.setBreed(husky);
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        trUpdatePet = new TrUpdatePet(new StubPetManagerService());
    }

    @Test
    public void shouldChangeGender() {
        pet.setGender(Gender.MALE);
        assertEquals("Correct gender change", Gender.MALE, pet.getGender());
    }

    @Test
    public void shouldChangeWeight() {
        pet.setWeight(number30);
        assertEquals("Correct weight change", number30, pet.getWeight(), 0);
    }

    @Test
    public void shouldChangeName() throws PetRepeatException {
        pet.setName(ohio);
        assertEquals("Correct name change", ohio, pet.getName());
    }

    @Test
    public void shouldChangeBreed() {
        pet.setBreed(bulldog);
        assertEquals("Correct breed change", bulldog, pet.getBreed());
    }

    @Test
    public void shouldUpdatePetService() throws UserIsNotOwnerException {
        usr.addPet(pet);
        pet.setWeight(number20);
        trUpdatePet.setUser(usr);
        trUpdatePet.setPet(pet);
        trUpdatePet.execute();
        boolean changeResult = trUpdatePet.isResult();

        assertTrue("should communicate with service to update a pet", changeResult);

    }

    @Test(expected = UserIsNotOwnerException.class)
    public void shouldNotUpdatePetIfNotOwner() throws PetRepeatException, UserIsNotOwnerException {
        usr.addPet(pet);
        User usr2 = new User("Gabi", "er2@gmail.com", "909020");
        usr2.addPet(getAuxPet(name));
        trUpdatePet.setUser(usr2);
        trUpdatePet.setPet(pet);
        trUpdatePet.execute();
    }

    @Test(expected = PetRepeatException.class)
    public void shouldNotUpdatePetNameIfExisting() throws PetRepeatException {
        Pet linux = getAuxPet(linuxName);
        usr.addPet(linux);
        usr.addPet(pet);
        pet.setName(linuxName);
    }

    /**
     * Creates a new pet.
     * @param petName The name of the new pet
     * @return The new pet
     */
    private Pet getAuxPet(String petName) throws PetRepeatException {
        Pet pet2 = new Pet();
        pet2.setName(petName);
        pet2.setGender(Gender.MALE);
        pet2.setBirthDate("2 MAR 2020");
        pet2.setBreed(husky);
        pet2.setRecommendedDailyKiloCalories(2);
        pet2.setWashFrequency(2);
        pet2.setWeight(2);
        return pet2;
    }
}


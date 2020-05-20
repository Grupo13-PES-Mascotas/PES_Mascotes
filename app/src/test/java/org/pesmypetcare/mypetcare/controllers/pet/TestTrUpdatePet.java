package org.pesmypetcare.mypetcare.controllers.pet;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.controllers.pet.TrUpdatePet;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTrUpdatePet {
    private Pet pet;
    private User usr;
    private TrUpdatePet trUpdatePet;
    private final String NAME = "Manolo";
    private final String HUSKY = "Husky";
    private final String BULLDOG = "Bulldog";
    private final String LINUX_NAME = "Linux";
    private final String OHIO = "Ohio";
    private final int NUMBER_30 = 30;
    private final int NUMBER_20 = 20;


    @Before
    public void setUp() throws PetRepeatException {
        usr = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = new Pet();
        pet.setName(NAME);
        pet.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed(HUSKY);
        pet.setRecommendedDailyKiloCaloriesForCurrentDate(2);
        pet.setWashFrequencyForCurrentDate(2);
        pet.setWeightForCurrentDate(2);
        trUpdatePet = new TrUpdatePet(new StubPetManagerService());
    }

    @Test
    public void shouldChangeGender() {
        pet.setGender(GenderType.Male);
        assertEquals("Correct gender change", GenderType.Male, pet.getGender());
    }

    @Test
    public void shouldChangeWeight() {
        pet.setWeightForCurrentDate(NUMBER_30);
        assertEquals("Correct weight change", NUMBER_30, pet.getLastWeight(), 0);
    }

    @Test
    public void shouldChangeName() throws PetRepeatException {
        pet.setName(OHIO);
        assertEquals("Correct name change", OHIO, pet.getName());
    }

    @Test
    public void shouldChangeBreed() {
        pet.setBreed(BULLDOG);
        assertEquals("Correct breed change", BULLDOG, pet.getBreed());
    }

    @Test
    public void shouldUpdatePetService() throws UserIsNotOwnerException {
        usr.addPet(pet);
        pet.setWeightForCurrentDate(NUMBER_20);
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
        usr2.addPet(getAuxPet(NAME));
        trUpdatePet.setUser(usr2);
        trUpdatePet.setPet(pet);
        trUpdatePet.execute();
    }

    @Test(expected = PetRepeatException.class)
    public void shouldNotUpdatePetNameIfExisting() throws PetRepeatException {
        Pet linux = getAuxPet(LINUX_NAME);
        usr.addPet(linux);
        usr.addPet(pet);
        pet.setName(LINUX_NAME);
    }

    /**
     * Creates a new pet.
     * @param petName The name of the new pet
     * @return The new pet
     */
    private Pet getAuxPet(String petName) throws PetRepeatException {
        Pet pet2 = new Pet();
        pet2.setName(petName);
        pet2.setGender(GenderType.Male);
        pet2.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet2.setBreed(HUSKY);
        pet2.setRecommendedDailyKiloCaloriesForCurrentDate(2);
        pet2.setWashFrequencyForCurrentDate(2);
        pet2.setWeightForCurrentDate(2);
        return pet2;
    }
}


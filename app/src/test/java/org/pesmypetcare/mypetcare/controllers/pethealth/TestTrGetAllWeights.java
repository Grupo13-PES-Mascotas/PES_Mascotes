package org.pesmypetcare.mypetcare.controllers.pethealth;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrGetAllWeights {
    private User user;
    private Pet pet;
    private TrGetAllWeights trGetAllWeights;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet();

        user.addPet(pet);
        trGetAllWeights = new TrGetAllWeights(new StubPetManagerService());
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotAddWeightToNonOwnerPet() throws NotPetOwnerException {
        trGetAllWeights.setUser(new User("johnSmith", "johnSmith@gmail.com", "5678"));
        trGetAllWeights.setPet(pet);
        trGetAllWeights.execute();
    }

    @Test
    public void shouldGetAllWeights() throws NotPetOwnerException {
        trGetAllWeights.setUser(user);
        trGetAllWeights.setPet(pet);
        trGetAllWeights.execute();

        DateTime dateTime = DateTime.getCurrentDate();
        assertEquals("Should get all the weights", "{" + dateTime.toString() + "=" + "10.0}",
            pet.getHealthInfo().getWeight().toString());
    }

    private Pet getDinkyPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Dinky");
        pet.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCaloriesForCurrentDate(2);
        pet.setWashFrequencyForCurrentDate(2);
        pet.setWeightForCurrentDate(2);
        return pet;
    }
}

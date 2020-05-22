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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrAddNewWeight {
    private static final double DELTA = 0.05;
    private static final double NEW_WEIGHT = 10.0;
    private static final double NEW_WEIGHT1 = 15.0;
    private User user;
    private Pet pet;
    private DateTime dateTime;
    private TrAddNewWeight trAddNewWeight;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet();
        dateTime = getToday();

        user.addPet(pet);
        trAddNewWeight = new TrAddNewWeight(new StubPetManagerService());
    }

    private DateTime getToday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
        Date date = new Date();
        String strData = dateFormat.format(date);
        return DateTime.Builder.buildDateString(strData);
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotAddWeightToNonOwnerPet() throws NotPetOwnerException, ExecutionException,
        InterruptedException {
        trAddNewWeight.setUser(new User("johnSmith", "johnSmith@gmail.com", "5678"));
        trAddNewWeight.setPet(pet);
        trAddNewWeight.setNewWeight(NEW_WEIGHT);
        trAddNewWeight.setDateTime(dateTime);
        trAddNewWeight.execute();
    }

    @Test
    public void shouldChangePetWeight() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trAddNewWeight.setUser(user);
        trAddNewWeight.setPet(pet);
        trAddNewWeight.setNewWeight(NEW_WEIGHT);
        trAddNewWeight.setDateTime(dateTime);
        trAddNewWeight.execute();

        assertEquals("Should change weight", NEW_WEIGHT, pet.getLastWeight(), DELTA);
    }

    @Test
    public void shouldOnlyHaveOneWeightPerDay() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trAddNewWeight.setUser(user);
        trAddNewWeight.setPet(pet);
        trAddNewWeight.setNewWeight(NEW_WEIGHT);
        trAddNewWeight.setDateTime(dateTime);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        trAddNewWeight.setNewWeight(NEW_WEIGHT1);
        trAddNewWeight.execute();

        assertEquals("Should only have one weight per day", 1, pet.getHealthInfo().getWeight().size());
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

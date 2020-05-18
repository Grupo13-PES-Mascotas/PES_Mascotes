package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.controllers.pethealth.TrAddNewWashFrequency;
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

public class TestTrAddNewWashFrequency {
    private User user;
    private Pet pet;
    private DateTime dateTime;
    private TrAddNewWashFrequency trAddNewWashFrequency;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet();
        dateTime = getToday();

        user.addPet(pet);
        trAddNewWashFrequency = new TrAddNewWashFrequency(new StubPetManagerService());
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
        trAddNewWashFrequency.setUser(new User("johnSmith", "johnSmith@gmail.com", "5678"));
        trAddNewWashFrequency.setPet(pet);
        trAddNewWashFrequency.setNewWashFrequency(2);
        trAddNewWashFrequency.setDateTime(dateTime);
        trAddNewWashFrequency.execute();
    }

    @Test
    public void shouldAddWashFrequency() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trAddNewWashFrequency.setUser(user);
        trAddNewWashFrequency.setPet(pet);
        trAddNewWashFrequency.setNewWashFrequency(2);
        trAddNewWashFrequency.setDateTime(dateTime);
        trAddNewWashFrequency.execute();

        assertEquals("Should add wash frequency", 2, pet.getLastWashFrequency());
    }

    @Test
    public void shouldOnlyHaveOneWashFrequencyPerDay() throws NotPetOwnerException, ExecutionException,
            InterruptedException {
        trAddNewWashFrequency.setUser(user);
        trAddNewWashFrequency.setPet(pet);
        trAddNewWashFrequency.setNewWashFrequency(2);
        trAddNewWashFrequency.setDateTime(dateTime);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        trAddNewWashFrequency.setNewWashFrequency(5);
        trAddNewWashFrequency.execute();

        assertEquals("Should only have one wash frequency per day", 1, pet.getHealthInfo().getWashFrequency().size());
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

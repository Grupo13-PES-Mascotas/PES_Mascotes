package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.controllers.pethealth.TrDeleteWashFrequency;
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

import static org.junit.Assert.assertEquals;

public class TestTrDeleteWashFrequency {
    private User user;
    private Pet pet;
    private TrDeleteWashFrequency trDeleteWashFrequency;
    private DateTime dateTime;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet();

        user.addPet(pet);
        setActualDate();

        trDeleteWashFrequency = new TrDeleteWashFrequency(new StubPetManagerService());
    }

    private void setActualDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String strData = dateFormat.format(date);
        dateTime = DateTime.Builder.buildFullString(strData);
        dateTime.setHour(0);
        dateTime.setMinutes(0);
        dateTime.setSeconds(0);
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotDeleteWashFrequencyToNonOwnerPet() throws NotPetOwnerException, ExecutionException,
        InterruptedException {
        trDeleteWashFrequency.setUser(new User("johnSmith", "johnSmith@gmail.com", "5678"));
        trDeleteWashFrequency.setPet(pet);
        trDeleteWashFrequency.setDateTime(dateTime);
        trDeleteWashFrequency.execute();
    }

    @Test
    public void shouldDeleteWashFrequencyData() throws NotPetOwnerException, ExecutionException, InterruptedException {
        pet.setWashFrequency(2);

        trDeleteWashFrequency.setUser(user);
        trDeleteWashFrequency.setPet(pet);
        trDeleteWashFrequency.setDateTime(dateTime);
        trDeleteWashFrequency.execute();

        assertEquals("Should delete weight for actual date", 0, pet.getHealthInfo().getWashFrequency().size());
    }

    private DateTime getActualDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String strData = dateFormat.format(date);
        return DateTime.Builder.buildFullString(strData);
    }

    private Pet getDinkyPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Dinky");
        pet.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }
}

package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TestTrDeleteWeight {
    private User user;
    private Pet pet;
    private TrDeleteWeight trDeleteWeight;
    private DateTime dateTime;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet();

        user.addPet(pet);
        setActualDate();

        trDeleteWeight = new TrDeleteWeight(new StubPetManagerService());
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
    public void shouldNotDeleteWeightToNonOwnerPet() throws NotPetOwnerException {
        trDeleteWeight.setUser(new User("johnSmith", "johnSmith@gmail.com", "5678"));
        trDeleteWeight.setPet(pet);
        trDeleteWeight.setDateTime(dateTime);
        trDeleteWeight.execute();
    }

    @Test
    public void shouldDeleteWeightData() throws NotPetOwnerException {
        pet.setWeight(10.0);

        trDeleteWeight.setUser(user);
        trDeleteWeight.setPet(pet);
        trDeleteWeight.setDateTime(dateTime);
        trDeleteWeight.execute();

        assertEquals("Should delete weight for actual date", 0, pet.getHealthInfo().getWeight().size());
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

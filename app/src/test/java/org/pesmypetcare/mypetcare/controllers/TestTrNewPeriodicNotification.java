package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

public class TestTrNewPeriodicNotification {
    private static final String NAME = "Duki";
    private static final String BREED = "Labrador";
    private Pet pet;
    private TrNewPeriodicNotification trNewPeriodicNotification;

    @Before
    public void SetUp() throws PetRepeatException {
        pet = new Pet();
        pet.setName(NAME);
        pet.setGender(GenderType.Male);
        pet.setBirthDate("2 FEB 1999");
        pet.setBreed(BREED);
        pet.setRecommendedDailyKiloCalories(4);
        pet.setWashFrequency(30);
        pet.setWeight(20);
        pet.setOwner(new User("johnDoe", "", ""));
        trNewPeriodicNotification = new TrNewPeriodicNotification(new StubPetManagerService());
    }
    @Test
    public void shouldAddOneNotification() {

    }
}

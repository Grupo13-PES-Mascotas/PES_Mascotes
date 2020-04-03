package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import static org.junit.Assert.assertTrue;

public class TestTrNewPersonalEvent {
    private final String PASSWORD = "12En)(";
    private User user;
    private Pet pet;
    private final String NAME = "Manolo";
    private final String HUSKY = "Husky";

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", PASSWORD);
        pet = new Pet();
        pet.setName(NAME);
        pet.setGender(GenderType.Female);
        pet.setBirthDate("2 MAR 2010");
        pet.setBreed(HUSKY);
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        TrRegisterNewPet trRegisterNewPet = new TrRegisterNewPet(new StubPetManagerService());
        TrNewPersonalEvent trNewPersonalEvent = new TrNewPersonalEvent(new StubPetManagerService(),
                new StubUserManagerService());
    }

    @Test
    public void shouldAddOneEvent() {
        Event e = new Event("Hello", "Today");
        pet.addEvent(e);
        assertTrue("should add one event", pet.getEvents("Today").contains(e));
    }
}

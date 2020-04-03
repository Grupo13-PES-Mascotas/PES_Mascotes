package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import static org.junit.Assert.assertTrue;

public class TestTrNewPersonalEvent {
    private Pet pet;
    private final String NAME = "Dinky";
    private final String HUSKY = "Husky";
    private TrNewPersonalEvent trNewPersonalEvent;

    @Before
    public void setUp() throws PetRepeatException {
        pet = new Pet();
        pet.setName(NAME);
        pet.setGender(GenderType.Female);
        pet.setBirthDate("2 MAR 2010");
        pet.setBreed(HUSKY);
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        pet.setOwner(new User("johnDoe", "", ""));
        trNewPersonalEvent = new TrNewPersonalEvent(new StubPetManagerService());
    }

    @Test
    public void shouldAddOneEvent() {
        Event e = new Event("Hello", "2020-04-03T10:30:00");
        pet.addEvent(e);
        System.out.println(e);
        System.out.println(pet.getEvents("2020-04-03"));
        assertTrue("should add one event", pet.getEvents("2020-04-03").contains(e));
    }

    @Test
    public void shouldCommunicateWithService() {
        Event e = new Event("Hello", "2020-04-03T10:30:00");
        pet.addEvent(e);
        trNewPersonalEvent.setPet(pet);
        trNewPersonalEvent.setEvent(e);
        trNewPersonalEvent.execute();
        boolean addingResult = trNewPersonalEvent.isResult();
        assertTrue("should communicate with service to add a event", addingResult);
    }
}

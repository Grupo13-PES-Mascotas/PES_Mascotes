package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestTrDeletePersonalEvent {
    private static final String DATE_TIME = "2020-04-03T10:30:00";
    private Pet pet;
    private final String NAME = "Dinky";
    private final String HUSKY = "Husky";
    private TrDeletePersonalEvent trDeletePersonalEvent;

    @Before
    public void setUp() throws PetRepeatException {
        pet = new Pet();
        pet.setName(NAME);
        pet.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed(HUSKY);
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        pet.setOwner(new User("johnDoe", "", ""));
        trDeletePersonalEvent = new TrDeletePersonalEvent(new StubPetManagerService());
    }

    @Test
    public void shouldDeleteOneEvent() {
        Event e = new Event("Hello", DateTime.Builder.buildFullString(DATE_TIME));
        pet.addEvent(e);
        pet.deleteEvent(e);
        assertFalse("should add one event", pet.getEvents(DATE_TIME).contains(e));
    }

    @Test
    public void shouldCommunicateWithService() {
        Event e = new Event("Hello2", DateTime.Builder.buildFullString(DATE_TIME));
        pet.addEvent(e);
        trDeletePersonalEvent.setPet(pet);
        trDeletePersonalEvent.setEvent(e);
        trDeletePersonalEvent.execute();
        boolean deletingResult = trDeletePersonalEvent.isResult();
        assertTrue("should communicate with service to delete a event", deletingResult);
    }
}

package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class TestTrNewPeriodicNotification {
    private static final String DATE = "2020-04-15T10:30:00";
    private static final String DATE2 = "2020-05-13T10:30:00";
    private static final String DATE_2_WEEKS = "2020-05-27T14:30:00";
    private static final String DATE_3_MONTHS = "2020-08-13T14:30:00";
    private static final String DESC = "Hello";
    private final int WEDNESDAY = 3;
    private final int DATE13 = 13;
    private final String NAME = "Dinky";
    private final String HUSKY = "Husky";
    private Pet pet;
    private TrNewPeriodicNotification trNewPeriodicNotification;

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
        trNewPeriodicNotification = new TrNewPeriodicNotification(new StubPetManagerService());
    }
    @Test
    public void shouldAddOneNotificationEveryWeek() throws ParseException {
        Event e = new Event(DESC, DATE);
        trNewPeriodicNotification.setPet(pet);
        trNewPeriodicNotification.setEvent(e);
        int periodWeek = 7;
        trNewPeriodicNotification.setPeriodicity(periodWeek, WEDNESDAY);
        trNewPeriodicNotification.execute();
        assertEquals("should add one periodic notification weekly", e, pet.getPeriodicNotificationDay(DATE));
    }

    @Test
    public void shouldAddOneNotificationEvery2Weeks() throws ParseException {
        Event e = new Event(DESC, DATE2);
        trNewPeriodicNotification.setPet(pet);
        trNewPeriodicNotification.setEvent(e);
        int period2Weeks = 14;
        trNewPeriodicNotification.setPeriodicity(period2Weeks, WEDNESDAY);
        trNewPeriodicNotification.execute();
        assertEquals("should add one periodic notification every 2 weeks", e,
                pet.getPeriodicNotificationDay(DATE_2_WEEKS));
    }

    @Test
    public void shouldAddOneNotificationEveryMonth() throws ParseException {
        Event e = new Event(DESC, DATE2);
        trNewPeriodicNotification.setPet(pet);
        trNewPeriodicNotification.setEvent(e);
        int periodMonth = -1;
        trNewPeriodicNotification.setPeriodicity(periodMonth, DATE13);
        trNewPeriodicNotification.execute();
        assertEquals("should add one periodic notification monthly", e, pet.getPeriodicNotificationDay(DATE2));
    }

    @Test
    public void shouldAddOneNotificationEvery3Months() throws ParseException {
        Event e = new Event(DESC, DATE2);
        trNewPeriodicNotification.setPet(pet);
        trNewPeriodicNotification.setEvent(e);
        int period3Months = -3;
        trNewPeriodicNotification.setPeriodicity(period3Months, DATE13);
        trNewPeriodicNotification.execute();
        assertEquals("should add one periodic notification every 3 months", e,
                pet.getPeriodicNotificationDay(DATE_3_MONTHS));
    }
}

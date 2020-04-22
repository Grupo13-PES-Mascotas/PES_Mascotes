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
import static org.junit.Assert.assertTrue;

public class TestTrNewPeriodicNotification {
    private static final String DATE = "2020-04-15";
    private static final String DATE2 = "2020-05-13";
    private static final String DATE_2_WEEKS = "2020-05-27";
    private static final String DATE_3_MONTHS = "2020-08-13";
    private static final String DESC = "Hello";
    private final int WEDNESDAY = 3;
    private final int DATE13 = 13;
    private final int periodWeek = 7;
    private final int period2Weeks = 14;
    private final int periodMonth = -1;
    private final int period3Months = -3;
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
        trNewPeriodicNotification.setPeriodicity(periodWeek, WEDNESDAY);
        trNewPeriodicNotification.execute();
        assertTrue("should add one periodic notification weekly", pet.getPeriodicNotificationDay(DATE).contains(e));
    }

    @Test
    public void shouldAddOneNotificationEvery2Weeks() throws ParseException {
        Event e = new Event(DESC, DATE2);
        trNewPeriodicNotification.setPet(pet);
        trNewPeriodicNotification.setEvent(e);
        trNewPeriodicNotification.setPeriodicity(period2Weeks, WEDNESDAY);
        trNewPeriodicNotification.execute();
        assertTrue("should add one periodic notification every 2 weeks",
                pet.getPeriodicNotificationDay(DATE_2_WEEKS).contains(e));
    }

    @Test
    public void shouldAddOneNotificationEveryMonth() throws ParseException {
        Event e = new Event(DESC, DATE2);
        trNewPeriodicNotification.setPet(pet);
        trNewPeriodicNotification.setEvent(e);
        trNewPeriodicNotification.setPeriodicity(periodMonth, DATE13);
        trNewPeriodicNotification.execute();
        assertTrue("should add one periodic notification monthly", pet.getPeriodicNotificationDay(DATE2).contains(e));
    }

    @Test
    public void shouldAddOneNotificationEvery3Months() throws ParseException {
        Event e = new Event(DESC, DATE2);
        trNewPeriodicNotification.setPet(pet);
        trNewPeriodicNotification.setEvent(e);
        trNewPeriodicNotification.setPeriodicity(period3Months, DATE13);
        trNewPeriodicNotification.execute();
        assertTrue("should add one periodic notification every 3 months",
                pet.getPeriodicNotificationDay(DATE_3_MONTHS).contains(e));
    }
}

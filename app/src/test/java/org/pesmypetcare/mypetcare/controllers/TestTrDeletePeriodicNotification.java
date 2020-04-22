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
import static org.junit.Assert.assertNotEquals;

/**
 * @author Daniel Clemente
 */
public class TestTrDeletePeriodicNotification {
    private static final String DATE = "2020-04-15T10:30:00";
    private static final String DATE2 = "2020-05-13T10:30:00";
    private static final String DATE_2_WEEKS = "2020-05-27T14:30:00";
    private static final String DATE_3_MONTHS = "2020-08-13T14:30:00";
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
    private TrDeletePeriodicNotification trDeletePeriodicNotification;

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
        trDeletePeriodicNotification = new TrDeletePeriodicNotification(new StubPetManagerService());

    }

    @Test
    public void shouldDeleteOneNotificationEveryWeek() throws ParseException {
        Event e = new Event(DESC, DATE);
        pet.addPeriodicNotification(e, periodWeek, WEDNESDAY);
        trDeletePeriodicNotification.setEvent(e);
        trDeletePeriodicNotification.setPet(pet);
        trDeletePeriodicNotification.setPeriodicity(periodWeek, WEDNESDAY);
        trDeletePeriodicNotification.execute();
        assertNotEquals("should delete one periodic notification weekly", e, pet.getPeriodicNotificationDay(DATE));
    }

    @Test
    public void shouldDeleteOneNotificationEvery2Weeks() throws ParseException {
        Event e = new Event(DESC, DATE);
        pet.addPeriodicNotification(e, period2Weeks, WEDNESDAY);
        trDeletePeriodicNotification.setEvent(e);
        trDeletePeriodicNotification.setPet(pet);
        trDeletePeriodicNotification.setPeriodicity(period2Weeks, WEDNESDAY);
        trDeletePeriodicNotification.execute();
        assertNotEquals("should delete one periodic notification weekly", e, pet.getPeriodicNotificationDay(DATE_2_WEEKS));
    }
}

package org.pesmypetcare.mypetcare.controllers;


import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.event.TrNewPeriodicNotification;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubGoogleCalendarService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

/**
 * @author Daniel Clemente
 */

public class TestTrNewPeriodicNotification {
    private static final String DATE = "2020-04-15";
    private static final String DATE2 = "2020-05-13";
    private static final String DATE_2_WEEKS = "2020-05-27";
    private static final String DATE_3_MONTHS = "2020-08-13";
    private static final String DESC = "Hello";
    private static final String BIRTH = "2010-03-02";
    private final int periodWeek = 7;
    private final int period2Weeks = 14;
    private final int periodMonth = -1;
    private final int period3Months = -3;
    private final String NAME = "Dinky";
    private final String NAME2 = "Pinky";
    private final String HUSKY = "Husky";
    private Pet pet;
    private Pet pet2;
    private User user;
    private TrNewPeriodicNotification trNewPeriodicNotification;

    @Before
    public void setUp() throws PetRepeatException {
        pet = new Pet();
        user = new User("johnDoe", "", "");
        pet.setName(NAME);
        pet.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString(BIRTH));
        pet.setBreed(HUSKY);
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        pet.setOwner(user);
        pet2 = new Pet();
        setPet2();
        trNewPeriodicNotification = new TrNewPeriodicNotification(new StubGoogleCalendarService());
    }

    private void setPet2() throws PetRepeatException {
        pet2.setName(NAME2);
        pet2.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString(BIRTH));
        pet2.setBreed(HUSKY);
        pet2.setRecommendedDailyKiloCalories(2);
        pet2.setWashFrequency(2);
        pet2.setWeight(2);
        User user2 = new User("Paco", "", "");
        pet2.setOwner(user2);
    }
    @Test
    public void shouldAddOneNotificationEveryWeek() throws ParseException,
            UserIsNotOwnerException, ExecutionException, InterruptedException {
        DateTime date = DateTime.Builder.buildDateString(DATE);
        Event e = new Event(DESC, date);
        trNewPeriodicNotification.setUser(user);
        trNewPeriodicNotification.setPet(pet);
        trNewPeriodicNotification.setEvent(e);
        trNewPeriodicNotification.setPeriodicity(periodWeek);
        trNewPeriodicNotification.execute();
        assertTrue("should add one periodic notification weekly", pet.getPeriodicEvents(DATE).contains(e));
    }

    @Test
    public void shouldAddOneNotificationEvery2Weeks() throws ParseException,
            UserIsNotOwnerException, ExecutionException, InterruptedException {
        DateTime date = DateTime.Builder.buildDateString(DATE);
        Event e = new Event(DESC, date);
        trNewPeriodicNotification.setUser(user);
        trNewPeriodicNotification.setPet(pet);
        trNewPeriodicNotification.setEvent(e);
        trNewPeriodicNotification.setPeriodicity(period2Weeks);
        trNewPeriodicNotification.execute();
        assertTrue("should add one periodic notification every 2 weeks",
                pet.getPeriodicEvents(DATE_2_WEEKS).contains(e));
    }

    @Test
    public void shouldAddOneNotificationEveryMonth() throws ParseException,
            UserIsNotOwnerException, ExecutionException, InterruptedException {
        DateTime date = DateTime.Builder.buildDateString(DATE);
        Event e = new Event(DESC, date);
        trNewPeriodicNotification.setUser(user);
        trNewPeriodicNotification.setPet(pet);
        trNewPeriodicNotification.setEvent(e);
        trNewPeriodicNotification.setPeriodicity(periodMonth);
        trNewPeriodicNotification.execute();
        assertTrue("should add one periodic notification monthly", pet.getPeriodicEvents(DATE2).contains(e));
    }

    @Test
    public void shouldAddOneNotificationEvery3Months() throws ParseException,
            UserIsNotOwnerException, ExecutionException, InterruptedException {
        DateTime date = DateTime.Builder.buildDateString(DATE);
        Event e = new Event(DESC, date);
        trNewPeriodicNotification.setUser(user);
        trNewPeriodicNotification.setPet(pet);
        trNewPeriodicNotification.setEvent(e);
        trNewPeriodicNotification.setPeriodicity(period3Months);
        trNewPeriodicNotification.execute();
        assertTrue("should add one periodic notification every 3 months",
                pet.getPeriodicEvents(DATE_3_MONTHS).contains(e));
    }

    @Test(expected = UserIsNotOwnerException.class)
    public void shouldNoDeleteOneNotificationIfNotOwner() throws ParseException,
            UserIsNotOwnerException, ExecutionException, InterruptedException {
        DateTime date = DateTime.Builder.buildDateString(DATE);
        Event e = new Event(DESC, date);
        pet.addPeriodicNotification(e, periodWeek);
        trNewPeriodicNotification.setUser(user);
        trNewPeriodicNotification.setPet(pet2);
        trNewPeriodicNotification.setEvent(e);
        trNewPeriodicNotification.setPeriodicity(periodWeek);
        trNewPeriodicNotification.execute();
    }
}

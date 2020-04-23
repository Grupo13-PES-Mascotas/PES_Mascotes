package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Daniel Clemente
 */
public class TestTrDeletePeriodicNotification {
    private static final String DATE = "2020-04-15T10:30:00";
    private static final String DATE2 = "2020-05-13T10:30:00";
    private static final String DATE_2_WEEKS = "2020-05-27T10:30:00";
    private static final String DATE_3_MONTHS = "2020-08-13T14:30:00";
    private static final String DESC = "Hello";
    private static final String DATEDIFFHOUR = "2020-04-15T10:30:00";
    private static final String NAME2 = "Pinky";
    private final int periodWeek = 7;
    private final int period2Weeks = 14;
    private final int periodMonth = -1;
    private final int period3Months = -3;
    private final String NAME = "Dinky";
    private final String HUSKY = "Husky";
    private Pet pet;
    private Pet pet2;
    private User user;
    private User user2;
    private TrDeletePeriodicNotification trDeletePeriodicNotification;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "", "");
        user2 = new User("Jose", "", "");
        pet = new Pet();
        pet.setName(NAME);
        pet.setGender(GenderType.Female);
        pet.setBirthDate("2 MAR 2010");
        pet.setBreed(HUSKY);
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        pet.setOwner(user);
        pet2 = new Pet();
        pet2.setName(NAME2);
        pet2.setGender(GenderType.Female);
        pet2.setBirthDate("2 MAR 2010");
        pet2.setBreed(HUSKY);
        pet2.setRecommendedDailyKiloCalories(2);
        pet2.setWashFrequency(2);
        pet2.setWeight(2);
        pet2.setOwner(user2);
        trDeletePeriodicNotification = new TrDeletePeriodicNotification(new StubPetManagerService());
    }

    @Test
    public void shouldDeleteOneNotificationEveryWeek() throws ParseException, UserIsNotOwnerException {
        DateTime date = DateTime.Builder.buildFullString(DATE);
        Event e = new Event(DESC, date);
        pet.addPeriodicNotification(e, periodWeek);
        trDeletePeriodicNotification.setEvent(e);
        trDeletePeriodicNotification.setPet(pet);
        trDeletePeriodicNotification.execute();
        assertFalse("should delete one periodic notification weekly", pet.getPeriodicEvents(DATE).contains(e));
    }

    @Test
    public void shouldDeleteOneNotificationEvery2Weeks() throws ParseException, UserIsNotOwnerException {
        DateTime date = DateTime.Builder.buildFullString(DATE2);
        Event e = new Event(DESC, date);
        pet.addPeriodicNotification(e, period2Weeks);
        trDeletePeriodicNotification.setEvent(e);
        trDeletePeriodicNotification.setPet(pet);
        trDeletePeriodicNotification.execute();
        assertNotEquals("should delete one periodic notification every 2 weeks", e, pet.getPeriodicEvents(DATE_2_WEEKS).contains(e));
    }

    @Test
    public void shouldDeleteOneNotificationEveryMonth() throws ParseException, UserIsNotOwnerException {
        DateTime date = DateTime.Builder.buildFullString(DATE);
        Event e = new Event(DESC, date);
        pet.addPeriodicNotification(e, periodWeek);
        trDeletePeriodicNotification.setEvent(e);
        trDeletePeriodicNotification.setPet(pet);
        trDeletePeriodicNotification.execute();
        assertFalse("should delete one periodic notification monthly", pet.getPeriodicEvents(DATE).contains(e));
    }
    @Test(expected = UserIsNotOwnerException.class)
    public void shouldNoDeleteOneNotificationIfNotOwner() throws ParseException, UserIsNotOwnerException {
        DateTime date = DateTime.Builder.buildFullString(DATE);
        Event e = new Event(DESC, date);
        pet.addPeriodicNotification(e, periodWeek);
        trDeletePeriodicNotification.setUser(user);
        trDeletePeriodicNotification.setEvent(e);
        trDeletePeriodicNotification.setPet(pet2);
        trDeletePeriodicNotification.execute();
    }

}

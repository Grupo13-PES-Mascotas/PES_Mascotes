package org.pesmypetcare.mypetcare.controllers.event;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubGoogleCalendarService;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Daniel Clemente
 */
public class TestTrDeletePeriodicNotification {
    private static final String DATE = "2020-04-15T10:30:00";
    private static final String DATE2 = "2020-05-13T10:30:00";
    private static final String DATE_2_WEEKS = "2020-05-27T10:30:00";
    private static final String DESC = "Hello";
    private static final String NAME2 = "Pinky";
    private static final String BIRTH = "2010-03-02";
    private final int periodWeek = 7;
    private final int period2Weeks = 14;
    private final String NAME = "Dinky";
    private final String HUSKY = "Husky";
    private Pet pet;
    private Pet pet2;
    private User user;
    private TrDeletePeriodicNotification trDeletePeriodicNotification;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "", "");
        pet = new Pet();
        pet.setName(NAME);
        pet.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString(BIRTH));
        pet.setBreed(HUSKY);
        pet.setRecommendedDailyKiloCaloriesForCurrentDate(2);
        pet.setWashFrequencyForCurrentDate(2);
        pet.setWeightForCurrentDate(2);
        pet.setOwner(user);
        pet2 = new Pet();
        setPet2();
        trDeletePeriodicNotification = new TrDeletePeriodicNotification(new StubGoogleCalendarService());
    }

    private void setPet2() throws PetRepeatException {
        pet2.setName(NAME2);
        pet2.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString(BIRTH));
        pet2.setBreed(HUSKY);
        pet2.setRecommendedDailyKiloCaloriesForCurrentDate(2);
        pet2.setWashFrequencyForCurrentDate(2);
        pet2.setWeightForCurrentDate(2);
        User user2 = new User("Jose", "", "");
        pet2.setOwner(user2);
    }

    @Test
    public void shouldDeleteOneNotificationEveryWeek() throws ParseException,
            UserIsNotOwnerException, ExecutionException, InterruptedException {
        DateTime date = DateTime.Builder.buildFullString(DATE);
        Event e = new Event(DESC, date);
        pet.addPeriodicNotification(e, periodWeek);
        trDeletePeriodicNotification.setUser(user);
        trDeletePeriodicNotification.setEvent(e);
        trDeletePeriodicNotification.setPet(pet);
        trDeletePeriodicNotification.execute();
        assertFalse("should delete one periodic notification weekly", pet.getPeriodicEvents(DATE).contains(e));
    }

    @Test
    public void shouldDeleteOneNotificationEvery2Weeks() throws ParseException,
            UserIsNotOwnerException, ExecutionException, InterruptedException {
        DateTime date = DateTime.Builder.buildFullString(DATE2);
        Event e = new Event(DESC, date);
        pet.addPeriodicNotification(e, period2Weeks);
        trDeletePeriodicNotification.setUser(user);
        trDeletePeriodicNotification.setEvent(e);
        trDeletePeriodicNotification.setPet(pet);
        trDeletePeriodicNotification.execute();
        assertNotEquals("should delete one periodic notification every 2 weeks",
                e, pet.getPeriodicEvents(DATE_2_WEEKS).contains(e));
    }

    @Test
    public void shouldDeleteOneNotificationEveryMonth() throws ParseException,
            UserIsNotOwnerException, ExecutionException, InterruptedException {
        DateTime date = DateTime.Builder.buildFullString(DATE);
        Event e = new Event(DESC, date);
        pet.addPeriodicNotification(e, periodWeek);
        trDeletePeriodicNotification.setUser(user);
        trDeletePeriodicNotification.setEvent(e);
        trDeletePeriodicNotification.setPet(pet);
        trDeletePeriodicNotification.execute();
        assertFalse("should delete one periodic notification monthly", pet.getPeriodicEvents(DATE).contains(e));
    }
    @Test(expected = UserIsNotOwnerException.class)
    public void shouldNoDeleteOneNotificationIfNotOwner() throws ParseException,
            UserIsNotOwnerException, ExecutionException, InterruptedException {
        DateTime date = DateTime.Builder.buildFullString(DATE);
        Event e = new Event(DESC, date);
        pet.addPeriodicNotification(e, periodWeek);
        trDeletePeriodicNotification.setUser(user);
        trDeletePeriodicNotification.setEvent(e);
        trDeletePeriodicNotification.setPet(pet2);
        trDeletePeriodicNotification.execute();
    }

}

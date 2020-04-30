package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.EventData;

import java.util.concurrent.ExecutionException;

/**
 * @author Enric Hernando
 */
public class GoogleCalendarAdapter implements GoogleCalendarService {
    private static final String A_REALLY_PRETTY_LOCATION = "A really pretty Location";
    public static final int EMAIL_REMINDER_MINUTES = 10;

    @Override
    public void registerNewEvent(Pet pet, Event event) throws ExecutionException, InterruptedException {
        String id = (pet.getName() + event.getDateTime().getDay() + event.getDateTime().getMonth()
                + event.getDateTime().getYear() + event.getDescription()).toLowerCase();
        EventData eventData = new EventData(id, pet.getName(), A_REALLY_PRETTY_LOCATION,
                event.getDescription(), EventData.BLUEBERRY, EMAIL_REMINDER_MINUTES, 0,
                event.getDateTime().toString(), event.getDateTime().toString());
        ServiceLocator.getInstance().getGoogleCalendarManagerClient().createEvent(pet.getOwner()
                .getGoogleCalendarToken(),pet.getOwner().getUsername(), pet.getName(), eventData);

    }

    @Override
    public void deleteEvent(Pet pet, Event event) throws ExecutionException, InterruptedException {
        String id = (pet.getName() + event.getDateTime().getDay() + event.getDateTime().getMonth()
                + event.getDateTime().getYear() + event.getDescription()).toLowerCase();
        ServiceLocator.getInstance().getGoogleCalendarManagerClient().deleteEvent(pet.getOwner()
                .getGoogleCalendarToken(), pet.getOwner().getUsername(), pet.getName(), id);
    }

    @Override
    public void newSecondaryCalendar(Pet pet) throws ExecutionException, InterruptedException {
        ServiceLocator.getInstance().getGoogleCalendarManagerClient().createSecondaryCalendar(pet.getOwner()
                .getGoogleCalendarToken(), pet.getOwner().getUsername(), pet.getName());
    }

    @Override
    public void registerNewPeriodicNotification(User user, Pet pet, Event event, int period) throws ExecutionException,
            InterruptedException {
        String id = (pet.getName() + event.getDateTime().getDay() + event.getDateTime().getMonth()
                + event.getDateTime().getYear() + event.getDescription()).toLowerCase();
        EventData eventData = new EventData(id, pet.getName(), A_REALLY_PRETTY_LOCATION,
                event.getDescription(), EventData.BLUEBERRY, EMAIL_REMINDER_MINUTES, period,
                event.getDateTime().toString(), event.getDateTime().toString());
        ServiceLocator.getInstance().getGoogleCalendarManagerClient().createEvent(pet.getOwner()
                .getGoogleCalendarToken(), pet.getOwner().getUsername(), pet.getName(), eventData);
    }

    @Override
    public void deletePeriodicEvent(User user, Pet pet, Event event) throws ExecutionException, InterruptedException {
        String id = (pet.getName() + event.getDateTime().getDay() + event.getDateTime().getMonth()
                + event.getDateTime().getYear() + event.getDescription()).toLowerCase();
        ServiceLocator.getInstance().getGoogleCalendarManagerClient().deleteEvent(pet.getOwner()
                .getGoogleCalendarToken(), pet.getOwner().getUsername(), pet.getName(), id);
    }
}

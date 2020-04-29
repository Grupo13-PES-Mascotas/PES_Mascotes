package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.EventData;

import java.util.concurrent.ExecutionException;

/**
 * @author Enric Hernando
 */
public class GoogleCalendarAdapter implements GoogleCalendarService{
    @Override
    public void registerNewEvent(Pet pet, Event event) throws ExecutionException, InterruptedException {
        // Not implemented yet

        ServiceLocator.getInstance().getGoogleCalendarManagerClient().createEvent(pet.getOwner().getGoogleCalendarToken(),
                pet.getOwner().getUsername(), pet.getName(), new EventData(pet.getName()+event.getDateTime()+event.getDescription(), pet.getName(),
                        "", event.getDescription(), "1", 0, 0,
                        event.getDateTime().toString(), event.getDateTime().toString()));
    }

    @Override
    public void deleteEvent(Pet pet, Event event) throws ExecutionException, InterruptedException {
        ServiceLocator.getInstance().getGoogleCalendarManagerClient().deleteEvent(pet.getOwner().getGoogleCalendarToken(),
                pet.getOwner().getUsername(), pet.getName(), pet.getName()+event.getDateTime()+event.getDescription());
    }

    @Override
    public void newSecondaryCalendar(Pet pet) throws ExecutionException, InterruptedException {
        ServiceLocator.getInstance().getGoogleCalendarManagerClient().createSecondaryCalendar(pet.getOwner().getGoogleCalendarToken(),
                pet.getOwner().getUsername(), pet.getName());
    }

    @Override
    public void registerNewPeriodicNotification(User user, Pet pet, Event event, int period) {
        //Not implemented yet
    }

    @Override
    public void deletePeriodicEvent(User user, Pet pet, Event event) {
        //Not implemented yet
    }
}

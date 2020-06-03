package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.googlecalendar.GoogleCalendarService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Enric Hernando
 */
public class StubGoogleCalendarService implements GoogleCalendarService {
    private static final String JOHN_DOE = "johnDoe";
    private static final String JOHN_DOE_2 = "johnDoe2";
    private static final String DINKY = "Dinky";
    private static final double WEIGHT = 10.0;
    private Map<String, ArrayList<Pet>> data;

    public StubGoogleCalendarService() {
        this.data = new HashMap<>();
        this.data.put(JOHN_DOE, new ArrayList<>());
        Pet pet = new Pet(DINKY);
        pet.setWeightForCurrentDate(WEIGHT);
        Objects.requireNonNull(this.data.get(JOHN_DOE)).add(pet);

        this.data.put(JOHN_DOE_2, new ArrayList<>());
        for (int index = 0; index < 2; ++index) {
            pet = new Pet("pet" + index);
            Objects.requireNonNull(this.data.get(JOHN_DOE_2)).add(pet);
        }
    }

    @Override
    public void registerNewPeriodicNotification(User user, Pet pet, Event event, int period) {
        ArrayList<Pet> pets = data.get(user.getUsername());
        assert pets != null;
        int index = Objects.requireNonNull(pets).indexOf(pet);
        try {
            pets.get(index).addPeriodicNotification(event, period);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePeriodicEvent(User user, Pet pet, Event event) {
        ArrayList<Pet> pets = data.get(user.getUsername());
        assert pets != null;
        int index = Objects.requireNonNull(pets).indexOf(pet);
        try {
            pets.get(index).deletePeriodicNotification(event);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerNewEvent(Pet pet, Event event) {
        //
    }

    @Override
    public void deleteEvent(Pet pet, Event event) {
        //
    }

    @Override
    public void newSecondaryCalendar(Pet pet) {
        //
    }
}

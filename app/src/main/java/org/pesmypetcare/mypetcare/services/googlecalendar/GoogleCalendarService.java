package org.pesmypetcare.mypetcare.services.googlecalendar;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.users.User;

/**
 * @author Enric Hernando
 */
public interface GoogleCalendarService {
    /**
     * Add a event to a pet.
     * @param pet The pet
     * @param event The event
     */
    void registerNewEvent(Pet pet, Event event);

    /**
     * Delete a event from a pet.
     * @param pet The pet
     * @param event The event
     */
    void deleteEvent(Pet pet, Event event);

    /**
     * Add a new calendar for a pet.
     * @param pet The pet
     */
    void newSecondaryCalendar(Pet pet);

    /**
     * Add a periodic event to pet.
     * @param user The user that wants to add a periodic event
     * @param pet The pet which the user wants to add a periodic event
     * @param event The period event
     * @param period The periodicity of the period event
     */
    void registerNewPeriodicNotification(User user, Pet pet, Event event, int period);

    /**
     * Delete a periodic event of pet.
     * @param user The user that wants to delete a periodic event
     * @param pet The pet which the user wants to delete a periodic event
     * @param event The period event that user wants to delete
     */
    void deletePeriodicEvent(User user, Pet pet, Event event);
}

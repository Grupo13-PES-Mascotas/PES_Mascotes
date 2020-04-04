package org.pesmypetcare.mypetcare.activities.fragments.calendar;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;

public interface CalendarCommunication {

    /**
     * Get the user.
     * @return The user that has done the log in
     */
    User getUser();

    /**
     * Passes the information to create a new personal event to the main activity.
     * @param pet The pet of the event
     * @param description The description of the event
     * @param dateTime The date and time of the event
     */
    void newPersonalEvent(Pet pet, String description, String dateTime);

    /**
     * Passes the information to delete a personal event to the main activity.
     * @param pet The pet of the event
     * @param event The event
     */
    void deletePersonalEvent(Pet pet, Event event);
}

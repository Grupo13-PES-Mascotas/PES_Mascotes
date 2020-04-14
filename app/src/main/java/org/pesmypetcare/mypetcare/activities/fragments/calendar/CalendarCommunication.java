package org.pesmypetcare.mypetcare.activities.fragments.calendar;

import android.content.Context;

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

    void newPeriodicNotification(Pet selectedPet, int periodicity, String toString, String toString1);

    void schedulePeriodicNotification(Context context, long timeInMillis, String pet, String toString, int period);
}

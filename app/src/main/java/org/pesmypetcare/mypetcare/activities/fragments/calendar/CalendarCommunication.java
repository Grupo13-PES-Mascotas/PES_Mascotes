package org.pesmypetcare.mypetcare.activities.fragments.calendar;

import android.content.Context;

import org.pesmypetcare.mypetcare.features.notification.Notification;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

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
    void newPersonalEvent(Pet pet, String description, String dateTime) throws ExecutionException, InterruptedException,
            InvalidFormatException;

    /**
     * Passes the information to delete a personal event to the main activity.
     * @param pet The pet of the event
     * @param event The event
     */
    void deletePersonalEvent(Pet pet, Event event) throws ExecutionException, InterruptedException;

    /**
     * Creates a notification.
     * @param context The context
     * @param time The time of the notification
     * @param title The notification's title
     * @param text The notification's text
     */
    void scheduleNotification(Context context, long time, String title, String text);

    /**
     * Cancel a notification.
     * @param context The context
     * @param notification The notification
     */
    void cancelNotification(Context context, Notification notification);

    /**
     * Add a new periodic notification.
     * @param selectedPet The pet whom the user wants to add a periodic notification
     * @param periodicity The periodicity of the event
     * @param reasonText The description of the event
     * @param dateTime The date of the event
     */
    void newPeriodicNotification(Pet selectedPet, int periodicity, String reasonText, DateTime dateTime)
            throws ParseException, UserIsNotOwnerException, ExecutionException, InterruptedException;

    /**
     * Delete a periodic notification.
     * @param selectedPet The pet whom the user wants to delete a periodic notification
     * @param event The event that the user wants to delete
     * @param user The user that wants to delete a periodic notification
     */
    void deletePeriodicNotification(Pet selectedPet, Event event, User user)
            throws ParseException, UserIsNotOwnerException, ExecutionException, InterruptedException;

    /**
     * Creates a periodic notification.
     * @param context The context
     * @param timeInMillis The time of the notification
     * @param name The notification's title
     * @param toString The notification's text
     * @param period The periodicity of the event
     */
    void schedulePeriodicNotification(Context context, long timeInMillis, String name, String toString, int period);
}

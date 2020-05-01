package org.pesmypetcare.mypetcare.controllers.event;

import org.pesmypetcare.mypetcare.services.GoogleCalendarAdapter;

/**
 * @author Albert Pinto
 */
public class EventControllersFactory {
    private EventControllersFactory() {
        // Private constructor
    }

    /**
     * Create the transaction for deleting a personal event.
     * @return The transaction for deleting a personal event
     */
    public static TrDeletePersonalEvent createTrDeletePersonalEvent() {
        return new TrDeletePersonalEvent(new GoogleCalendarAdapter());
    }

    /**
     * Create the transaction for creating a new personal event.
     * @return The transaction for creating a new personal event
     */
    public static TrNewPersonalEvent createTrNewPersonalEvent() {
        return new TrNewPersonalEvent(new GoogleCalendarAdapter());
    }

    /**
     * Create the transaction for adding a new periodic notification.
     * @return The transaction for adding a new periodic notification
     */
    public static TrNewPeriodicNotification createTrNewPeriodicNotification() {
        return new TrNewPeriodicNotification(new GoogleCalendarAdapter());
    }

    /**
     * Create the transaction for deleting a periodic notification.
     * @return The transaction for deleting a periodic notification
     */
    public static TrDeletePeriodicNotification createTrDeletePeriodicNotification() {
        return new TrDeletePeriodicNotification(new GoogleCalendarAdapter());
    }
}

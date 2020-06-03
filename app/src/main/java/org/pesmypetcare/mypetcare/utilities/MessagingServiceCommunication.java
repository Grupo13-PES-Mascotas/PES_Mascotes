package org.pesmypetcare.mypetcare.utilities;

/**
 * @author Albert Pinto
 */
public interface MessagingServiceCommunication extends MessagingTokenServiceCommunication {

    /**
     * Schedule a post notification.
     * @param title The title of the notification
     * @param text The text of the notification
     * @param time The time to display the notification
     */
    void schedulePostNotification(String title, String text, long time);
}

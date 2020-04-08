package org.pesmypetcare.mypetcare.features.notification;

import org.pesmypetcare.mypetcare.features.users.User;

import java.util.Date;
import java.util.Objects;

public class Notification {
    private String title;
    private String description;
    private Date date;
    private String hour;
    private int notificationID;
    private int requestCode;
    private User user;

    public Notification(String title, String description, Date date, String hour) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.hour = hour;
    }

    public Notification(String description, Date date, String title) {
        this.description = description;
        this.date = date;
        this.title = title;
    }

    /**
     * Setter of the notificationID attribute.
     * @param notificationID The notificationID
     */
    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    /**
     * Setter of the requestCode attribute.
     * @param requestCode The requestCode
     */
    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    /**
     * Getter of the notificationID attribute.
     * @return The notificationID
     */
    public int getNotificationID() {
        return notificationID;
    }

    /**
     * Getter of the requestCode attribute.
     * @return The requestCode
     */
    public int getRequestCode() {
        return requestCode;
    }

    /**
     * Setter of the user attribute.
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter of the text attribute.
     * @return The text
     */
    public String getText() {
        return this.description;
    }

    /**
     * Getter of the title attribute.
     * @return The title
     */
    public String getTitle() {
        return this.title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notification notification = (Notification) o;
        return Objects.equals(description, notification.description)
                && Objects.equals(date.toString(), notification.date.toString())
                && Objects.equals(title, notification.title);
    }
}

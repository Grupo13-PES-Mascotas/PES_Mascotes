package org.pesmypetcare.mypetcare.features.notification;

import org.pesmypetcare.mypetcare.features.users.User;

import java.util.Date;
import java.util.Objects;

public class Notification {
    private String title;
    private String description;
    private Date date;
    private String hour;
    private int NOTIFICATION_ID;
    private int REQUEST_CODE;
    private User user;

    public Notification(String title, String description, Date date, String hour, int id, int req){
        this.title = title;
        this.description = description;
        this.date = date;
        this.hour = hour;
        this.NOTIFICATION_ID = id;
        this.REQUEST_CODE = req;
    }

    public Notification(String description, Date date, String title) {
        this.description = description;
        this.date = date;
        this.title = title;
    }

    /**
     * Getter of the NOTIFICATION_ID attribute.
     * @return The NOTIFICATION_ID
     */
    public int getNOTIFICATION_ID() {
        return NOTIFICATION_ID;
    }

    /**
     * Getter of the REQUEST_CODE attribute.
     * @return The REQUEST_CODE
     */
    public int getREQUEST_CODE() {
        return REQUEST_CODE;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification notification = (Notification) o;
        return Objects.equals(description, notification.description)
                && Objects.equals(date.toString(), notification.date.toString())
                && Objects.equals(title, notification.title);
    }
}

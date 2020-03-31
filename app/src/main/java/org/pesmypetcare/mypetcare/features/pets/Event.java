package org.pesmypetcare.mypetcare.features.pets;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {
    private String description;
    private String dateTime;

    public Event(String description, String dateTime) {
        this.description = description;
        this.dateTime = dateTime;
    }

    /**
     * Get the description.
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description.
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the dateTime.
     * @return The getTime.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Set the dateTime.
     * @param dateTime The datetime to set.
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(dateTime, event.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime);
    }
}

package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.httptools.utilities.DateTime;

import java.util.Objects;

public class Event {
    private String description;
    private DateTime dateTime;

    public Event(String description, DateTime dateTime) {
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
    public DateTime getDateTime() {
        return dateTime;
    }

    /**
     * Set the dateTime.
     * @param dateTime The datetime to set.
     */
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Check the type of the event.
     * @return True if is periodic, false otherwise
     */
    public boolean isPeriodic() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event event = (Event) o;
        return dateTime.compareTo(event.dateTime) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime);
    }
}

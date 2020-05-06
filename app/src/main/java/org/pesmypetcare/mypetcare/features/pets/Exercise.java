package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.usermanager.datacontainers.DateTime;

/**
 * @author Albert Pinto
 */
public class Exercise extends Event {
    private String name;
    private DateTime endTime;

    public Exercise(String name, String description, DateTime dateTime, DateTime endTime) {
        super(description, dateTime);
        this.name = name;
        this.endTime = endTime;
    }

    /**
     * Get the name.
     * @return The name to get
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the name.
     * @return The name to get
     */
    public DateTime getEndTime() {
        return endTime;
    }

    /**
     * Set the end time.
     * @param endTime The end time to set
     */
    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "{" + getName() + ", " + getDescription() + ", " + getDateTime() + ", " + getEndTime() + "}";
    }
}

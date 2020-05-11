package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

/**
 * @author Enric Hernando
 */
public class Illness extends Event {
    private DateTime endTime;
    private String type;
    private String severity;
    
    public Illness(String description, DateTime dateTime, DateTime endTime, String type, String severity) {
        super(description, dateTime);
        this.endTime = endTime;
        this.type = type;
        this.severity = severity;
    }

    /**
     * Getter of the endTime attribute.
     * @return The endTime
     */
    public DateTime getEndTime() {
        return endTime;
    }

    /**
     * Getter of the endTime attribute.
     * @param endTime The endTime
     */
    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Getter of the type of the illness.
     * @return The type of the illness
     */
    public String getType() {
        return type;
    }

    /**
     * Setter of the duration of the illness.
     * @param type The new duration of the illness
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter of the severity of the illness.
     * @return The severity of the illness
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * Setter of the duration of the illness.
     * @param type The new duration of the illness
     */
    public void setSeverity(String type) {
        this.severity = severity;
    }
}

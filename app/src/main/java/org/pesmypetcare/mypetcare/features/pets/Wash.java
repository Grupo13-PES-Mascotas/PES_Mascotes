package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.usermanager.datacontainers.DateTime;

/**
 * @author Enric Hernando
 */
public class Wash extends Event{
    private static final String WASH = "Wash ";
    private static final String OF_THE_DAY = " of the day ";
    private String washName;
    private int duration;
    private DateTime washDate;

    public Wash(DateTime dateTime, int duration, String washName) {
        super(WASH + washName + OF_THE_DAY + dateTime.toString(), dateTime);
        this.washDate = dateTime;
        this.washName = washName;
        this.duration = duration;
    }
/*
    public Wash(Wash wash) {
        super(WASH + wash.getBody().getWashName() + OF_THE_DAY + wash.getDate(),
                DateTime.Builder.buildFullString(wash.getDate()));
        this.washDate = DateTime.Builder.buildFullString(meal.getDate());
        this.washName = wash.getBody().getMealName();
        this.duration = wash.getBody().getDuration();
    }

 */

    /**
     * Getter of the washName attribute.
     * @return The name of the meal
     */
    public String getWashName() {
        return washName;
    }

    /**
     * Setter of the washName attribute.
     * @param washName The new name of the wash
     */
    public void setWashName(String washName) {
        this.washName = washName;
    }

    /**
     * Getter of the duration of the wash.
     * @return The duration of the wash
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Setter of the duration of the wash.
     * @param duration The new duration of the wash
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Getter of the date of the wash.
     * @return The date of the wash
     */
    public DateTime getWashDate() {
        return washDate;
    }

    /**
     * Setter of the date of the wash.
     * @param washDate The new date of the wash
     */
    public void setWashDate(DateTime washDate) {
        super.setDateTime(washDate);
        this.washDate = washDate;
    }
}

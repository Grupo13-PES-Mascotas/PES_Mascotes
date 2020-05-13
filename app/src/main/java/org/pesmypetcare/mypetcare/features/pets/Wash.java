package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

/**
 * @author Enric Hernando
 */
public class Wash extends Event {
    private static final String WASH = "Wash ";
    private static final String OF_THE_DAY = " of the day ";
    private String washDescription;
    private int duration;
    private DateTime washDate;

    public Wash(DateTime dateTime, int duration, String washDescription) {
        super(WASH + washDescription + OF_THE_DAY + dateTime.toString(), dateTime);
        this.washDate = dateTime;
        this.washDescription = washDescription;
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
    public String getWashDescription() {
        return washDescription;
    }

    /**
     * Setter of the washName attribute.
     * @param washDescription The new name of the wash
     */
    public void setWashDescription(String washDescription) {
        this.washDescription = washDescription;
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

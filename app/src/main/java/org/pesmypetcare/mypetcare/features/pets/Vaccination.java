package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

/**
 * @author Enric Hernando
 */
public class Vaccination extends Event{
    private static final String VACCINATION = "Vaccination ";
    private static final String OF_THE_DAY = " of the day ";
    private String description;
    private DateTime vaccinationDate;

    public Vaccination(DateTime dateTime, String description) {
        super(VACCINATION + description + OF_THE_DAY + dateTime.toString(), dateTime);
        this.vaccinationDate = dateTime;
        this.description = description;
    }

    /**
     * Getter of the description attribute.
     * @return The description of the vaccination
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter of the description attribute.
     * @param description The new name of the vaccination
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter of the date of the vaccination.
     * @return The date of the vaccination
     */
    public DateTime getDate() {
        return vaccinationDate;
    }

    /**
     * Setter of the date of the vaccination.
     * @param vaccinationDate The new date of the vaccination
     */
    public void setDate(DateTime vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }
}

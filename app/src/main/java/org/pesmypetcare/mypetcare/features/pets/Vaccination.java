package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

/**
 * @author Xavier Campos
 */
public class Vaccination extends Event{
    private static final String SPACE = " ";
    private DateTime vaccinationDate;
    private String vaccinationDescription;

    public Vaccination(String description, DateTime dateTime) {
        super(R.string.vaccination_date + SPACE + dateTime.toString() + " ."
            + R.string.vaccination_description + SPACE + description, dateTime);
        this.vaccinationDescription = description;
        this.vaccinationDate = dateTime;
    }

    /**
     * Getter of the date of the vaccination.
     * @return The date of the vaccination
     */
    public DateTime getVaccinationDate() {
        return vaccinationDate;
    }

    /**
     * Setter of the date of the vaccination.
     * @param vaccinationDate The date of the vaccination to set
     */
    public void setVaccinationDate(DateTime vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
        super.setDateTime(vaccinationDate);
        super.setDescription(R.string.vaccination_date + SPACE + vaccinationDate.toString() + " ."
            + R.string.vaccination_description + SPACE + vaccinationDescription);
    }

    /**
     * Getter of the description of the vaccination.
     * @return The description of the vaccination
     */
    public String getVaccinationDescription() {
        return vaccinationDescription;
    }

    /**
     * Setter of the description of the vaccination.
     * @param vaccinationDescription The description of the vaccination to set
     */
    public void setVaccinationDescription(String vaccinationDescription) {
        this.vaccinationDescription = vaccinationDescription;
        super.setDescription(vaccinationDescription);
        super.setDescription(R.string.vaccination_date + SPACE + vaccinationDate.toString() + " ."
            + R.string.vaccination_description + SPACE + vaccinationDescription);
    }
}

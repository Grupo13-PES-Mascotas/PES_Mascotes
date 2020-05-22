package org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.vaccination;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.events.Event;

/**
 * @author Xavier Campos & Enric Hernando Puerta
 */
public class Vaccination extends Event {
    private DateTime vaccinationDate;

    public Vaccination(String description, DateTime dateTime) {
        super(description, dateTime);
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
    }
}

package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

/**
 * @author Xavier Campos
 */
public class Medication extends Event {
    private static final String MEDICATION = "Medication ";
    private static final String WITH_START_DATE = " with start date ";
    private String medicationName;
    private int medicationQuantity;
    private double medicationFrequency;
    private int medicationDuration;
    private DateTime medicationDate;

    public Medication(String medicationName, int medicationQuantity, double medicationFrequency,
                      int medicationDuration, DateTime medicationDate) {
        super(MEDICATION + medicationName + WITH_START_DATE + medicationName, medicationDate.toString());
        this.medicationName = medicationName;
        this.medicationQuantity = medicationQuantity;
        this.medicationFrequency = medicationFrequency;
        this.medicationDuration = medicationDuration;
        this.medicationDate = medicationDate;
    }

    /**
     * Getter of the medicationName attribute.
     * @return The name of the medication
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Setter of the medicationName attribute.
     * @param medicationName The new name of the medication to set
     */
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    /**
     * Getter of the medicationQuantity attribute.
     * @return The quantity of the medication
     */
    public int getMedicationQuantity() {
        return medicationQuantity;
    }

    /**
     * Setter of the medicationQuantity attribute.
     * @param medicationQuantity The new quantity of medication to set
     */
    public void setMedicationQuantity(int medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }

    /**
     * Getter of the medicationFrequency attribute.
     * @return The frequency of the medication
     */
    public double getMedicationFrequency() {
        return medicationFrequency;
    }

    /**
     * Setter of the medicationFrequency attribute.
     * @param medicationFrequency The new medication frequency to set
     */
    public void setMedicationFrequency(double medicationFrequency) {
        this.medicationFrequency = medicationFrequency;
    }

    /**
     * Getter of the medicationDuration attribute.
     * @return The duration of the medication
     */
    public int getMedicationDuration() {
        return medicationDuration;
    }

    /**
     * Setter of the medicationDuration attribute.
     * @param medicationDuration The new medication duration to set
     */
    public void setMedicationDuration(int medicationDuration) {
        this.medicationDuration = medicationDuration;
    }

    /**
     * Getter of the medicationDate attribute.
     * @return The date of the medication
     */
    public DateTime getMedicationDate() {
        return medicationDate;
    }

    /**
     * Setter of the medicationDate attribute.
     * @param medicationDate The new medication date to set
     */
    public void setMedicationDate(DateTime medicationDate) {
        this.medicationDate = medicationDate;
    }
}

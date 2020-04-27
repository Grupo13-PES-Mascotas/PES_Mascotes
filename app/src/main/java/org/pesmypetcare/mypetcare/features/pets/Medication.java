package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.usermanager.datacontainers.DateTime;

/**
 * @author Xavier Campos
 */
public class Medication extends Event {
    private static final String MEDICATION = "Medication ";
    private static final String WITH_START_DATE = " with start date ";
    private String medicationName;
    private double medicationQuantity;
    private int medicationFrequency;
    private int medicationDuration;
    private DateTime medicationDate;

    public Medication(String medicationName, double medicationQuantity, int medicationFrequency,
                      int medicationDuration, DateTime medicationDate) {
        super(MEDICATION + medicationName + WITH_START_DATE + medicationDate.toString(), medicationDate);
        this.medicationName = medicationName;
        this.medicationQuantity = medicationQuantity;
        this.medicationFrequency = medicationFrequency;
        this.medicationDuration = medicationDuration;
        this.medicationDate = medicationDate;
    }

    public Medication(org.pesmypetcare.usermanager.datacontainers.pet.Medication libraryMedication) {
        super(MEDICATION + libraryMedication.getName() + WITH_START_DATE + libraryMedication.getDate(),
            DateTime.Builder.buildFullString(libraryMedication.getDate()));
        this.medicationName = libraryMedication.getName();
        this.medicationQuantity = libraryMedication.getBody().getQuantity();
        this.medicationFrequency = libraryMedication.getBody().getPeriodicity();
        this.medicationDuration = libraryMedication.getBody().getDuration();
        this.medicationDate = DateTime.Builder.buildFullString(libraryMedication.getDate());
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
    public double getMedicationQuantity() {
        return medicationQuantity;
    }

    /**
     * Setter of the medicationQuantity attribute.
     * @param medicationQuantity The new quantity of medication to set
     */
    public void setMedicationQuantity(double medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }

    /**
     * Getter of the medicationFrequency attribute.
     * @return The frequency of the medication
     */
    public int getMedicationFrequency() {
        return medicationFrequency;
    }

    /**
     * Setter of the medicationFrequency attribute.
     * @param medicationFrequency The new medication frequency to set
     */
    public void setMedicationFrequency(int medicationFrequency) {
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
        super.setDateTime(medicationDate);
        this.medicationDate = medicationDate;
    }
}

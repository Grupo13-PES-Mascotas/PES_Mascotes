package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.R;

/**
 * @author Xavier Campos
 */
public class VetVisit extends Event {
    private static final String SPACE = " ";
    private DateTime dateTime;
    private String address;
    private String reason;

    public VetVisit(DateTime dateTime, String address, String reason) {
        super(reason, dateTime);
        this.dateTime = dateTime;
        this.address = address;
        this.reason = reason;
    }

    /**
     * Getter of the date of the visit.
     * @return The date of the visit
     */
    public DateTime getVisitDate() {
        return dateTime;
    }

    /**
     * Setter of the date of the visit.
     * @param dateTime The datetime to set to the visit
     */
    public void setVisitDate(DateTime dateTime) {
        super.setDateTime(dateTime);
        super.setDescription(R.string.visit_of_the_day + SPACE + dateTime.toString() + SPACE + R.string.visit_reason
            + SPACE + reason + SPACE + R.string.visit_addres + SPACE + address);
        this.dateTime = dateTime;
    }

    /**
     * Getter of the address of the visit.
     * @return The address of the visit
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter of the address of the visit.
     * @param address The new address of the visit
     */
    public void setAddress(String address) {
        super.setDescription(R.string.visit_of_the_day + SPACE + dateTime.toString() + SPACE + R.string.visit_reason
            + SPACE + reason + SPACE + R.string.visit_addres + SPACE + address);
        this.address = address;
    }

    /**
     * Getter of the reason of the visit.
     * @return The reason of the visit
     */
    public String getReason() {
        return reason;
    }

    /**
     * Setter of the reason of the visit.
     * @param reason The new reason of the visit
     */
    public void setReason(String reason) {
        super.setDescription(R.string.visit_of_the_day + SPACE + dateTime.toString() + SPACE + R.string.visit_reason
            + SPACE + reason + SPACE + R.string.visit_addres + SPACE + address);
        this.reason = reason;
    }
}

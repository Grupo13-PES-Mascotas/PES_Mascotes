package org.pesmypetcare.mypetcare.features.pets.events.periodic;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.events.Event;

/**
 * @author Daniel Clemente
 */
public class PeriodEvent extends Event {
    private int period;

    public PeriodEvent(String description, DateTime dateTime, int period) {
        super(description, dateTime);
        this.period = period;
    }

    /**
     * Get the period.
     * @return The period.
     */
    public int getPeriod() {
        return period;
    }

    /**
     * Set the period.
     * @param period The datetime to set.
     */
    public void setPeriod(int period) {
        this.period = period;
    }

    /**
     * Check the type of the event.
     * @return True if is periodic, false otherwise
     */
    public boolean isPeriodic() {
        return true;
    }
}

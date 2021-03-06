package org.pesmypetcare.mypetcare.features.pets.events.exercise.walk;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.Exercise;

import java.util.List;

/**
 * @author Albert Pinto
 */
public class Walk extends Exercise {
    private List<LatLng> coordinates;

    public Walk(String name, String description, DateTime dateTime, DateTime endTime, List<LatLng> coordinates) {
        super(name, description, dateTime, endTime);
        this.coordinates = coordinates;
    }

    /**
     * Get the coordinates.
     * @return The coordinates
     */
    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    /**
     * Set the coordinates.
     * @param coordinates The coordinates to set
     */
    public void setCoordinates(List<LatLng> coordinates) {
        this.coordinates = coordinates;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" + getName() + ", " + getDescription() + ", " + getDateTime() + ", " + getEndTime() + ", "
            + getCoordinates() + "}";
    }
}

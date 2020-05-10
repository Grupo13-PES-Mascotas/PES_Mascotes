package org.pesmypetcare.mypetcare.features.pets;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.List;

/**
 * @author Albert Pinto
 */
public class Walking extends Exercise {
    private List<LatLng> coordinates;

    public Walking(String name, String description, DateTime dateTime, DateTime endTime, List<LatLng> coordinates) {
        super(name, description, dateTime, endTime);
        this.coordinates = coordinates;
    }

    public List<LatLng> getCoordinates() {
        return coordinates;
    }

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

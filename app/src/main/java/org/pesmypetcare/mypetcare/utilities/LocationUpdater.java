package org.pesmypetcare.mypetcare.utilities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

import org.pesmypetcare.mypetcare.activities.MainActivity;
import org.pesmypetcare.mypetcare.activities.fragments.walks.ActualWalkingCommunication;

import java.util.Objects;

/**
 * @author Daniel Clemente
 */
public class LocationUpdater {
    private static final int LAT = 0;
    private static final int LNG = 1;
    private static final int MIN_TIME = 5000;
    private static Context context;
    private static LocationManager locationManager;

    private static ActualWalkingCommunication communication;

    private static LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateCoordinates(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Not implemented
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Not implemented
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Not implemented
        }
    };

    private LocationUpdater() {
        // Private constructor
    }

    /**
     * Start a walk.
     */
    public static void startRoute() {
        updateLocation();
    }

    /**
     * Set the context.
     * @param context The context to set
     */
    public static void setContext(Context context) {
        LocationUpdater.context = context;
    }

    /**
     * End updating the location of the user.
     */
    public static void endRoute() {
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    /**
     * Get the current location of the user.
     * @return The current location
     */
    public static LatLng getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = Objects.requireNonNull(locationManager).getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String loc = Objects.requireNonNull(location).getLatitude() + " " + location.getLongitude();
        String[] splitPos = loc.split(" ");
        return new LatLng(Double.parseDouble(splitPos[LAT]), Double.parseDouble(splitPos[LNG]));
    }



    /**
     * Update the coordinates of the last location detected.
     * @param location Last location of the user detected
     */
    private static void updateCoordinates(Location location) {
        if (location != null) {
            communication.updateActualLocation(location.getLatitude(), location.getLongitude());
            MainActivity.updateLocation(location.getLatitude(), location.getLongitude());
        }
    }

    /**
     * Initialize the location manager to get updates about the current location.
     */
    private static void updateLocation() {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateCoordinates(location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, 0, locationListener);
    }

    /**
     * Set the communication.
     * @param communication The communication to set
     */
    public static void setCommunication(ActualWalkingCommunication communication) {
        LocationUpdater.communication = communication;
    }
}

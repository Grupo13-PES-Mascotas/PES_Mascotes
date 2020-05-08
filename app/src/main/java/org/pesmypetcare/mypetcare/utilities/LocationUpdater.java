package org.pesmypetcare.mypetcare.utilities;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Daniel Clemente
 */


public class LocationUpdater {
    private static Context context;
    private static Location location;
    private static List<Location> listLocation;
    private static LocationManager locationManager;


    public static void startRoute() {
        listLocation = new ArrayList<>();
        updateLocation();

    }

    public static void setContext(Context context) {
        LocationUpdater.context = context;
    }

    public static List<Location> endRoute() {
        if(locationManager != null) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.removeUpdates(locationListener);
            }
        }
        return listLocation;
    }

    static LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            updateCoordinates(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private static void updateCoordinates(Location location) {
        if (location != null) {
            listLocation.add(location);
        }
    }

    private static void updateLocation() {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager  = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateCoordinates(location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000, 0, locationListener);
    }
}

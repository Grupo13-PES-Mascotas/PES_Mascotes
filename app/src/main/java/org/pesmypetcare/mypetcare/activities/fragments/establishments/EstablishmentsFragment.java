package org.pesmypetcare.mypetcare.activities.fragments.establishments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import org.pesmypetcare.mypetcare.databinding.FragmentEstablishmentsBinding;
import org.pesmypetcare.mypetcare.utilities.LocationUpdater;

/**
 * @author Xavier Campos & Enric Hernando
 */
public class EstablishmentsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener,
    GoogleMap.OnInfoWindowCloseListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {
    private static final float ZOOM = 16.0f;
    private static final float TRANSPARENCY = 0.7f;
    private FragmentEstablishmentsBinding binding;
    private EstablishmentsCommunication communication;
    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEstablishmentsBinding.inflate(inflater, container, false);
        communication = (EstablishmentsCommunication) getActivity();

        mapView = binding.mapEstablishmentView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        binding.establishmentFilterScrollView.setAlpha(TRANSPARENCY);

        return binding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Called after the map is ready to be used
        this.googleMap = googleMap;
        LatLng currentLocation = LocationUpdater.getCurrentLocation();
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, ZOOM));
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnInfoWindowCloseListener(this);
        googleMap.setInfoWindowAdapter(this);
        googleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // Get the full content view (null for default)
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        // Get only the contents for the view (null for default)
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        // Called when the info window is clicked
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        // Called when the info window is closed
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        // Called when a polyline is clicked
    }
}

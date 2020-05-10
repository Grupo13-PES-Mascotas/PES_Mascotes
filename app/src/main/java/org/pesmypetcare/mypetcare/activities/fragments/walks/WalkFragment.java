package org.pesmypetcare.mypetcare.activities.fragments.walks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentWalkBinding;
import org.pesmypetcare.mypetcare.features.pets.Walk;
import org.pesmypetcare.mypetcare.utilities.LocationUpdater;

import java.util.List;
import java.util.Map;

public class WalkFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener {
    private static final float ZOOM = 16.0f;
    private FragmentWalkBinding binding;
    private MapView mapView;
    private GoogleMap googleMap;
    private Map<Polyline, Walk> polylines;
    private WalkCommunication communication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWalkBinding.inflate(inflater, container, false);
        communication = (WalkCommunication) getActivity();

        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return binding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng currentLocation = LocationUpdater.getCurrentLocation();
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, ZOOM));
        googleMap.setOnPolylineClickListener(this);

        List<Walk> walks = communication.getWalkingRoutes();

        for (Walk walk : walks) {
            PolylineOptions options = new PolylineOptions().clickable(true).addAll(walk.getCoordinates());
            Polyline polyline = googleMap.addPolyline(options);
            polyline.setStartCap(new RoundCap());
            polyline.setEndCap(new RoundCap());
            polyline.setColor(getResources().getColor(R.color.colorPrimary, null));
            polyline.setJointType(JointType.ROUND);
        }
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
    public void onPolylineClick(Polyline polyline) {
        polyline.setColor(getResources().getColor(R.color.green, null));
    }
}

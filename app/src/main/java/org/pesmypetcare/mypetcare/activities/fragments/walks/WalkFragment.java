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
import org.pesmypetcare.mypetcare.utilities.LocationUpdater;

public class WalkFragment extends Fragment implements OnMapReadyCallback {
    private static final float ZOOM = 16.0f;
    private FragmentWalkBinding binding;
    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWalkBinding.inflate(inflater, container, false);
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

        PolylineOptions options = new PolylineOptions().clickable(true).add(
            new LatLng(41.220208, 1.720938),
            new LatLng(41.219639, 1.721323),
            new LatLng(41.219537, 1.721221),
            new LatLng(41.219426, 1.721282),
            new LatLng(41.219392, 1.721411),
            new LatLng(41.219404, 1.721516),
            new LatLng(41.218867, 1.721867)
        );

        Polyline polyline = googleMap.addPolyline(options);
        polyline.setStartCap(new RoundCap());
        polyline.setEndCap(new RoundCap());
        polyline.setColor(getResources().getColor(R.color.colorPrimary, null));
        polyline.setJointType(JointType.ROUND);
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
}

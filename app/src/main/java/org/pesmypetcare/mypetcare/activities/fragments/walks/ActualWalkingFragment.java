package org.pesmypetcare.mypetcare.activities.fragments.walks;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetFragment;
import org.pesmypetcare.mypetcare.activities.fragments.infopet.InfoPetHealthFragment;
import org.pesmypetcare.mypetcare.databinding.FragmentActualWalkingBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Albert Pinto
 */
public class ActualWalkingFragment extends Fragment implements OnMapReadyCallback, ActualWalkingCommunication {
    private static final float ZOOM = 16.0f;
    private static final int REQUEST_INTERVAL = 10000;
    private static final int FASTEST_INTERVAL_REQUEST = 5000;
    private static Context context;
    private static LayoutInflater inflater;
    private static String errorMessage;
    private static FusedLocationProviderClient fusedLocationProviderClient;
    private static LocationRequest locationRequest;
    private static LocationCallback locationCallback;

    private FragmentActualWalkingBinding binding;
    private MapView mapView;
    private List<LatLng> coordinates;
    private Polyline actualPolyline;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentActualWalkingBinding.inflate(inflater, container, false);
        context = getContext();
        ActualWalkingFragment.inflater = inflater;
        errorMessage = getString(R.string.error_empty_input_field);
        mapView = binding.actualWalkingMapView;
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        coordinates = new ArrayList<>();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
            Objects.requireNonNull(getContext()));

        getLocationRequest();
        getLocationCallback();

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        binding.endWalkingButton.setOnClickListener(v -> showEndWalkDialog());

        return binding.getRoot();
    }

    /**
     * Get the location request.
     */
    private void getLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(REQUEST_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL_REQUEST);
    }

    /**
     * Get the location callback.
     */
    private void getLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    System.out.println("LATLNG " + latitude + " " + longitude);

                    MainActivity.updateLocation(latitude, longitude);
                    updateActualLocation(latitude, longitude);
                }
            }
        };
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

        if (fusedLocationProviderClient != null && locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(task.getResult().getLatitude(),
                task.getResult().getLongitude()), ZOOM));
        });

        //LocationUpdater.startRoute();
    }

    @Override
    public void updateActualLocation(double latitude, double longitude) {
        coordinates.add(new LatLng(latitude, longitude));

        if (actualPolyline != null) {
            actualPolyline.remove();
        }

        PolylineOptions options = new PolylineOptions().clickable(true).addAll(coordinates);
        actualPolyline = googleMap.addPolyline(options);
        actualPolyline.setStartCap(new RoundCap());
        actualPolyline.setEndCap(new RoundCap());
        actualPolyline.setJointType(JointType.ROUND);
        actualPolyline.setColor(getResources().getColor(R.color.colorPrimary, null));
    }

    /**
     * Create the end walking dialog.
     * @return The ned walking dialog
     */
    private static AlertDialog createEndDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(context),
            R.style.AlertDialogTheme);
        builder.setTitle(R.string.end_walking_title);
        builder.setMessage(R.string.end_walking_message);

        View endWalkingLayout = inflater.inflate(R.layout.end_walking, null);
        TextInputLayout inputWalkingName = endWalkingLayout.findViewById(R.id.inputWalkingName);
        TextInputLayout inputWalkingDescription = endWalkingLayout.findViewById(R.id.inputWalkingDescription);
        MaterialButton btnCancelWalking = endWalkingLayout.findViewById(R.id.cancelWalkingButton);
        MaterialButton btnEndWalking = endWalkingLayout.findViewById(R.id.endWalkingButton);

        builder.setView(endWalkingLayout);
        AlertDialog dialog = builder.create();

        setCancelWalkButtonListener(btnCancelWalking, dialog);
        setEndWalkListener(inputWalkingName, inputWalkingDescription, btnEndWalking, dialog);

        return dialog;
    }

    /**
     * Set the cancel button listener.
     * @param btnCancelWalking The cancel walking button
     * @param dialog The dialog
     */
    private static void setCancelWalkButtonListener(MaterialButton btnCancelWalking, AlertDialog dialog) {
        btnCancelWalking.setOnClickListener(v -> {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            InfoPetFragment.getCommunication().cancelWalking();
            dialog.dismiss();
        });
    }

    /**
     * Set the end walk listener.
     * @param inputWalkingName The input walking name layout
     * @param inputWalkingDescription The input walking description layout
     * @param btnEndWalking The button for ending the walk
     * @param dialog The dialog
     */
    private static void setEndWalkListener(TextInputLayout inputWalkingName, TextInputLayout inputWalkingDescription,
                                           MaterialButton btnEndWalking, AlertDialog dialog) {
        btnEndWalking.setOnClickListener(v -> addEndWalkListener(inputWalkingName, inputWalkingDescription, dialog));
    }

    /**
     * Add the end walk listener.
     * @param inputWalkingName The input walking name layout
     * @param inputWalkingDescription The input walking description layout
     * @param dialog The dialog
     */
    private static void addEndWalkListener(TextInputLayout inputWalkingName, TextInputLayout inputWalkingDescription,
                                           AlertDialog dialog) {
        String walkingName = Objects.requireNonNull(inputWalkingName.getEditText()).getText().toString();
        String walkingDescription = Objects.requireNonNull(inputWalkingDescription.getEditText()).getText()
            .toString();

        if ("".equals(walkingName)) {
            inputWalkingName.setErrorEnabled(true);
            inputWalkingName.setError(errorMessage);
        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            InfoPetFragment.getCommunication().endWalk(walkingName, walkingDescription);
            dialog.dismiss();
        }

        InfoPetHealthFragment.updateBarChart();
    }

    /**
     * Show the end walk dialog.
     */
    public static void showEndWalkDialog() {
        AlertDialog endWalkDialog = createEndDialog();
        endWalkDialog.show();
    }
}

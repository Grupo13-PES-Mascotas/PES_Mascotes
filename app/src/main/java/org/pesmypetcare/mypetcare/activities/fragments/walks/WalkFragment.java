package org.pesmypetcare.mypetcare.activities.fragments.walks;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.material.checkbox.MaterialCheckBox;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentWalkBinding;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.WalkPets;
import org.pesmypetcare.mypetcare.utilities.LocationUpdater;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WalkFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener,
    GoogleMap.OnInfoWindowCloseListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {
    private static final float ZOOM = 16.0f;
    private static final String LESS_THAN_A_MINUTE = "<1 min";
    private static final int NUM_DIFFERENT_COLORS = 14;
    private static final float TRANSPARENCY = 0.7f;
    private static final int HOUR_MINUTES = 60;

    private FragmentWalkBinding binding;
    private MapView mapView;
    private GoogleMap googleMap;
    private Map<Polyline, WalkPets> polylines;
    private WalkCommunication communication;
    private WalkPets selectedWalkPets;
    private int[] colors;
    private int nextColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWalkBinding.inflate(inflater, container, false);
        communication = (WalkCommunication) getActivity();
        polylines = new HashMap<>();

        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        initializeAvailableColors();

        binding.walkingRoutesFilterScrollView.setAlpha(TRANSPARENCY);

        return binding.getRoot();
    }

    /**
     * Initialize the available colors.
     */
    private void initializeAvailableColors() {
        colors = new int[]{
            getResources().getColor(R.color.colorPrimary, null), getResources().getColor(R.color.red, null),
            getResources().getColor(R.color.green, null), getResources().getColor(R.color.violet, null),
            getResources().getColor(R.color.orange, null), getResources().getColor(R.color.yellow, null),
            getResources().getColor(R.color.cyan, null), getResources().getColor(R.color.magenta, null),
            getResources().getColor(R.color.ocean_blue, null), getResources().getColor(R.color.aquamarine, null),
            getResources().getColor(R.color.turquoise, null), getResources().getColor(R.color.rose, null),
            getResources().getColor(R.color.spring_green, null), getResources().getColor(R.color.grey, null)
        };
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng currentLocation = LocationUpdater.getCurrentLocation();
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, ZOOM));
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnInfoWindowCloseListener(this);
        googleMap.setInfoWindowAdapter(this);
        googleMap.setOnInfoWindowClickListener(this);

        List<WalkPets> walkPetsList = communication.getWalkingRoutes();

        for (WalkPets walkPets : walkPetsList) {
            addWalkRoute(walkPets);
        }
    }

    /**
     * Add the walk route.
     * @param walkPets The walk route
     */
    private void addWalkRoute(WalkPets walkPets) {
        Polyline polyline = createPolyline(walkPets.getWalk().getCoordinates());
        polylines.put(polyline, walkPets);

        MaterialCheckBox checkBox = new MaterialCheckBox(Objects.requireNonNull(getContext()));
        checkBox.setText(walkPets.getWalk().getName());
        checkBox.setChecked(true);
        checkBox.setTextColor(getResources().getColor(R.color.black, null));
        checkBox.setButtonTintList(ColorStateList.valueOf(colors[nextColor]));
        nextColor = (nextColor + 1) % NUM_DIFFERENT_COLORS;

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> setCheckboxListener(polyline, isChecked));

        binding.walkingRoutesFilterLayout.addView(checkBox);
    }

    /**
     * Set the listener for the checkbox.
     * @param polyline The polyline
     * @param isChecked The state of the checkbox
     */
    private void setCheckboxListener(Polyline polyline, boolean isChecked) {
        if (isChecked) {
            polyline.setVisible(true);
            polyline.setClickable(true);
        } else {
            polyline.setVisible(false);
            polyline.setClickable(false);
        }
    }

    /**
     * Create a polyline.
     * @param coordinates The coordinates of the polyline
     * @return The polyline
     */
    private Polyline createPolyline(List<LatLng> coordinates) {
        PolylineOptions options = new PolylineOptions().clickable(true).addAll(coordinates);
        Polyline polyline = googleMap.addPolyline(options);
        polyline.setStartCap(new RoundCap());
        polyline.setEndCap(new RoundCap());
        polyline.setColor(colors[nextColor]);
        polyline.setJointType(JointType.ROUND);

        return polyline;
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
        List<LatLng> points = polyline.getPoints();
        LatLng middlePoint = points.get(points.size() / 2);

        MarkerOptions options = new MarkerOptions().position(middlePoint).title("Test title").snippet("Test snippet");
        Marker marker = googleMap.addMarker(options);
        selectedWalkPets = polylines.get(polyline);
        marker.showInfoWindow();
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        marker.remove();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View walkRoute = getLayoutInflater().inflate(R.layout.walk_route, null);
        TextView name = walkRoute.findViewById(R.id.walkRouteName);
        name.setText(selectedWalkPets.getWalk().getName());

        TextView description = walkRoute.findViewById(R.id.walkRouteDescription);
        description.setText(selectedWalkPets.getWalk().getDescription());
        setDuration(walkRoute);

        return walkRoute;
    }

    /**
     * Set the duration.
     * @param walkRoute The walk route
     */
    private void setDuration(View walkRoute) {
        TextView duration = walkRoute.findViewById(R.id.walkRouteDuration);
        int minutes = getMinutes(selectedWalkPets.getWalk().getDateTime(), selectedWalkPets.getWalk().getEndTime());

        if (minutes < 0) {
            duration.setText(LESS_THAN_A_MINUTE);
        } else {
            String strMinutes = minutes + " min";
            duration.setText(strMinutes);
        }
    }

    /**
     * Get the minutes duration.
     * @param startDateTime The start DateTime
     * @param endDateTime The end DateTime
     * @return The duration in minutes
     */
    private int getMinutes(DateTime startDateTime, DateTime endDateTime) {
        int startMinutes = startDateTime.getHour() * HOUR_MINUTES + startDateTime.getMinutes();
        int endMinutes = endDateTime.getHour() * HOUR_MINUTES + endDateTime.getMinutes();

        return endMinutes - startMinutes;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()),
            R.style.AlertDialogTheme);
        builder.setTitle(selectedWalkPets.getWalk().getName());
        builder.setMessage(selectedWalkPets.getWalk().getDescription());

        View selectedWalkRouteLayout = initializeWalkRouteLayout();
        builder.setView(selectedWalkRouteLayout);
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    /**
     * Initialize the walk route layout.
     * @return The walk route layout
     */
    @NonNull
    private View initializeWalkRouteLayout() {
        View selectedWalkRouteLayout = getLayoutInflater().inflate(R.layout.selected_walk_route, null);
        addWalkRouteInfo(selectedWalkRouteLayout);

        LinearLayout walkInfoPetLayout = selectedWalkRouteLayout.findViewById(R.id.walkRoutePetsLayout);

        for (Pet pet : selectedWalkPets.getPets()) {
            addActualPet(walkInfoPetLayout, pet);
        }

        return selectedWalkRouteLayout;
    }

    /**
     * Add the actual pet.
     * @param walkInfoPetLayout The layout of the walk route
     * @param pet The pet to add
     */
    private void addActualPet(LinearLayout walkInfoPetLayout, Pet pet) {
        TextView actualPet = new TextView(getContext());
        actualPet.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT));
        String petName = " " + pet.getName();
        actualPet.setText(petName);
        walkInfoPetLayout.addView(actualPet);
    }

    /**
     * Add the walk route info.
     * @param selectedWalkRouteLayout The layout to add the walk route info
     */
    private void addWalkRouteInfo(View selectedWalkRouteLayout) {
        DateTime startDateTime = selectedWalkPets.getWalk().getDateTime();
        DateTime endDateTime = selectedWalkPets.getWalk().getEndTime();
        String strDate = " " + startDateTime.toString().substring(0, startDateTime.toString().indexOf('T'));
        String strStartHour = " " + startDateTime.toString().substring(startDateTime.toString().indexOf('T') + 1);
        String strEndHour = " " + endDateTime.toString().substring(endDateTime.toString().indexOf('T') + 1);

        TextView date = selectedWalkRouteLayout.findViewById(R.id.walkRouteInfoDate);
        TextView startHour = selectedWalkRouteLayout.findViewById(R.id.walkRouteInfoStartHour);
        TextView endHour = selectedWalkRouteLayout.findViewById(R.id.walkRouteInfoEndHour);

        date.setText(strDate);
        startHour.setText(strStartHour);
        endHour.setText(strEndHour);
    }
}

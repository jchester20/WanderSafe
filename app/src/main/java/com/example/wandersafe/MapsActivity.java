package com.example.wandersafe;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Response;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // The entry point to the Fused Location Provider.
    public FusedLocationProviderClient mFusedLocationProviderClient;

    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;
    private Switch mapSwitch;
    private JSONArray api_call_response;
    private RequestQueue requestQueue;
    private ArrayList<Marker> markers;
    private HashMap<String, ArrayList> zones;
    private ArrayList<Circle> circles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Button searchBtn = (Button)findViewById(R.id.searchBtn);
        mapSwitch = (Switch)findViewById(R.id.mapSwitch);
        requestQueue = Volley.newRequestQueue(this);
        markers = new ArrayList<>();
        zones = new HashMap<>();
        circles = new ArrayList<>();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MapsActivity.this, SearchActivity.class);
                // myIntent.putExtra("key", value); //Optional parameters
                MapsActivity.this.startActivity(myIntent);
            }
        });
        mapSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    buildAPIQueue();
                    startPinMap();
                } else {
                    startHeatMap();
                }
            }
        });
    }

    private void setUpZones() {
        ArrayList<LatLng> highDanger = new ArrayList<>();
        highDanger.add(new LatLng(37.879602, -122.273783));
        zones.put("High Danger", highDanger);

        ArrayList<LatLng> danger = new ArrayList<>();
        danger.add(new LatLng(37.872291, -122.272302));
        danger.add(new LatLng(37.874118, -122.268620));
        zones.put("Danger", danger);

        ArrayList<LatLng> modSafe = new ArrayList<>();
        modSafe.add(new LatLng(37.875167, -122.272796));
        zones.put("Mod Safe", modSafe);

        ArrayList<LatLng> safe = new ArrayList<>();
        safe.add(new LatLng(37.865459, -122.269949));
        safe.add(new LatLng(37.865998, -122.265622));
        zones.put("Safe", safe);

        for (int i = 0; i < zones.get("High Danger").size(); i++) {
            CircleOptions circleOptions = new CircleOptions()
                    .center((LatLng) zones.get("High Danger").get(i))
                    .radius(160); // In meters
            Circle circle = mMap.addCircle(circleOptions);
            circle.setFillColor(getColor(R.color.colorHighDangerTrans));
            circle.setStrokeWidth(0);
            circle.setZIndex(10000);
            circles.add(circle);
        }

        for (int i = 0; i < zones.get("Danger").size(); i++) {
            CircleOptions circleOptions = new CircleOptions()
                    .center((LatLng) zones.get("Danger").get(i))
                    .radius(140); // In meters
            Circle circle = mMap.addCircle(circleOptions);
            circle.setFillColor(getColor(R.color.colorDangerTrans));
            circle.setStrokeWidth(0);
            circle.setZIndex(1000000);
            circles.add(circle);
        }

        for (int i = 0; i < zones.get("Mod Safe").size(); i++) {
            CircleOptions circleOptions = new CircleOptions()
                    .center((LatLng) zones.get("Mod Safe").get(i))
                    .radius(170); // In meters
            Circle circle = mMap.addCircle(circleOptions);
            circle.setFillColor(getColor(R.color.colorModSafeTrans));
            circle.setStrokeWidth(0);
            circle.setZIndex(10000);
            circles.add(circle);
        }

        for (int i = 0; i < zones.get("Safe").size(); i++) {
            CircleOptions circleOptions = new CircleOptions()
                    .center((LatLng) zones.get("Safe").get(i))
                    .radius(180); // In meters
            Circle circle = mMap.addCircle(circleOptions);
            circle.setFillColor(getColor(R.color.colorSafeTrans));
            circle.setStrokeWidth(0);
            circle.setZIndex(10000);
            circles.add(circle);
        }
    }

    private void startHeatMap() {
        for (Marker m : markers) {
            m.setVisible(false);
        }
        for (Circle c : circles) {
            c.setVisible(true);
        }
    }

    private void startPinMap() {
        for (Circle c : circles) {
            c.setVisible(false);
        }
        for (Marker m : markers) {
            m.setVisible(true);
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location)task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                // handle error
            }
        } catch (Resources.NotFoundException e) {
            Log.e("Error: ", e.toString());
        }

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
        mMap.setMyLocationEnabled(true);
        setUpZones();
    }

    protected void buildAPIQueue() {
        String url = "https://data.cityofberkeley.info/resource/k2nh-s5h5.json";
        System.out.println(url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //ADD FUNCTIONALITY HERE
                        api_call_response = response;
                        handleJSON();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        //add request to queue
        requestQueue.add(jsonArrayRequest);
    }

    protected void handleJSON() {
        for(int i = 0; i < Math.min(50, api_call_response.length()); i++) {
            try {
                JSONObject currLoc = api_call_response.getJSONObject(i);
                JSONObject loc = currLoc.getJSONObject("block_location");
                String lat = loc.getString("latitude");
                String lng = loc.getString("longitude");
                Marker toAdd = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));
                markers.add(toAdd);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

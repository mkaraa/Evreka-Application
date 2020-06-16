package com.example.evreka_application;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperatorActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ClusterManager<ContainerInfo> clusterManager;
    private List<ContainerInfo> items = new ArrayList<>();
    private Map<String, ContainerInfo> containers;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);

        containers = new HashMap<>();
        if (containers.size() < 1000) {
            readDataFromFirebase();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("ReadDataFirebase", "Map is full");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void readDataFromFirebase() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("id");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap<String, HashMap<String, Object>> values = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();

                for (String key : values.keySet()) {
                    HashMap<String, Object> stringStringHashMap = values.get(key);
                    ContainerInfo containerInfo = new ContainerInfo();
                    containerInfo.setContainerId(String.valueOf(stringStringHashMap.get("containerId")));
                    containerInfo.setLat(Double.valueOf(String.valueOf(stringStringHashMap.get("lat"))));
                    containerInfo.setLng(Double.valueOf(String.valueOf(stringStringHashMap.get("lng"))));
                    containerInfo.setSensorId((String) stringStringHashMap.get("sensorId"));
                    containerInfo.setTemperature(Integer.valueOf(String.valueOf(stringStringHashMap.get("temperature"))));
                    containerInfo.setFullnessRate(Integer.valueOf(String.valueOf(stringStringHashMap.get("fullnessRate"))));
                    containers.put(key, containerInfo);
                    System.out.println(containerInfo.getContainerId() + " is added with cords: " + containerInfo.getLat() + " and " + containerInfo.getLng());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("readDataFromFirebase_ERROR:", "Reading data occurs with errors");

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5.0f));

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);

        mMap.setMinZoomPreference(1.0f);
        mMap.setMaxZoomPreference(60.0f);

        for (String key : containers.keySet()) {
            new MarkerOptions()
                    .snippet("\n SensorId : "+containers.get(key).getSensorId()
                            +"\n Temperature :"+containers.get(key).getTemperature()
                            +"\n FullnessRate :"+containers.get(key).getFullnessRate())
                    .title(containers.get(key).getContainerId());
            items.add(containers.get(key));
        }


        clusterManager = new ClusterManager<>(this, mMap);
        clusterManager.clearItems();
        clusterManager.cluster();
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        clusterManager.addItems(items);

        mMap.resetMinMaxZoomPreference();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                items.add(new ContainerInfo(latLng));
                clusterManager.addItems(items);
                clusterManager.cluster();
            }
        });

    }

}
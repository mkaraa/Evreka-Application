package com.example.evreka_application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.AbstractAlgorithm;
import com.google.maps.android.clustering.algo.Algorithm;
import com.google.maps.android.clustering.algo.ScreenBasedAlgorithm;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class OperatorActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ClusterManager<ContainerInfo> clusterManager;
    private List<ContainerInfo> items = new ArrayList<>();

    private Map<String ,ContainerInfo> containers;
    private ContainerInfo container;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    public String sensorId;
    public int temperature,fullnessRate,containerId;
    public double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        containers = new HashMap<>();
        if (containers.size() < 1000){
            readDataFromFirebase();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("ReadDataFirebase","Map is full");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    protected boolean onMarkerClick(Marker marker) {
        //Log.i("GoogleMapActivity", "onMarkerClick");
        LatLng position = marker.getPosition();
        String idSelected= marker.getId();
/*  Toast.makeText(getApplicationContext(),
            "Marker Clicked: " + marker.getTitle(), Toast.LENGTH_LONG)
            .show();*/

        return false;
    }


    public void readDataFromFirebase(){
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
                        System.out.println(containerInfo.getContainerId() + " is added with cords: " + containerInfo.getLat() + " and " +  containerInfo.getLng());
                    }

//                for (int i = 0; i < 50; i++) {
//                        try {
//                            //System.out.println("CCCCCC" + container.getSensorId());
//                            sensorId = container.getSensorId();
//                            lat = container.getLat();
//                            lng = container.getLng();
//                            System.out.println(sensorId + lat + lng + " is added width i" + i);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Log.i("Firebase Data Not more data", "DB Read Finish");
//                        }
//                    }
               }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("readDataFromFirebase_ERROR:","Reading data occurs with errors");

        }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 5.0f ) );

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);

        mMap.setMinZoomPreference(1.0f);
        mMap.setMaxZoomPreference(30.0f);
        // Add a marker in Sydney and move the camera  -11.563930922631542
        //-17.777840839585593

        for(String key : containers.keySet()) {
            items.add(containers.get(key));
        }

        clusterManager = new ClusterManager<>(this,mMap);
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
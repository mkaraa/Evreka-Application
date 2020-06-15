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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

        readDataFromFirebase();

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

                try {

                }catch (Exception e){
                    e.printStackTrace();
                }
                container = dataSnapshot.getValue(ContainerInfo.class);
                //System.out.println("CCCCCC" + container.getSensorId());

                sensorId = container.getSensorId();

                lat = container.getLat();
                lng = container.getLng();

                containers.put(sensorId, new ContainerInfo(lat,lng));

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
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

        clusterManager = new ClusterManager<ContainerInfo>(this,mMap);
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);


        mMap.setMinZoomPreference(4.0f);
        mMap.setMaxZoomPreference(10.0f);
        // Add a marker in Sydney and move the camera  -11.563930922631542
        //-17.777840839585593

        for(int i = 0 ; i < 10 ; i++) {
            LatLng sydney = new LatLng(lat,lng);
            mMap.addMarker(
                    new MarkerOptions()
                            .position(sydney)
                            .title("Marker")
                            .alpha(0.8f)            //set opacity
                            .snippet("The old capital of the Australia")
                            .draggable(true)        //reposition
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.green))
                            .zIndex(1.0f)
            );

            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }



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
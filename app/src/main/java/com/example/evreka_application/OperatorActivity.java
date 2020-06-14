package com.example.evreka_application;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OperatorActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ClusterManager<MyItem> clusterManager;
    private List<MyItem> items = new ArrayList<>();

    //9.55, 122.52 asia continent latlng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

        clusterManager = new ClusterManager<MyItem>(this,mMap);
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);

        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(14.0f);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(
                new MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney")
                    .alpha(0.8f)            //set opacity
                    .snippet("The old capital of the Australia")
                    .draggable(true)        //reposition
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.green))
                    .zIndex(1.0f)
                );

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

       mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
           @Override
           public void onMapClick(LatLng latLng) {

               items.add(new MyItem(latLng));
               clusterManager.addItems(items);
               clusterManager.cluster();

           }
       });

    }
}
package com.example.evreka_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem {

    private LatLng position;
    private String title;
    private String snipped;

    public MyItem(LatLng position) {
        this.position = position;
    }

    public MyItem(LatLng position, String title, String snipped) {
        this.position = position;
        this.title = title;
        this.snipped = snipped;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return position;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return snipped;
    }
}

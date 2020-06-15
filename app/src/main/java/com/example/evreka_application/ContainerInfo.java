package com.example.evreka_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ContainerInfo implements ClusterItem {

    private String sensorId;
    private int temperature,fullnessRate,containerId;
    private double lat,lng;
    private LatLng position = new LatLng(lat,lng);


    public ContainerInfo(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public ContainerInfo(LatLng position) {
        this.position = position;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return position;
    }

    @Nullable
    @Override
    public String getTitle() {
        return sensorId;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return sensorId;
    }

    public ContainerInfo(String sensorId, int temperature, int fullnessRate, int containerId, double lat, double lng) {
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.fullnessRate = fullnessRate;
        this.containerId = containerId;
        this.lat = lat;
        this.lng = lng;
    }

    public ContainerInfo() {
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getFullnessRate() {
        return fullnessRate;
    }

    public void setFullnessRate(int fullnessRate) {
        this.fullnessRate = fullnessRate;
    }

    public int getContainerId() {
        return containerId;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}

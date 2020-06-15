package com.example.evreka_application;

public class ContainerInfo {

    private String sensorId;
    private int temperature,fullnessRate,containerId;
    private double lat,lng;

    public ContainerInfo(String sensorId, int temperature, int fullnessRate, int containerId, double lat, double lng) {
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.fullnessRate = fullnessRate;
        this.containerId = containerId;
        this.lat = lat;
        this.lng = lng;
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

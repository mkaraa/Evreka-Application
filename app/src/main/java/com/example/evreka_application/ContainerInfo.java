package com.example.evreka_application;

public class ContainerInfo {

    private String sensorId;
    private int temperature,fullnessRate,containerId;

    public ContainerInfo(String sensorId, int temperature, int fullnessRate, int containerId) {
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.fullnessRate = fullnessRate;
        this.containerId = containerId;
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
}

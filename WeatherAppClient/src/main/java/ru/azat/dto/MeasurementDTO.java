package ru.azat.dto;

public class MeasurementDTO {

    private float value;

    private boolean raining;

    private boolean sunny;

    private boolean cloudy;

    private SensorDTO sensorOwner;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public boolean isSunny() {
        return sunny;
    }

    public void setSunny(boolean sunny) {
        this.sunny = sunny;
    }

    public boolean isCloudy() {
        return cloudy;
    }

    public void setCloudy(boolean cloudy) {
        this.cloudy = cloudy;
    }

    public SensorDTO getSensorOwner() {
        return sensorOwner;
    }

    public void setSensorOwner(SensorDTO sensorOwner) {
        this.sensorOwner = sensorOwner;
    }
}

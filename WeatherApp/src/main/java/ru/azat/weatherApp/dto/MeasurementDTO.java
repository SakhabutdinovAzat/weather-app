package ru.azat.weatherApp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.azat.weatherApp.model.Sensor;

public class MeasurementDTO {

    @NotNull
    @Min(value = -100, message = "Value should be more -100")
    @Max(value = 100, message = "Value should be less 100")
    private float value;

    @NotNull
    private boolean raining;

    @NotNull
    private boolean sunny;

    @NotNull
    private boolean cloudy;

    @NotNull
    private Sensor sensorOwner;

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

    public Sensor getSensorOwner() {
        return sensorOwner;
    }

    public void setSensorOwner(Sensor sensorOwner) {
        this.sensorOwner = sensorOwner;
    }
}

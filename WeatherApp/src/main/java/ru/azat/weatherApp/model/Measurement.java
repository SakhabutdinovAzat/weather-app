package ru.azat.weatherApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurements")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = -100, message = "Value should be more -100")
    @Max(value = 100, message = "Value should be less 100")
    @Column(name = "value")
    private float value;

    @NotNull
    @Column(name = "raining")
    private boolean raining;

    @NotNull
    @Column(name = "sunny")
    private boolean sunny;

    @NotNull
    @Column(name = "cloudy")
    private boolean cloudy;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensorOwner;

    @Column(name = "add_at")
    private LocalDateTime addedAt;

    public Measurement() {
    }

    public Measurement(float value, boolean raining, boolean sunny, boolean cloudy) {
        this.value = value;
        this.raining = raining;
        this.sunny = sunny;
        this.cloudy = cloudy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}

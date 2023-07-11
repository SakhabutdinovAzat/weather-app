package ru.azat.weatherApp.util;

public class MeasurementNotAddedException extends RuntimeException{

    public MeasurementNotAddedException(String message) {
        super(message);
    }
}

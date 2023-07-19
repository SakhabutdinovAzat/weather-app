package ru.weather.app.util;

public class MeasurementNotAddedException extends RuntimeException{

    public MeasurementNotAddedException(String message) {
        super(message);
    }
}

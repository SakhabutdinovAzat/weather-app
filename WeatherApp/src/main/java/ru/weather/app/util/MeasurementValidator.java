package ru.weather.app.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.weather.app.model.Measurement;
import ru.weather.app.service.SensorsService;

@Component
public class MeasurementValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (measurement.getSensorOwner() == null) {
            return;
        }

        if(sensorsService.findByName(measurement.getSensorOwner().getName()).isEmpty()) {
            errors.rejectValue("sensor", "", "Sensor with this name not found");
        }
    }
}

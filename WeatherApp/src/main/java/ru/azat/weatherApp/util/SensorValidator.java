package ru.azat.weatherApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.azat.weatherApp.model.Sensor;
import ru.azat.weatherApp.service.SensorsService;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        // посмотреть есть ли такой сенсор в БД
        if(sensorsService.findByName(sensor.getName()).isPresent()){
            errors.rejectValue("name", "", "This sensor name is already taken");
        }

    }
}

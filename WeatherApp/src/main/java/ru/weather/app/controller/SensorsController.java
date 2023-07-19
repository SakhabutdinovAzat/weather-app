package ru.weather.app.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.weather.app.dto.SensorDTO;
import ru.weather.app.model.Sensor;
import ru.weather.app.service.SensorsService;
import ru.weather.app.util.SensorErrorResponse;
import ru.weather.app.util.SensorNotCreatedException;
import ru.weather.app.util.SensorNotFoundException;
import ru.weather.app.util.SensorValidator;

import java.util.List;
import java.util.stream.Collectors;

import static ru.weather.app.util.ErrorUtil.sensorErrorToClient;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    //@ResponseBody
    @GetMapping()
    public List<SensorDTO> getSensors(){
        return sensorsService.findAll().stream().map(this::convertToSensorDTO).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public SensorDTO getSensor(@PathVariable("id") int id){
        return convertToSensorDTO(sensorsService.findOne(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult) {
        Sensor sensorToRegistration = convertToSensor(sensorDTO);

        sensorValidator.validate(sensorToRegistration, bindingResult);

        if (bindingResult.hasErrors()) {
            sensorErrorToClient(bindingResult);
        }

        sensorsService.save(sensorToRegistration);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor with this id wasn't found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}

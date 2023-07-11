package ru.azat.weatherApp.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.azat.weatherApp.dto.SensorDTO;
import ru.azat.weatherApp.model.Sensor;
import ru.azat.weatherApp.service.SensorsService;
import ru.azat.weatherApp.util.SensorErrorResponse;
import ru.azat.weatherApp.util.SensorNotCreatedException;
import ru.azat.weatherApp.util.SensorNotFoundException;
import ru.azat.weatherApp.util.SensorValidator;

import java.util.List;
import java.util.stream.Collectors;

import static ru.azat.weatherApp.util.ErrorUtil.sensorErrorToClient;

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

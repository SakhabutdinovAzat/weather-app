package ru.azat.weatherApp.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.azat.weatherApp.dto.MeasurementDTO;
import ru.azat.weatherApp.model.Measurement;
import ru.azat.weatherApp.service.MeasurementsService;
import ru.azat.weatherApp.util.MeasurementErrorResponse;
import ru.azat.weatherApp.util.MeasurementNotAddedException;
import ru.azat.weatherApp.util.MeasurementNotFoundException;
import ru.azat.weatherApp.util.MeasurementValidator;

import java.util.List;
import java.util.stream.Collectors;

import static ru.azat.weatherApp.util.ErrorUtil.measurementErrorToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping()
    public List<MeasurementDTO> findAll() {
        return measurementsService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public MeasurementDTO findOne(@PathVariable("id") int id) {
        return convertToMeasurementDTO(measurementsService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {

        Measurement measurementToAdd = convertToMeasurement(measurementDTO);

        measurementValidator.validate(measurementToAdd, bindingResult);

        if (bindingResult.hasErrors()) {
            measurementErrorToClient(bindingResult);
        }

        measurementsService.save(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainingDays")
    public Long rainingDays(){
        return measurementsService.findAll().stream().filter(Measurement::isRaining).count();
    }

    @GetMapping("/sunnyDays")
    public Long sunnyDays(){
        return measurementsService.findAll().stream().filter(Measurement::isSunny).count();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotFoundException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                "Measurement not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotAddedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}

package ru.weather.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.weather.app.model.Measurement;
import ru.weather.app.repository.MeasurementsRepository;
import ru.weather.app.util.MeasurementNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    public Measurement findOne(int id){
        return measurementsRepository.findById(id).orElseThrow(MeasurementNotFoundException::new);
    }

    @Transactional
    public void save(Measurement measurement){
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setSensorOwner(sensorsService.findByName(measurement.getSensorOwner().getName()).get());
        measurement.setAddedAt(LocalDateTime.now());
    }
}

package ru.weather.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.weather.app.model.Measurement;

@Repository
public interface MeasurementsRepository  extends JpaRepository<Measurement, Integer> {
}

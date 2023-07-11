package ru.azat.weatherApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.azat.weatherApp.model.Measurement;

@Repository
public interface MeasurementsRepository  extends JpaRepository<Measurement, Integer> {
}

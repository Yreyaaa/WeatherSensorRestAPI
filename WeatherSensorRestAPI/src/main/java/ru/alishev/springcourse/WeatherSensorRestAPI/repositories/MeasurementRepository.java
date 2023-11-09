package ru.alishev.springcourse.WeatherSensorRestAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.WeatherSensorRestAPI.models.Measurement;

/**
 * @author Neil Alishev
 */
@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {


}

package ru.alishev.springcourse.WeatherSensorRestAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.WeatherSensorRestAPI.models.Sensor;

import java.util.Optional;

/**
 * @author Neil Alishev
 */
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    Optional<Sensor> getSensorByName(String fullName);


}

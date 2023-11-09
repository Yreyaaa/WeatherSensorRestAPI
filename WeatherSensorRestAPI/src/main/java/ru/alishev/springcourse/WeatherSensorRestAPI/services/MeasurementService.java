package ru.alishev.springcourse.WeatherSensorRestAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.WeatherSensorRestAPI.models.Measurement;
import ru.alishev.springcourse.WeatherSensorRestAPI.repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Neil Alishev
 */
@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;


    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;

    }

    private void enrichSensor(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichSensor(measurement);
        measurementRepository.save(measurement);
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }


}

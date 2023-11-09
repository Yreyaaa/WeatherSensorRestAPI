package ru.alishev.springcourse.WeatherSensorRestAPI.util;

import dto.MeasurementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.WeatherSensorRestAPI.models.Measurement;
import ru.alishev.springcourse.WeatherSensorRestAPI.services.SensorService;


/**
 * @author Neil Alishev
 */
@Component
public class MeasurementValidator implements Validator {

    //private final PersonDAO personDAO;
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Measurement.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) o;

        if (!sensorService.getSensorByName(measurementDTO.getSensor().getName()).isPresent())
            errors.rejectValue("sensor", "", "Сенсора с таким именем не существует");
    }
}

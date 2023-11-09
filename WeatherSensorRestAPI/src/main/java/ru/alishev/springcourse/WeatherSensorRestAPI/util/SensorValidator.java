package ru.alishev.springcourse.WeatherSensorRestAPI.util;

import dto.SensorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.WeatherSensorRestAPI.models.Sensor;
import ru.alishev.springcourse.WeatherSensorRestAPI.services.SensorService;


/**
 * @author Neil Alishev
 */
@Component
public class SensorValidator implements Validator {

    //private final PersonDAO personDAO;
    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) o;

        if (sensorService.getSensorByName(sensorDTO.getName()).isPresent())
            errors.rejectValue("name", "", "Сенсор с таким именем уже существует");
    }
}

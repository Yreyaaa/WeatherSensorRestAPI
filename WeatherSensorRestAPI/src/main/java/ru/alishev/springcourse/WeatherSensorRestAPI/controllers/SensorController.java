package ru.alishev.springcourse.WeatherSensorRestAPI.controllers;

import dto.SensorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.WeatherSensorRestAPI.models.Sensor;
import ru.alishev.springcourse.WeatherSensorRestAPI.services.SensorService;
import ru.alishev.springcourse.WeatherSensorRestAPI.util.PersonErrorResponse;
import ru.alishev.springcourse.WeatherSensorRestAPI.util.SensorNotCreatedException;
import ru.alishev.springcourse.WeatherSensorRestAPI.util.SensorValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Neil Alishev
 */
@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {

        sensorValidator.validate(sensorDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            String errorMsg;
            List<FieldError> errors = bindingResult.getFieldErrors();
            errorMsg = errors.stream().map(error -> error.getField() + " - " + error.getDefaultMessage() + ";").collect(Collectors.joining());
            throw new SensorNotCreatedException(errorMsg);

        }
        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {


        return modelMapper.map(sensorDTO, Sensor.class);
    }


    @GetMapping()
    public List<SensorDTO> getSensorDTO() {
        return sensorService.findAll().stream().map(this::convertToSensorDTO).collect(Collectors.toList()); // Jackson конвертирует эти объекты в JSON
    }


    @ExceptionHandler()
    private ResponseEntity<PersonErrorResponse> handleException(SensorNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public SensorDTO getSensorDTO(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorService.findOne(id)); // Jackson конвертирует в JSON
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }

}


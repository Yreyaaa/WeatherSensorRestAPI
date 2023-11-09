package ru.alishev.springcourse.WeatherSensorRestAPI.controllers;

import dto.MeasurementDTO;
import dto.SensorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.WeatherSensorRestAPI.models.Measurement;
import ru.alishev.springcourse.WeatherSensorRestAPI.services.MeasurementService;
import ru.alishev.springcourse.WeatherSensorRestAPI.services.SensorService;
import ru.alishev.springcourse.WeatherSensorRestAPI.util.MeasurementValidator;
import ru.alishev.springcourse.WeatherSensorRestAPI.util.MeasurementsResponse;
import ru.alishev.springcourse.WeatherSensorRestAPI.util.PersonErrorResponse;
import ru.alishev.springcourse.WeatherSensorRestAPI.util.SensorNotCreatedException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Neil Alishev
 */
@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping(path = "/measurements")

public class MeasurementController {

    private final MeasurementService measurementService;
    private final SensorService sensorService;

    private final MeasurementValidator measurementValidator;

    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SensorService sensorService, MeasurementValidator measurementValidator, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public MeasurementsResponse getMeasurement() {
        MeasurementsResponse measurementsResponse =new MeasurementsResponse();
        List<MeasurementDTO> list = new ArrayList<>();
        for (Measurement measurement : measurementService.findAll()) {
            MeasurementDTO measurementDTO = convertToSensorTO(measurement);
            list.add(measurementDTO);
        }

        measurementsResponse.setMeasurementDTOList(list);
        return measurementsResponse; // Jackson конвертирует эти объекты в JSON
    }


    @GetMapping("/rainyDaysCount")
    public Integer getRainyDaysCount() {
        MeasurementsResponse list = getMeasurement();

        return (Integer) (int) list.getMeasurementDTOList().stream().filter(MeasurementDTO::isRaining).count();
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        measurementValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            String errorMsg;
            List<FieldError> errors = bindingResult.getFieldErrors();
            errorMsg = errors.stream().map(error -> error.getField() + " - " + error.getDefaultMessage() + ";").collect(Collectors.joining());
            throw new SensorNotCreatedException(errorMsg);

        }
        measurementService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);

        measurement.setSensor(sensorService.getSensorByName(measurement.getSensor().getName()).get());

        return measurement;
    }


    private MeasurementDTO convertToSensorTO(Measurement measurement) {
        MeasurementDTO measurementDTO = modelMapper.map(measurement, MeasurementDTO.class);
        measurementDTO.setSensor(modelMapper.map(measurement.getSensor(), SensorDTO.class));
        return measurementDTO;
    }


    @ExceptionHandler()
    private ResponseEntity<PersonErrorResponse> handleException(SensorNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
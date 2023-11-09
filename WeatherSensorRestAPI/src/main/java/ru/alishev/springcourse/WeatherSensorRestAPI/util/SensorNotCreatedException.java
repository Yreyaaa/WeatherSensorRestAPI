package ru.alishev.springcourse.WeatherSensorRestAPI.util;

public class SensorNotCreatedException extends RuntimeException {
    public SensorNotCreatedException(String msg) {
        super(msg);

    }
}

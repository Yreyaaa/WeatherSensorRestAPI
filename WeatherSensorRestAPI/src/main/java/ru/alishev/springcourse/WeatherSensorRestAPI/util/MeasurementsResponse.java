package ru.alishev.springcourse.WeatherSensorRestAPI.util;

import dto.MeasurementDTO;

import java.util.List;

public class MeasurementsResponse {
    public List<MeasurementDTO> getMeasurementDTOList() {
        return measurementDTOList;
    }

    public void setMeasurementDTOList(List<MeasurementDTO> measurementDTOList) {
        this.measurementDTOList = measurementDTOList;
    }

    private List<MeasurementDTO> measurementDTOList;
}

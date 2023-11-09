package dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Neil Alishev
 */

public class MeasurementDTO {

    @NotNull(message = "Значение температуры не может быть пустым")
    @Range(min = -100, max = 100, message = "Значение температуры должно быть в диапозоне от -100 до 100")
    private Double value;


    @NotNull(message = "Поле \"дождь\" не может быть пустым")
    private Boolean raining;


    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor.getName() +
                '}';
    }
}
package dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Neil Alishev
 */

public class SensorDTO {


    @NotEmpty(message = "Имя сенсора не может быть пустым")
    @Size(min = 2, max = 30, message = "Имя сенсора должно иметь длинну от 2 до 30 символов")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}





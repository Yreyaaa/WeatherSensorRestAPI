package ru.alishev.springcourse.WeatherSensorRestAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.MeasurementDTO;
import dto.SensorDTO;
import org.knowm.xchart.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import ru.alishev.springcourse.WeatherSensorRestAPI.util.MeasurementsResponse;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication

public class WeatherSensorRestAPI {

    public static void main(String[] args) {
        SpringApplication.run(WeatherSensorRestAPI.class, args);
        ObjectMapper objectMapper = new ObjectMapper();

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements/add";

        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name", "sensor005");

        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("sensor005");


        HttpEntity requestSensor = null;


//        for (int i = 0; i < 2000; i++) {
//            requestSensor = new HttpEntity<>(getRandommeasurementDTO(sensorDTO));
//            String response = restTemplate.postForObject(url, requestSensor, String.class);
//            System.out.println(response);
//
//        }


        MeasurementsResponse measurementsResponse = restTemplate.getForObject("http://localhost:8080/measurements", MeasurementsResponse.class);


        List<Double> ArrayValue = measurementsResponse.getMeasurementDTOList().stream().map(MeasurementDTO::getValue).collect(Collectors.toList());

        ArrayValue.forEach(System.out::println);



        drawChart(ArrayValue);

    }

    private static void drawChart(List<Double> temperatures) {
        double[] xData = IntStream.range(0, temperatures.size()).asDoubleStream().toArray();
        double[] yData = temperatures.stream().mapToDouble(x -> x).toArray();

        XYChart chart = QuickChart.getChart("Temperatures", "X", "Y", "temperature",
                xData, yData);

        new SwingWrapper(chart).displayChart();
    }

        private static MeasurementDTO getRandommeasurementDTO (SensorDTO sensorDTO){
            MeasurementDTO measurementDTO = new MeasurementDTO();

            measurementDTO.setValue((Math.random() - Math.random() * 95));
            measurementDTO.setRaining(new Random().nextBoolean());
            measurementDTO.setSensor(sensorDTO);
            return measurementDTO;
        }


        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }

}

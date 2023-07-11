package ru.azat;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class App {
    public static void main( String[] args ) {
        final String sensorName = "First sensor";

        registrationSensor(sensorName);

        Random random = new Random(47);

        float minTemperature = -30;
        float maxTemperature = 50;
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            sendMeasurements(random.nextFloat() * maxTemperature,
                    random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), sensorName);
        }
    }

    private static void registrationSensor(String sensorName) {
        final String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);

        makePostRequest(url, jsonData);
    }

    private static void sendMeasurements(double value, boolean raining, boolean sunny, boolean cloudy, String sensorName) {
        final String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sunny", sunny);
        jsonData.put("cloudy", cloudy);
        jsonData.put("sensorOwner", Map.of("name", sensorName));

        makePostRequest(url, jsonData);
    }

    private static void makePostRequest(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(url, request, String.class);
            System.out.println("Measurement sending successful");
        } catch (HttpClientErrorException e) {
            System.out.println("Error!!!");
            System.out.println(e.getMessage());
        }
    }
}

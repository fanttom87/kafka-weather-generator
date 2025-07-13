package org.example;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherProducer {
    static String[] cityList = {"Москва", "Санкт-Петербург", "Ярославль", "Волгоград", "Екатеринбург", "Сочи"};
    static String[] weatherList = {"Солнечно", "Облачно", "Дождь", "Гроза"};

    static long currentDay = 0;

    public static void main(String[] args) {
        Map<String, Object> config = new HashMap<>();
        config.put("bootstrap.servers", "localhost:9092");
        config.put("key.serializer", StringSerializer.class.getName());
        config.put("value.serializer", StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<>(config);

        Random random = new Random();
        ObjectMapper mapper = new ObjectMapper();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                String city = cityList[random.nextInt(cityList.length)];
                int temperature = random.nextInt(36);
                String weather = weatherList[random.nextInt(weatherList.length)];

                String date = "2025-11-" + String.format("%02d", (currentDay % 30) + 1);

                Map<String, Object> data = new HashMap<>();
                data.put("city", city);
                data.put("temperature", temperature);
                data.put("condition", weather);
                data.put("date", date);

                String json = mapper.writeValueAsString(data);

                ProducerRecord<String, String> record = new ProducerRecord<>("weather-topic", city, json);
                producer.send(record);
                System.out.println("Отправлено: " + json);

                currentDay++;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}
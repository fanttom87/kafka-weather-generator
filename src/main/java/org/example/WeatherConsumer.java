package org.example;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.time.Duration;
import java.util.*;

public class WeatherConsumer {
    public static void main(String[] args) {
        Map<String, Object> config = new HashMap<>();
        config.put("bootstrap.servers", "localhost:9092");
        config.put("group.id", "weather-group-" + System.currentTimeMillis());
        config.put("key.deserializer", StringDeserializer.class.getName());
        config.put("value.deserializer", StringDeserializer.class.getName());
        config.put("auto.offset.reset", "earliest");

        Consumer<String, String> consumer = new KafkaConsumer<>(config);
        ObjectMapper mapper = new ObjectMapper();

        consumer.subscribe(Collections.singletonList("weather-topic"));

        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            for (var record : records) {
                try {
                    Map<String, Object> data = mapper.readValue(record.value(), new TypeReference<>() {
                    });

                    String city = (String) data.get("city");
                    int temperature = (Integer) data.get("temperature");
                    String condition = (String) data.get("condition");
                    String date = (String) data.get("date");

                    System.out.printf("Получено: [%s] %s | Температура: %d°C | Погода: %s%n",
                            date, city, temperature, condition);

                } catch (Exception e) {
                    System.err.println("Ошибка при парсинге JSON: " + record.value());
                }
            }
        }
    }
}
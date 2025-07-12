package org.example;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WeatherConsumer {
    public static void main(String[] args) {
        Map<String, Object> config = new HashMap<>();
        config.put("bootstrap.servers", "localhost:9092");
        config.put("group.id", "weather-group");
        config.put("key.deserializer", StringDeserializer.class.getName());
        config.put("value.deserializer", StringDeserializer.class.getName());
        config.put("auto.offset.reset", "earliest");
        config.put("enable.auto.commit", "true");
        config.put("auto.commit.interval.ms", "1000");
        config.put("session.timeout.ms", "10000");

        Consumer<String, String> consumer = new KafkaConsumer<>(config);

        consumer.subscribe(Collections.singletonList("weather-topic"));

        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            for (var record : records) {
                System.out.printf("Получено сообщение: %s%n", record.value());
            }
        }
    }
}

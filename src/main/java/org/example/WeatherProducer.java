package org.example;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherProducer {
    static String[] cityList = {"Москва", "Санкт-Петербург", "Ярославль", "Волгоград", "Екатеринбург", "Сочи"};
    static String[] weatherList = {"Солнечно", "Облачно", "Дождь", "Гроза"};

    public static void main(String[] args) {
        Map<String, Object> config = new HashMap<>();
        config.put("bootstrap.servers", "localhost:9092");
        config.put("key.serializer", StringSerializer.class.getName());
        config.put("value.serializer", StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<>(config);

        Random random = new Random();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            String city = cityList[random.nextInt(cityList.length)];
            int temperature = random.nextInt(36);
            String weather = weatherList[random.nextInt(weatherList.length)];

            String message = "Город: " + city + ", Температура: " + temperature + ", Состояние: " + weather;
            ProducerRecord<String, String> record = new ProducerRecord<>("weather-topic", city, message);

            producer.send(record);
            System.out.println("Отправлено: " + message);
        }, 0, 2, TimeUnit.SECONDS);
    }
}
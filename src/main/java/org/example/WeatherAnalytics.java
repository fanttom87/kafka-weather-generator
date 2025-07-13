package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WeatherAnalytics {

    private Map<String, Integer> rainyDays = new HashMap<>();
    private Map<String, Integer> sunnyDays = new HashMap<>();
    private Map<String, Integer> totalTemp = new HashMap<>();
    private Map<String, Integer> countTemp = new HashMap<>();

    private int maxTemp = -1;
    private String hottestCity = "";
    private String hottestDate = "";

    private static final File STORAGE_FILE = new File("weather-analytics.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public WeatherAnalytics() {
        loadFromFile(); // загружаем предыдущую статистику из файла
    }

    public void addData(WeatherData data) {
        if ("Дождь".equals(data.condition)) {
            rainyDays.put(data.city, rainyDays.getOrDefault(data.city, 0) + 1);
        }
        if ("Солнечно".equals(data.condition)) {
            sunnyDays.put(data.city, sunnyDays.getOrDefault(data.city, 0) + 1);
        }

        totalTemp.put(data.city, totalTemp.getOrDefault(data.city, 0) + data.temperature);
        countTemp.put(data.city, countTemp.getOrDefault(data.city, 0) + 1);

        if (data.temperature > maxTemp) {
            maxTemp = data.temperature;
            hottestCity = data.city;
            hottestDate = data.date;
        }

        saveToFile();
    }

    public void printAnalytics() {
        System.out.println("\n--- Аналитика ---");

        if (!rainyDays.isEmpty()) {
            String rainiest = Collections.max(rainyDays.entrySet(), Map.Entry.comparingByValue()).getKey();
            System.out.println("Самый дождливый город: " + rainiest + " (" + rainyDays.get(rainiest) + " дней)");
        }

        if (!sunnyDays.isEmpty()) {
            String sunniest = Collections.max(sunnyDays.entrySet(), Map.Entry.comparingByValue()).getKey();
            System.out.println("Самый солнечный город: " + sunniest + " (" + sunnyDays.get(sunniest) + " дней)");
        }

        if (!totalTemp.isEmpty()) {
            String coldest = null;
            double minAvg = Double.MAX_VALUE;
            for (String city : totalTemp.keySet()) {
                double avg = (double) totalTemp.get(city) / countTemp.get(city);
                if (avg < minAvg) {
                    minAvg = avg;
                    coldest = city;
                }
            }
            System.out.printf("Самая низкая средняя температура: %s (%.1f°C)%n", coldest, minAvg);
        }

        if (maxTemp != -1) {
            System.out.printf("Самая жаркая погода: %d°C в городе %s (%s)%n", maxTemp, hottestCity, hottestDate);
        }

        System.out.println("------------------\n");
    }

    private void saveToFile() {
        try {
            Map<String, Object> state = new HashMap<>();
            state.put("rainyDays", rainyDays);
            state.put("sunnyDays", sunnyDays);
            state.put("totalTemp", totalTemp);
            state.put("countTemp", countTemp);
            state.put("maxTemp", maxTemp);
            state.put("hottestCity", hottestCity);
            state.put("hottestDate", hottestDate);

            mapper.writeValue(STORAGE_FILE, state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        if (!STORAGE_FILE.exists()) return;

        try {
            Map<String, Object> state = mapper.readValue(STORAGE_FILE, Map.class);

            rainyDays = (Map<String, Integer>) state.getOrDefault("rainyDays", new HashMap<>());
            sunnyDays = (Map<String, Integer>) state.getOrDefault("sunnyDays", new HashMap<>());
            totalTemp = (Map<String, Integer>) state.getOrDefault("totalTemp", new HashMap<>());
            countTemp = (Map<String, Integer>) state.getOrDefault("countTemp", new HashMap<>());

            maxTemp = (Integer) state.getOrDefault("maxTemp", -1);
            hottestCity = (String) state.getOrDefault("hottestCity", "");
            hottestDate = (String) state.getOrDefault("hottestDate", "");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

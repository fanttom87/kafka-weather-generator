package org.example;

import java.util.*;

public class WeatherAnalytics {

    private final Map<String, Integer> rainyDays = new HashMap<>();
    private final Map<String, Integer> sunnyDays = new HashMap<>();
    private final Map<String, Integer> totalTemp = new HashMap<>();
    private final Map<String, Integer> countTemp = new HashMap<>();

    private int maxTemp = -1;
    private String hottestCity = "";
    private String hottestDate = "";

    public WeatherAnalytics() {

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
}

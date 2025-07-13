package org.example;

public class WeatherData {
    public String city;
    public int temperature;
    public String condition;
    public String date;

    @Override
    public String toString() {
        return String.format("Город: %s | Температура: %d°C | Погода: %s | Дата: %s",
                city, temperature, condition, date);
    }
}

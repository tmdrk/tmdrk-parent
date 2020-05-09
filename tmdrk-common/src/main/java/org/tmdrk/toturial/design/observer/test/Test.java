package org.tmdrk.toturial.design.observer.test;

/**
 * @ClassName Test
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/13 15:30
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherData);
        weatherData.setMeasurement(20,68,565);
        weatherData.setMeasurement(21,67,566);
        weatherData.setMeasurement(22,86,767);
    }
}

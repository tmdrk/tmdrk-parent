package org.tmdrk.toturial.design.observer.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName WeatherData
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/13 15:14
 * @Version 1.0
 **/
public class WeatherData implements Subject{
    private boolean changed = false;
    List<Observer> list;
    private float temperature; //温度
    private float humidity; //湿度
    private float pressure; //压强 压力
    public WeatherData(){
        list = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        list.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int index = list.indexOf(o);
        if(index>=0){
            list.remove(index);
        }

    }

    @Override
    public void notifyObservers() {
        if(changed){
            for (Observer o : list) {
                o.update(temperature,humidity,pressure);
            }
            clearChanged();
        }
    }
    public void setMeasurement(float temp,float humidity,float pressure){
        this.temperature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementChanged();

    }
    public void measurementChanged(){
        setChanged();
        notifyObservers();
    }

    private void setChanged(){
        changed = true;
    }
    private void clearChanged(){
        changed = false;
    }
}

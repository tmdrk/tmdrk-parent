package org.tmdrk.toturial.design.observer.test;

/**
 * @ClassName CurrentConditionsDisplay
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/13 15:21
 * @Version 1.0
 **/
public class CurrentConditionsDisplay implements Observer,DisplayElement{
    private Subject subject;
    private float temperature; //温度
    private float humidity; //湿度
    private float pressure; //压强 压力

    public CurrentConditionsDisplay(Subject weatherData){
        this.subject = weatherData;
        weatherData.registerObserver(this);
    }

    /**
     * @Author zhoujie
     * @Description 显示温度和湿度
     * @Date 15:30 2020/2/13
     * @Param []
     * @return void
     **/
    @Override
    public void display() {
        System.out.println("Current conditions:"+temperature+"F degrees and "+humidity+"% humidity");
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }
}

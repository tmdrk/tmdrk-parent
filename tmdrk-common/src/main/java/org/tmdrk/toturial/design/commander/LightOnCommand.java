package org.tmdrk.toturial.design.commander;

/**
 * @ClassName LightOnCommand
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/17 17:00
 * @Version 1.0
 **/
public class LightOnCommand implements Command{
    Light light;
    public LightOnCommand(Light light){
        this.light = light;
    }
    @Override
    public void execute() {
        light.on();
    }
}

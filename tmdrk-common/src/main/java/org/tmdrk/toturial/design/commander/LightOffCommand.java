package org.tmdrk.toturial.design.commander;

/**
 * @ClassName LightOffCommand
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/17 17:00
 * @Version 1.0
 **/
public class LightOffCommand implements Command{
    Light light;
    public LightOffCommand(Light light){
        this.light = light;
    }
    @Override
    public void execute() {
        light.off();
    }
}

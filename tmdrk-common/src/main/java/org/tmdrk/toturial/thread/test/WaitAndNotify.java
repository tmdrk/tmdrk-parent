package org.tmdrk.toturial.thread.test;

/**
 * @ClassName WaitAndNotify
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/27 14:51
 * @Version 1.0
 **/
public class WaitAndNotify {
    public void await() throws InterruptedException {
        synchronized (this) {
            wait();
        }
    }
}

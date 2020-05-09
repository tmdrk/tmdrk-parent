package org.tmdrk.toturial.design.observer.test;

/**
 * @ClassName Subject
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/13 14:51
 * @Version 1.0
 **/
public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}

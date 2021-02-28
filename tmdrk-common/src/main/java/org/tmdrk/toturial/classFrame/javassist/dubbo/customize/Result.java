package org.tmdrk.toturial.classFrame.javassist.dubbo.customize;

public interface Result {
    Object getValue();
    void setValue(Object value);
    Throwable getException();
    void setException(Throwable t);
    boolean hasException();
    Object recreate() throws Throwable;
    Result get();
}

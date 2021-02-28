package org.tmdrk.toturial.classFrame.javassist.dubbo.customize;

/**
 * GenericResult
 *
 * @author Jie.Zhou
 * @date 2021/2/26 10:58
 */
public class GenericResult implements Result{
    private Object object;

    private Throwable throwable;

    @Override
    public Object getValue() {
        return object;
    }

    @Override
    public void setValue(Object value) {
        this.object = value;
    }

    @Override
    public Throwable getException() {
        return throwable;
    }

    @Override
    public void setException(Throwable t) {
        this.throwable = t;
    }

    @Override
    public boolean hasException() {
        if(getException() != null){
            return true;
        }
        return false;
    }

    @Override
    public Object recreate() throws Throwable {
        if(hasException()){
            throw throwable;
        }
        return getValue();
    }

    @Override
    public Result get() {
        return null;
    }
}

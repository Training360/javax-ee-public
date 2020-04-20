package empapp;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
public class CounterBean {

    private int counter;

    @Lock(LockType.WRITE)
    public void inc() {
        counter = counter + 1;
    }

    @Lock(LockType.READ)
    public int get() {
        return counter;
    }
}

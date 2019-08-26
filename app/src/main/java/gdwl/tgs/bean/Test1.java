package gdwl.tgs.bean;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 田桂森 2019/8/19
 */
public  class Test1 {
    int a=1;

    public int getA() {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3,5,1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100));
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
 
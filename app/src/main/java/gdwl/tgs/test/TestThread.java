package gdwl.tgs.test;

import com.orhanobut.logger.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 田桂森 2019/8/19
 */
public class TestThread {

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(25));

    public void run() {
        for (int i = 0; i < 30; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        Logger.d("当前线程：" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            threadPoolExecutor.execute(runnable);
        }
    }
    
    
    public void b(){
        Executors.newFixedThreadPool(5);
        Executors.newCachedThreadPool();
        Executors.newScheduledThreadPool(5);
        Executors.newSingleThreadExecutor();
    }
}
 
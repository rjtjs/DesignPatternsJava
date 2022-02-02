package designpatterns.concurrency;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActiveObjectTest {
    private int threadCount = 100;
    private int threadPoolSize = 10;

    @Test
    public void counterShouldCountNumberOfThreads() throws InterruptedException {
        final ExecutorService service = Executors.newFixedThreadPool(threadPoolSize);
        final CountDownLatch latch = new CountDownLatch(threadCount);
        final Counter counter = new Counter();

        for (int i = 0; i < threadCount; i++) {
            service.execute(() -> {
                counter.increment();
                latch.countDown();
            });
        }
        latch.await();
    }
}

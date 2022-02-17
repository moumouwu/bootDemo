package com.boot.juc.pool;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author binSin
 * @date 2022/2/15
 */
public class ThreadScheduled {
    static LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(10000);

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
//                System.out.println(Thread.currentThread().getName() + "出去啦");
                // 出去一个人计数器就减1
                linkedBlockingQueue.add("15");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println("***"+linkedBlockingQueue.size());
        test();
    }

    public static List<Map<String, Object>> test() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (linkedBlockingQueue.size() == 0) {
                    return;
                }
                for (int i = 0; i < linkedBlockingQueue.size(); i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String poll = linkedBlockingQueue.poll();
                        }
                    }).start();
                }
                System.out.println("**********************" + linkedBlockingQueue.size());
            }
        }, 2000, 1000, TimeUnit.MILLISECONDS);
        System.out.println(linkedBlockingQueue.size());
        return null;
    }
}

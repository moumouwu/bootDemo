package com.boot.juc.pool;

import java.util.concurrent.*;


/**
 * @author binSin
 * @date 2021/11/17
 */
public class ThreadPoolDemo1 {


    /**
     * 火车站 3 个售票口, 10 个用户买票
     *
     * @param args
     */
    public static void main(String[] args) {
        /**
         * corePoolSize 线程池的核心线程数
         * maximumPoolSize 能容纳的最大线程数
         * keepAliveTime 空闲线程存活时间
         * unit 存活的时间单位
         * workQueue 存放提交但未执行任务的队列
         * threadFactory 创建线程的工厂类:可以省略
         * handler 等待队列满后的拒绝策略:可以省略
         */
        // 定时线程次:线程数量为 3---窗口数为 3
        ExecutorService threadService = new ThreadPoolExecutor(3,
                3,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        try {
            // 10 个人买票
            for (int i = 1; i <= 10; i++) {
                threadService.execute(() -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " 窗口,开始卖票");
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName() + " 窗口买票结束");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //完成后结束
            threadService.shutdown();
        }
    }
}
/**
 pool-1-thread-3 窗口,开始卖票
 pool-1-thread-2 窗口,开始卖票
 pool-1-thread-1 窗口,开始卖票
 pool-1-thread-3 窗口买票结束
 pool-1-thread-2 窗口买票结束
 pool-1-thread-1 窗口买票结束
 ....
 */

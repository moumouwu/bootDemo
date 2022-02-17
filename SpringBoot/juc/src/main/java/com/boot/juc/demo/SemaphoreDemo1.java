package com.boot.juc.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author binSin
 * @date 2021/11/16
 */
public class SemaphoreDemo1 {
    public static void main(String[] args) {
        // 10台电脑
        Semaphore semaphore = new Semaphore(5);

        CountDownLatch countDownLatch = new CountDownLatch(20);

        // 20 个小伙伴想要上网
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+"进来了网吧");
                    countDownLatch.countDown();
                    countDownLatch.await();
                    Thread.sleep(1000);
                    //等待获取许可证
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到了电脑");
                    //抢到的小伙伴，迅速就开打啦 这里就模拟个时间哈，
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //打完几把游戏的小伙伴 女朋友来找了 溜啦溜啦 希望大家都有人陪伴
                    System.out.println("女朋友来找，"+Thread.currentThread().getName() + "离开了");
                    semaphore.release();//释放资源,离开了就要把电脑让给别人啦。
                }
            }, String.valueOf(i)).start();
        }
    }
}


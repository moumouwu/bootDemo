package com.boot.juc.demo;

/**
 * @author binSin
 * @date 2021/11/16
 */

import java.util.concurrent.CountDownLatch;

/**
 * @Author: crush
 * @Date: 2021-08-19 23:21
 * version 1.0
 */
public class CountDownLatchDemo1 {

    public static void main(String[] args) {
        // 初始值8 有八个人需要出寝室门
        CountDownLatch countDownLatch = new CountDownLatch(8);
        for (int i = 1; i <= 8; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "出去啦");
                // 出去一个人计数器就减1
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        try {
            System.out.println("等待所有人出去");
            countDownLatch.await(); // 阻塞等待计数器归零
            System.out.println("都出去了 可以关门了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 阻塞的操作 ： 计数器  num++
        System.out.println(Thread.currentThread().getName() + "====寝室人都已经出来了，关门向教室冲！！！====");
    }
}

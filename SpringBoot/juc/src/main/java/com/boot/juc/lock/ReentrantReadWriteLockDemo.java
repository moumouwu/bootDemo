package com.boot.juc.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author binSin
 * @date 2021/11/10
 */
public class ReentrantReadWriteLockDemo {

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();


    public static void main(String[] args) {
        final ReentrantReadWriteLockDemo test = new ReentrantReadWriteLockDemo();
        new Thread(() -> {
            System.out.println();
//            test.get(Thread.currentThread());
             test.get2(Thread.currentThread());
        }).start();

        new Thread(() -> {
//            test.get(Thread.currentThread());
             test.get2(Thread.currentThread());
        }).start();
    }

    /**
     * 输出
     * Thread-0正在进行读操作
     * Thread-0读操作完毕
     * Thread-1正在进行读操作
     * Thread-1正在进行读操作
     * Thread-1正在进行读操作
     * ....
     * Thread-1读操作完毕
     */
    public synchronized void get(Thread thread) {
//        long start = System.currentTimeMillis();
//        while (System.currentTimeMillis() - start <= 1) {
        int i = 0;
        while (i < 5) {
            System.out.println(thread.getName() + "正在进行读操作" + i++);
        }
        System.out.println(thread.getName() + "读操作完毕");

    }


    /**
     * 两个线程同时读
     * 输出
     * Thread-0正在进行读操作
     * Thread-0读操作完毕
     * Thread-1正在进行读操作
     * Thread-1读操作完毕
     */
    public void get2(Thread thread) {
        rwl.readLock().lock();
        try {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start <= 1) {
                System.out.println(thread.getName() + "正在进行读操作");
            }
            System.out.println(thread.getName() + "读操作完毕");
        } finally {
            rwl.readLock().unlock();
        }
    }


}

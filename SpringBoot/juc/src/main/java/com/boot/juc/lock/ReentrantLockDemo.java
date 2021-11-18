package com.boot.juc.lock;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author binSin
 * @date 2021/11/10
 */
public class ReentrantLockDemo {

    // ReentrantLock 可重入锁 ReentrantLock 是唯一实现了 Lock 接口的类，并且 ReentrantLock 提供了更多的方法
    // 可重入锁：什么是 “可重入”，可重入就是说某个线程已经获得某个锁，可以再次获取锁而不会出现死锁
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("第1次获取锁，这个锁是：" + lock);
                    for (int i = 2; i <= 11; i++) {
                        try {
                            lock.lock();
                            System.out.println("第" + i + "次获取锁，这个锁是：" + lock);
                            try {
                                Thread.sleep(new Random().nextInt(200));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } finally {
                            // 如果把这里注释掉的话，那么程序就会陷入死锁当中。
                            lock.unlock();
                        }
                    }

                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("这里是为了测试死锁而多写一个的线程");
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }
}
/**
 * 第1次获取锁，这个锁是：java.util.concurrent.locks.ReentrantLock@6b5fde1f[Locked by thread Thread-0]
 * 第2次获取锁，这个锁是：java.util.concurrent.locks.ReentrantLock@6b5fde1f[Locked by thread Thread-0]
 * 第3次获取锁，这个锁是：java.util.concurrent.locks.ReentrantLock@6b5fde1f[Locked by thread Thread-0]
 * ...
 */


package com.boot.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author binSin
 * @date 2021/11/10
 */
public class LookDemo1 {

    public static void main(String[] args) {
        Share share = new Share();

        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                share.incr();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                share.decr();
            }
        }, "BB").start();
    }
}

class Share {

    private Integer number = 0;
    // 可重入锁
    private ReentrantLock lock = new ReentrantLock();
    private Condition newCondition = lock.newCondition();

    // 加一操作
    public void incr() {
        try {
            lock.lock();
            while (number != 0) {
                // 沉睡
                newCondition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + " :: " + number);
            // 唤醒另一个线程
            newCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // 减一操作
    public void decr() {
        try {
            lock.lock();
            while (number != 1) {
                // 沉睡
                newCondition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + " :: " + number);
            // 唤醒另一个线程
            newCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

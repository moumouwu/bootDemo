package com.boot.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author binSin
 * @date 2021/11/10
 */
public class LockDemo {

    public static void main(String[] args) {
        TicketLock ticket = new TicketLock();
        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();
    }
}

class TicketLock implements Runnable {

    private int tick = 10;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            // 上锁
            lock.lock();
            try {
                if (tick > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "完成售票,余票为：" + --tick);
                }
            } finally {
                // 手动释放锁
                lock.unlock();
            }
        }
    }

    public void sale(int tick) {
        if (tick > 0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "完成售票,余票为：" + --tick);
        }
    }
}

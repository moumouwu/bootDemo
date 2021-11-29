package com.boot.juc.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binSin
 * @date 2021/11/22
 */
public class VolatileTest {

    //    static volatile int i = 0;
    static AtomicInteger i = new AtomicInteger(0);

    static class AddThread extends Thread {

        @Override
        public void run() {
            synchronized (i) {
                for (int j = 0; j < 100000; j++) {
                    i.addAndGet(1);
                    //                    i++;
                }
            }
        }

//        public void run() {
//                for (int j = 0; j < 100000; j++) {
//                   i++;
//                }
//            }
//        }
    }

    public static void main(String[] args) {
        AddThread add1 = new AddThread();
        AddThread add2 = new AddThread();

        add1.start();
        add2.start();

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println("i 最终为 " + i);
    }
}


package com.boot.juc.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author binSin
 * @date 2021/12/23
 */
public class test_ABC {

    public static void main(String[] args) {
        char[] a = "123456".toCharArray();
        char[] b = "ABCDEF".toCharArray();
        char[] c = "abcdef".toCharArray();
        ReentrantLock lock = new ReentrantLock();
        Condition newCondition = lock.newCondition();
        Condition newCondition1 = lock.newCondition();
        Condition newCondition2 = lock.newCondition();
//        MyTask myTask = new MyTask();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    for (int i = 0; i < a.length; i++) {
                        newCondition1.signal();
                        System.out.print(a[i]);
                        newCondition.await();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    for (int i = 0; i < b.length; i++) {
                        newCondition1.await();
                        System.out.print(b[i]);
                        newCondition2.signal();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        },"t1");
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    for (int i = 0; i < c.length; i++) {
                        newCondition2.await();
                        System.out.print(c[i]);
                        newCondition.signal();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        thread.start();
        thread1.start();
        thread3.start();
    }

    public class MyTask extends Thread {


//        @Override
//        public void run() {
//            try {
//                reentrantLock
//                System.out.println("try lock:" + Thread.currentThread().getName());
//                boolean tryLock = reentrantLock.tryLock(3, TimeUnit.SECONDS);
//                if (tryLock) {
//                    System.out.println("try lock success :" + Thread.currentThread().getName());
//                    System.out.println("睡眠一下：" + Thread.currentThread().getName());
//                    Thread.sleep(5000);
//                    System.out.println("醒了：" + Thread.currentThread().getName());
//                } else {
//                    System.out.println("try lock 超时 :" + Thread.currentThread().getName());
//                }
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                System.out.println("unlock:" + Thread.currentThread().getName());
//                reentrantLock.unlock();
//            }
//        }
    }
}

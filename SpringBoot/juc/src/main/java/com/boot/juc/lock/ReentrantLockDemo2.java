package com.boot.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author binSin
 * @date 2021/11/10
 */
public class ReentrantLockDemo2 {

    /**
     * 实现可定时的锁请求：tryLock
     * <p>
     * 上面演示了trtLock的使用，trtLock设置获取锁的等待时间，
     * 超过3秒直接返回失败，可以从日志中看到结果。
     * 有异常是因为thread1获取锁失败，不应该调用unlock。
     *
     * @param args
     */
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Thread thread1 = new Thread_tryLock(reentrantLock);
        thread1.setName("thread1");
        thread1.start();
        Thread thread2 = new Thread_tryLock(reentrantLock);
        thread2.setName("thread2");
        thread2.start();
    }


    /**
     * try lock:thread2
     * try lock:thread1
     * try lock success :thread2
     * 睡眠一下：thread2
     * try lock 超时 :thread1
     * unlock:thread1
     * Exception in thread "thread1" java.lang.IllegalMonitorStateException
     * at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     * at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     * at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     * at com.boot.juc.thread.ReentrantLockDemo2$Thread_tryLock.run(ReentrantLockDemo2.java:49)
     * 醒了：thread2
     * unlock:thread2
     */
    static class Thread_tryLock extends Thread {
        ReentrantLock reentrantLock;

        public Thread_tryLock(ReentrantLock reentrantLock) {
            this.reentrantLock = reentrantLock;
        }

        @Override
        public void run() {
            try {
                System.out.println("try lock:" + Thread.currentThread().getName());
                boolean tryLock = reentrantLock.tryLock(3, TimeUnit.SECONDS);
                if (tryLock) {
                    System.out.println("try lock success :" + Thread.currentThread().getName());
                    System.out.println("睡眠一下：" + Thread.currentThread().getName());
                    Thread.sleep(5000);
                    System.out.println("醒了：" + Thread.currentThread().getName());
                } else {
                    System.out.println("try lock 超时 :" + Thread.currentThread().getName());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("unlock:" + Thread.currentThread().getName());
                reentrantLock.unlock();
            }
        }
    }

}


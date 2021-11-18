package com.boot.juc.readwriteLock;


import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author binSin
 * @date 2021/11/17
 */

public class ReadWriteLockDemo2 {

    /**
     * 降级锁
     * 写锁-》读锁-》释放写锁-》释放读锁
     * @param args
     */
    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        // 获取读锁
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        // 获取写锁
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        //1、 获取到写锁
        writeLock.lock();
        System.out.println("获取到了写锁");
        //2、 继续获取到读锁
        readLock.lock();
        System.out.println("继续获取到读锁");
        //3、释放写锁
        writeLock.unlock();
        //4、 释放读锁
        readLock.unlock();
    }
}
/**
 * 获取到了写锁
 * 继续获取到读锁
 */

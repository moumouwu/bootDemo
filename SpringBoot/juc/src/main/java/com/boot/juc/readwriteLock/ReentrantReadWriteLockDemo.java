package com.boot.juc.readwriteLock;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author binSin
 * @date 2021/11/17
 */
public class ReentrantReadWriteLockDemo {


    /**
     * 写锁： 排他锁，独占锁
     *      写锁加锁时，其他试图对这个加锁的线程都会被阻塞
     * 加锁前提条件
     *      没有其他读线程在访问
     *      没有其他写线程在访问
     * 读锁： 共享锁
     *      读锁加锁时，允许其他线程加读锁，不能加写锁
     *加锁前提条件：
     *      不存在其他线程的写锁，
     *      没有写请求，或者有写请求，当调用线程和持有锁的是同一线程
     *
     * @param args
     */
    //创建 map 集合
    private volatile Map<String, Object> map = new HashMap<>();

    //创建读写锁对象
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    //放数据
    public void put(String key, Object value) {
        //添加写锁
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在写数据" + key);
            //暂停一会
            TimeUnit.MICROSECONDS.sleep(300);
            //放数据
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写完了" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放写锁
            rwLock.writeLock().unlock();
        }
    }

    //取数据
    public Object get(String key) {
        //添加读锁
        rwLock.readLock().lock();
        Object result = null;
        try {
            System.out.println(Thread.currentThread().getName() + "正在取数据" + key);
            //暂停一会
            TimeUnit.MICROSECONDS.sleep(300);
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "取完数据了" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放读锁
            rwLock.readLock().unlock();
        }
        return result;
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();

        for (int i = 1; i <= 5; i++) {
            final int number = i;
            new Thread(() -> {
                demo.put(String.valueOf(number), number);
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5; i++) {
            final int number = i;
            new Thread(() -> {
                demo.get(String.valueOf(number));
            }, String.valueOf(i)).start();
        }
    }
}

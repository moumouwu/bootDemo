package com.boot.juc.readwriteLock;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author binSin
 * @date 2021/11/17
 */
public class ReadWriteLockDemo3 {

    /**
     * 缓存器,这里假设需要存储1000左右个缓存对象，按照默认的负载因子0.75，则容量=750，大概估计每一个节点链表长度为5个
     * 那么数组长度大概为：150,又有雨设置map大小一般为2的指数，则最近的数字为：128
     */
    private Map<String, Object> map = new ConcurrentHashMap<>(128);
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock writeLock = rwl.writeLock();
    private Lock readLock = rwl.readLock();

    public static void main(String[] args) throws InterruptedException {
        ReadWriteLockDemo3 readWriteLockDemo3 = new ReadWriteLockDemo3();
        for (int i = 0; i < 10; i++) {
            final int num = i;
            new Thread(() -> {
                readWriteLockDemo3.get(String.valueOf(num));
            }, String.valueOf(i)).start();
        }

        Thread.sleep(2000);
        for (int i = 0; i < 10; i++) {
            final int num = i;
            new Thread(() -> {
                readWriteLockDemo3.get(String.valueOf(num));
            }, String.valueOf(i)).start();
        }
    }

    public Object get(String id) {

        Object value = null;
        readLock.lock();//首先开启读锁，从缓存中去取
        try {
            //如果缓存中没有 释放读锁，上写锁
            if (map.get(id) == null) {
                readLock.unlock();
                writeLock.lock();
                try {
                    // 防止多写线程重复查询赋值
                    System.out.println(Thread.currentThread().getName() + "map插入数据");
                    map.put(id, id);
                    if (value == null) {
                        //此时可以去数据库中查找，这里简单的模拟一下
                        value = "redis-value";
                    }
                    //加读锁降级写锁,不明白的可以查看上面锁降级的原理与保持读取数据原子性的讲解
                    readLock.lock();
                } finally {
                    //释放写锁
                    writeLock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + "::" + map.get(id));
            }
        } finally {
            //最后释放读锁
            readLock.unlock();
        }
        return value;
    }

}

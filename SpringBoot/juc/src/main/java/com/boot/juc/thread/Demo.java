package com.boot.juc.thread;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author binSin
 * @date 2021/11/11
 */
public class Demo {

    // 集合线程问题
    public static void main(String[] args) {
        // 使用ArrayList会出现 ConcurrentModificationException 并发修改异常
        // List list = new ArrayList();

        //  Vector 在add方法上加上 Synchronized 关键字

        // Collections 提供了方法 synchronizedList 保证 list 是同步线程安全的。
        // List list = Collections.synchronizedList(new ArrayList<>());

        // 更新操作开销大（add()、set()、remove()等等），因为要复制整个数组
        // 是线程安全的
        // 它最适合于具有以下特征的应用程序：List 大小通常保持很小，只读操作远多 于可变操作，需要在遍历期间防止线程间的冲突。
        // 独占锁效率低：采用读写分离思想
        // 写线程获取到锁，其他写线程阻塞
        // 复制思想
        List list = new CopyOnWriteArrayList();

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString());
                System.out.println(list);
            }, "线程" + i).start();

        }
    }
}

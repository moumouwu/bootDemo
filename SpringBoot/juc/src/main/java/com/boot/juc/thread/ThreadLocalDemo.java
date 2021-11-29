package com.boot.juc.thread;

/**
 * @author binSin
 * @date 2021/11/22
 */
public class ThreadLocalDemo {
    //    # 创建线程副本，默认至1
    static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 1);


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
//            # 获取值
                int n = threadLocal.get();
//            # ++
                n++;
                System.out.println(Thread.currentThread().getId() + ": " + n);
            }).start();
        }
    }

}

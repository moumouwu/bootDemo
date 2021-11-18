package com.boot.juc.thread;

import java.util.Date;

/**
 * @author binSin
 * @date 2021/11/12
 * <p>Synchronized 详解</p>
 */
public class SynchronizedDemo1 {

    // synchronized 修饰方法
    // 两个非静态方法
    // 同一对象 ==》同步  不同的对象 ==》异步

    // 两个都是静态方法
    // 同一对象 ==》同步 不同的对象 ==》同步

    // 一个静态 一个非静态
    // 同一对象 ==》异步 不同的对象 ==》异步

    // synchronized 修饰方法块 this对象锁
    // 两个非静态方法
    // 同一对象 ==》同步  不同的对象 ==》异步

    // synchronized 修饰方法块 Demo.class 类锁
    // 两个非静态方法
    // 同一对象 ==》同步  不同的对象 ==》同步

    // synchronized 修饰方法块 一个Demo.class类锁 一个this对象锁
    // 两个非静态方法
    // 同一对象 ==》异步  不同的对象 ==》异步

    // synchronized 修饰方法块 一个Demo.class类锁 一个Demo2.class类锁
    // 两个非静态方法
    // 同一对象 ==》异步  不同的对象 ==》异步

    // 1、对于静态方法，由于此时对象还未生成，所以只能采用类锁；
    // 2、只要采用类锁，就会拦截所有线程，只能让一个线程访问。
    // 3、对于对象锁（this），如果是同一个实例，就会按顺序访问，但是如果是不同实例，就可以同时访问。
    // 4、如果对象锁跟访问的对象没有关系，那么就会都同时访问。


    public static void main(String[] args) throws InterruptedException {
        Demo demo = new Demo();
        Demo demo2 = new Demo();
        Demo2 demo3 = new Demo2();
//        new Thread(() -> {
//            demo.test1();
//        }, "AA").start();
//
//        new Thread(() -> {
//            demo2.test2();
//        }, "BB").start();

//        new Thread(() -> {
////            demo.test3();
////        }, "CC").start();

        new Thread(() -> {
            demo.test4();
        }, "CC").start();

        new Thread(() -> {
            demo.test5();
        }, "CC2").start();

//
//        new Thread(() -> {
//            demo2.test5();
//        }, "DD").start();

//        new Thread(() -> {
//            demo2.test5();
//        }, "DD2").start();

//        new Thread(() -> {
//            demo3.test4();
//        }, "demo2.test").start();

    }
}

class Demo2 {
    public void test4() {
        synchronized (this) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "::测试同步代码块");
            }
        }
    }
}

class Demo {
    public synchronized void test1() {
        try {
            Thread.sleep(1000);
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ":: 循环第" + i + "次");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void test2() {
        System.out.println(Thread.currentThread().getName() + ":: 只循环一次的方法");
    }

    public void test3() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "::没有synchronized关键字的普通方法");
        }
    }

    public void test4() {
        synchronized (this) {
            try {
                Thread.sleep(1000);
                System.out.println(new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "::测试同步代码块");
            }
        }
    }


    public void test5() {
        synchronized (new Demo2()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "::测试同步代码块");
            }
        }
    }

}




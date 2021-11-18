package com.boot.juc.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author binSin
 * @date 2021/11/10
 * <p>
 * 创建线程的三种常见方式
 * </p>
 */
public class ThreadCreateDemo {

    public static void main(String[] args) throws Exception {
        // 方式一： 通过实现Runnable接口来创建Thread线程
        Runnable runnable = new DemoRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        // lambda表达式创建
        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " :: lambda表达式创建");
        });
        thread1.start();

        // 方式二：通过继承Thread类来创建一个线程
        Thread thread2 = new DomeThread();
        thread2.start();

        // 方式三： 通过Callable接口来创建Thread线程
        FutureTask<Object> futureTask = new FutureTask<>(new DemoCallable<>());
        Thread thread3 = new Thread(futureTask);
        thread3.start();
        System.out.println(futureTask.get());
    }


}

class DemoRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " :: 通过Runnable接口来创建Thread线程");
    }
}

class DomeThread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " :: 通过继承Thread类来创建一个线程");
    }
}

class DemoCallable<Object> implements Callable<Object> {

    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " :: 通过实现Callable接口来创建一个线程");
        // 可以返回数据
        return (Object) "返回数据";
    }
}

package com.boot.juc.thread;


import java.util.concurrent.*;

/**
 * @author binSin
 * @date 2021/11/16
 */
public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        FutureTask<Integer> futureTask = new FutureTask<>(new CallableDemo1<Integer>());

        new Thread(futureTask,"BB").start();
        System.out.println(futureTask.get());
        // 我们来测试一下任务是否已经完成
        System.out.println(futureTask.isDone());


    }
}
class CallableDemo1<Integer> implements Callable<java.lang.Integer> {


    @Override
    public java.lang.Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"::通过实现Callable接口来执行任务，并返回结果！");
        return 1024;
    }
}


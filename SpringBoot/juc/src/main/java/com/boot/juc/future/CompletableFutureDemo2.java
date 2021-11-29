package com.boot.juc.future;


import java.util.concurrent.CompletableFuture;

/**
 * @author binSin
 * @date 2021/11/19
 */
public class CompletableFutureDemo2 {

    /**
     * 没有返回值的异步任务
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        System.out.println("主线程开始");
        //运行一个没有返回值的异步任务
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("子线程启动干活");
                Thread.sleep(5000);
                System.out.println("子线程完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //主线程阻塞
        System.out.println("等待子线程完成");
        future.get();
        System.out.println("主线程结束");
    }
}



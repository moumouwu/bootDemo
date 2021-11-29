package com.boot.juc.future;

import java.util.concurrent.CompletableFuture;

/**
 * @author binSin
 * @date 2021/11/19
 */
public class CompletableFutureDemo3 {

    /**
     * 有返回值的异步任务
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        System.out.println("主线程开始");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("子线程开始");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "子线程完成任务";
        });
        System.out.println(future.get());
        System.out.println("主线程结束");
    }
}

package com.boot.juc.future;


import java.util.concurrent.CompletableFuture;

/**
 * @author binSin
 * @date 2021/11/19
 */
public class CompletableFutureDemo10 {

    private static Integer num = 10;

    /**
     * anyOf: 只要在多个 future 里面有一个返回，整个任务就可以结束，而不需要等到每一个 future 结束
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        System.out.println("主线程开始");

        CompletableFuture<Integer>[] futures = new CompletableFuture[4];
        CompletableFuture<Integer> job1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("加 10 任务开始");
                num += 10;
                return num;
            } catch (Exception e) {
                return 0;
            }
        });
        futures[0] = job1;
        CompletableFuture<Integer> job2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("乘以 10 任务开始");
                num = num * 10;
                return num;
            } catch (Exception e) {
                return 1;
            }
        });
        futures[1] = job2;
        CompletableFuture<Integer> job3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("减以 10 任务开始");
                num = num - 10;
                return num;
            } catch (Exception e) {
                return 2;
            }
        });
        futures[2] = job3;
        CompletableFuture<Integer> job4 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
                System.out.println("除以 10 任务开始");
                num = num / 10;
                return num;
            } catch (Exception e) {
                return 3;
            }
        });
        futures[3] = job4;
        CompletableFuture<Object> future = CompletableFuture.anyOf(futures);
        System.out.println(future.get());
    }
}
//主线程开始
//乘以 10 任务开始
//100


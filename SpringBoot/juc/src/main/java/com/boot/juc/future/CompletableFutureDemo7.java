package com.boot.juc.future;


import java.util.concurrent.CompletableFuture;

/**
 * @author binSin
 * @date 2021/11/19
 */
public class CompletableFutureDemo7 {

    private static Integer num = 10;

    /**
     * thenCompose 合并两个有依赖关系的 CompletableFutures 的执行结果
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("主线程开始");
        //第一步加 10
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("让num+10;任务开始");
            num += 10;
            return num;
        });
        //合并
        CompletableFuture<Integer> future1 = future.thenCompose(i ->
                //再来一个 CompletableFuture
                CompletableFuture.supplyAsync(() -> {
                    return i + 1;
                }));
        System.out.println(future.get());
        System.out.println(future1.get());
    }
}
/**
 * 主线程开始
 * 让num+10;任务开始
 * 20
 * 21
 */


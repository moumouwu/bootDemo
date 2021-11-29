package com.boot.juc.future;

import java.util.concurrent.CompletableFuture;

/**
 * @author binSin
 * @date 2021/11/19
 */
public class CompletableFutureDemo4 {

    private static String action = "";

    /**
     * 当一个线程依赖另一个线程时，可以使用 thenApply 方法来把这两个线程串行化
     * @param args
     */
    public static void main(String[] args) throws Exception {
        System.out.println("主线程开始");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            action="计划去烧烤";
            return action;
        }).thenApply(String->{
            return action+="到店里->点烧烤";
        }).thenApply(String->{
            return action+="吃完回家";
        });
        System.out.println(future.get());
        System.out.println("主线程结束");

    }
}

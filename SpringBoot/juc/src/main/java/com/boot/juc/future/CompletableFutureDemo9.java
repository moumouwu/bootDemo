package com.boot.juc.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author binSin
 * @date 2021/11/19
 */
public class CompletableFutureDemo9 {

    private static Integer num = 10;

    /**
     * 合并多个任务的结果
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        System.out.println("主线程开始");
        List<CompletableFuture> list = new ArrayList<>();

        CompletableFuture<Integer> job1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("加 10 任务开始");
            return num + 10;
        });
        list.add(job1);

        CompletableFuture<Integer> job2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("乘以 10 任务开始");
            return num * 10;
        });
        list.add(job2);

        CompletableFuture<Integer> job3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("减以 10 任务开始");
            return num - 10;
        });
        list.add(job3);

        CompletableFuture<Integer> job4 = CompletableFuture.supplyAsync(() -> {
            System.out.println("除以 10 任务开始");
            return num / 10;
        });
        list.add(job4);

        // 多任务合并
        List<Integer> collect =
                list.stream().map(CompletableFuture<Integer>::join).collect(Collectors.toList());
        System.out.println(collect);
    }

}
/**
 * 主线程开始
 * 乘以 10 任务开始
 * 加 10 任务开始
 * 减以 10 任务开始
 * 除以 10 任务开始
 * [110, 100, 100, 10]
 */

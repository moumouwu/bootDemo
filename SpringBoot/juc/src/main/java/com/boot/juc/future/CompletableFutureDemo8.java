package com.boot.juc.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

/**
 * @author binSin
 * @date 2021/11/19
 */
public class CompletableFutureDemo8 {

    private static Integer sum = 0;
    private static Integer count = 1;

    /**
     * thenCombine 合并两个没有依赖关系的 CompletableFutures 任务
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{

        System.out.println("主线程开始");

        CompletableFuture<Integer> job1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("从1+...+50开始");
            for (int i=1;i<=50;i++){
                sum+=i;
            }
            System.out.println("sum::"+sum);
            return sum;
        });

        CompletableFuture<Integer> job2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("从1*...*10开始");

            for (int i=1;i<=10;i++){
                count=count*i;
            }
            System.out.println("count::"+count);

            return count;
        });

        // 合并两个结果
        CompletableFuture<Object> future = job1.thenCombine(job2, new
                BiFunction<Integer, Integer, List<Integer>>() {
                    @Override
                    public List<Integer> apply(Integer a, Integer b) {
                        List<Integer> list = new ArrayList<>();
                        list.add(a);
                        list.add(b);
                        return list;
                    }
                });
        System.out.println("合并结果为:" + future.get());
    }
}
/**
 主线程开始
 从1*...*10开始
 从1+...+50开始
 sum::1275
 count::3628800
 合并结果为:[1275, 3628800]
 */

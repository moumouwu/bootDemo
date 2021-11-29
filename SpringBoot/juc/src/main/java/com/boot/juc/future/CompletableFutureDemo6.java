package com.boot.juc.future;


import java.util.concurrent.CompletableFuture;

/**
 * @author binSin
 * @date 2021/11/19
 */
public class CompletableFutureDemo6 {


    /**
     * 异常处理
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("主线程开始");
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            System.out.println("子线程执行中");
            return i;
        }).exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return -1;
        });
        System.out.println(future.get());

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务开始");
            int i = 1 / 0;
            return i;
        }).handle((i, ex) -> {
            System.out.println("进入 handle 方法");
            if (ex != null) {
                System.out.println("发生了异常,内容为:" + ex.getMessage());
                return -1;
            } else {
                System.out.println("正常完成,内容为: " + i);
                return i;
            }
        });

    }
}
/**
 * 主线程开始
 * java.lang.ArithmeticException: / by zero
 * -1
 */

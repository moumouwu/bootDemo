package com.boot.juc.future;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author binSin
 * @date 2021/11/19
 */
public class CompletableFutureDemo5 {

    private static String action = "";

    /**
     * thenAccept 消费处理结果, 接收任务的处理结果，并消费处理，无返回结果
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("主线程开始");
        CompletableFuture.supplyAsync(() -> {
//            try {
            action = "逛淘宝==";
            System.out.println("逛淘宝");
//            } catch (Exception e) {
//                System.out.println("手动异常");
//                e.printStackTrace();
//            }
            return action;
        }).thenRun(new Runnable() {
            @Override
            public void run() {
                action += "下单0";
                System.out.println("下单0");
            }
        }).thenApply(String -> {
            System.out.println("下单1");
            return action += "下单1";
        }).thenApply(String -> {
            int a = 1 / 0;
            return action + "等快递";
        }).exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return "-1";
        }).thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("子线程全部完成，最后调用了 accept:" + s);
            }
        });
    }
}

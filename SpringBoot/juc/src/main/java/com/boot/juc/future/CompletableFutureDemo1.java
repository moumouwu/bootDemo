package com.boot.juc.future;

import java.util.concurrent.CompletableFuture;


/**
 * @author binSin
 * @date 2021/11/18
 */
public class CompletableFutureDemo1 {

    /**
     * 主线程里面创建一个 CompletableFuture，然后主线程调用 get 方法会阻塞，最后我们在一个子线程中使其终止
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "子线程开始干活");
                //子线程睡 5 秒
                Thread.sleep(5000);
//                //在子线程中完成主线程 如果注释掉这一行代码将会一直停住
                future.complete("success");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();
        //主线程调用 get 方法阻塞
        System.out.println("主线程调用 get 方法获取结果为: " + future.get());
        System.out.println("主线程完成,阻塞结束!!!!!!");
    }
}


package com.boot.juc.demo;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author binSin
 * @date 2021/11/16
 */
public class CyclicBarrierDemo1 {

    public static void main(String[] args) throws Exception{

        // 第一个参数：目标障碍数  第二个参数：一个Runnable任务，当达到目标障碍数时，就会执行我们传入的Runnable
        // 当我们抽了201次的时候，就会执行这个任务。
        CyclicBarrier cyclicBarrier = new CyclicBarrier(21, () -> {
            System.out.println("恭喜你，已经抽奖201次，幸运值已满，下次抽奖必中荣耀水晶！！！");
        });

        for (int i = 1; i <= 20; i++) {
            final int count = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "抽奖一次");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

        // 这行代码是重置计数
//        cyclicBarrier.reset();
        // 这里是我又加了 一次循环， 可以看到最后结果中输出了两次 "恭喜你"
//        for (int i = 1; i <= 201; i++) {
//            final int count = i;
//            new Thread(() -> {
//                System.out.println(Thread.currentThread().getName() + "抽奖一次");
//                try {
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//            }, String.valueOf(i)).start();
//        }
    }
}




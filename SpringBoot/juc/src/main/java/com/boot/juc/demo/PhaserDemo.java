package com.boot.juc.demo;

/**
 * @author binSin
 * @date 2021/11/16
 */

import java.util.concurrent.Phaser;

public class PhaserDemo {

    private static Phaser phaser = new MyPhaser();

    //自定义一个移相器来自定义输出
    static class MyPhaser extends Phaser {
        /**
         * @deprecated 在即将到来的阶段提前时执行操作并控制终止的可覆盖方法。 此方法在推进此移相器的一方到达时调用（当所有其他等待方处于休眠状态时）。
         *             如果此方法返回true ，则此移相器将在提前时设置为最终终止状态，并且对isTerminated后续调用将返回 true。
         * @param phase 进入此方法的当前阶段号，在此移相器前进之前
         * @param registeredParties 当前注册方的数量
         * @return
         */
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            if (phase == 0) {
                System.out.println("所有人都到达了网吧，准备开始开黑！！！");
                return false;
            } else if (phase == 1) {
                System.out.println("大家都同意，一起去次烧烤咯！！！");
                return false;
            } else if (phase == 2) {
                System.out.println("大家一起回寝室！！！");
                return true;
            }
            return true;
        }
    }

    //构建一个线程任务
    static class DoSomeThing implements Runnable {
        @Override
        public void run() {
            /**
             * 向此移相器添加一个新的未到达方
             */
            phaser.register();
            System.out.println(Thread.currentThread().getName() + "从家里出发，准备去学校后街上网开黑！！！");
            phaser.arriveAndAwaitAdvance();
            System.out.println(Thread.currentThread().getName() + "上着上着饿了，说去吃烧烤吗？");
            phaser.arriveAndAwaitAdvance();
            System.out.println(Thread.currentThread().getName() + "烧烤吃完了");
            phaser.arriveAndAwaitAdvance();

        }
    }

    public static void main(String[] args) throws Exception {
        DoSomeThing thing = new DoSomeThing();
        new Thread(thing, "小明").start();
        new Thread(thing, "小王").start();
        new Thread(thing, "小李").start();
    }
}
/**
 * 小李从家里出发，准备去学校后街上网开黑！！！
 * 小王从家里出发，准备去学校后街上网开黑！！！
 * 小明从家里出发，准备去学校后街上网开黑！！！
 * 所有人都到达了网吧，准备开始开黑！！！
 * 小李上着上着饿了，说去次烧烤吗？
 * 小明上着上着饿了，说去次烧烤吗？
 * 小王上着上着饿了，说去次烧烤吗？
 * 大家都同意，一起去次烧烤咯！！！
 * 小明烧烤次完了
 * 小李烧烤次完了
 * 小王烧烤次完了
 * 大家一起回寝室！！！
 */


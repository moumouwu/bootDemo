package com.boot.juc.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binSin
 * @date 2021/11/19
 */
public class Demo2 {
    /**
     * 货物个数
     */
    static AtomicInteger cargoNum = new AtomicInteger(1000000);
//    static Integer cargoNum = new Integer(1000000);

    public static void main(String[] args) {
        Carry carry1 = new Carry("甲");
        Carry carry2 = new Carry("乙");

        carry1.start();
        carry2.start();
        System.out.println(455286+544714);
    }

    static class Carry extends Thread {

        /**
         * 搬运人
         */
        private String peopleName;
        /**
         * 搬运次数
         */
        private int carryNum;

        public Carry(String peopleName) {
            this.peopleName = peopleName;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                synchronized (cargoNum) {
                    if (cargoNum.get() > 0) {
                        cargoNum.addAndGet(-1);
//                synchronized (Carry.class) {
//                    if (cargoNum > 0) {
//                        cargoNum--;
                        carryNum++;
                    } else {
                        System.out.println("搬运完成，员工：" + peopleName + "，搬运：[" + carryNum + "]次");
                        interrupt();
                    }
                }
            }
        }
    }

}



package com.boot.juc.thread;

/**
 * @author binSin
 * @date 2021/11/10
 */
public class SynchronizedDemo {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "窗口A").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "窗口B").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "窗口C").start();

        MyThread myThread = new MyThread();
        new Thread(myThread, "黄牛A").start();
        new Thread(myThread, "黄牛B").start();
        new Thread(myThread, "黄牛C").start();
        new Thread(myThread, "黄牛D").start();
        new Thread(myThread, "黄牛E").start();
        new Thread(myThread, "黄牛F").start();
    }
}

class Ticket {
    // 票数
    private int number = 30;

    // 卖票
    public synchronized void sale() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + " : 余票 " + number--);
        }
    }
}

class MyThread implements Runnable {
    //票数是多个线程的共享资源
    private int ticket = 60;

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
//            this.sale();
            synchronized (this) {
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + "还剩下卖票：ticket = " + ticket--);
                }
            }
        }
    }

    // 声明同步方法
    public synchronized void sale() {
        // 还有票
        if (ticket > 0) {
            try {
                Thread.sleep(300); // 加入延迟
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "还剩下卖票：ticket = " + ticket--);
        }

    }
}

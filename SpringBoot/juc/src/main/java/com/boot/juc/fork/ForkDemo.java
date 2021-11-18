package com.boot.juc.fork;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author binSin
 * @date 2021/11/18
 */
public class ForkDemo {

    /**
     * Fork/Join
     * 基本思路是将一个大任务分解成若干个小任务，并行处理多个小任务，
     * 后再汇总合并这些小任务的结果便可得到原来的大任务结果。
     * <p>
     * Fork ：递归式的将大任务分割成合适大小的小任务。
     * Join：执行任务并合并结果。
     * <p>
     * RecursiveAction：一个递归无结果的ForkJoinTask（没有返回值）任务
     * RecursiveTask：一个递归有结果的ForkJoinTask（有返回值）任务
     *
     * fork()   // 在当前线程运行的线程池中安排一个异步执行。简单的理解就是再创建一个子任务。
     * join()    //当任务完成的时候返回计算结果。
     * invoke()    //开始执行任务，如果必要，等待计算完成。
     * @param args
     */
    public static void main(String[] args) {
        //定义任务
        TaskExample taskExample = new TaskExample(1, 1000);
        //定义执行对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //加入任务执行
        ForkJoinTask<Long> result = forkJoinPool.submit(taskExample);
        //输出结果
        try {
            System.out.println(result.get());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            forkJoinPool.shutdown();
        }
    }
}

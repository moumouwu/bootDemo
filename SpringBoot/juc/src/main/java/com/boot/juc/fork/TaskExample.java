package com.boot.juc.fork;

import java.util.concurrent.RecursiveTask;

/**
 * @author binSin
 * @date 2021/11/18
 */
public class TaskExample extends RecursiveTask<Long> {

    private int start;

    private int end;

    private long sum;

    public TaskExample(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        System.out.println("任务:" + start + "=======" + end + "累加开始");
        // 大于100个书相机切分，小于直接加
        if (end - start <= 100) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 切分为两块
            int middle = start + 100;
            // 递归调用，切分为两个小任务
            TaskExample taskExample = new TaskExample(start, middle);
            TaskExample taskExample1 = new TaskExample(middle + 1, end);
            // 执行异步
            taskExample.fork();
            taskExample1.fork();
            sum = taskExample.join() + taskExample1.join();
        }
        return sum;
    }

}

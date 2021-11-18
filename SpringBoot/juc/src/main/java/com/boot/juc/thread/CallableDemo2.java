package com.boot.juc.thread;

/**
 * @author binSin
 * @date 2021/11/16
 */

import java.util.concurrent.*;

/**
 * @Author: crush
 * @Date: 2021-08-19 18:44
 * version 1.0
 */
public class CallableDemo2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        CallableAndFutureTest callableAndFutureTest = new CallableAndFutureTest();
        FutureTask<String> task = new FutureTask<>(callableAndFutureTest);
        new Thread(task).start();

//        System.out.println("尝试取消任务，传true表示取消任务，false则不取消任务::"+task.cancel(true));

        System.out.println("判断任务是否已经完成::"+task.isDone());

        // 结果已经计算出来，则立马取出来，如若摸没有计算出来，则一直等待，直到结果出来，或任务取消或发生异常。
        System.out.println("阻塞式获取结果::"+task.get());

        System.out.println("在获取结果时，给定一个等待时间，如果超过等待时间还未获取到结果，则会主动抛出超时异常::"+task.get(2, TimeUnit.SECONDS));

    }
}

class CallableAndFutureTest implements Callable<String> {
    @Override
    public String call() throws Exception {
        String str="";
        for (int i=0;i<10;i++){
            str+=String.valueOf(i);
            Thread.sleep(100);
        }
        return str;
    }
}

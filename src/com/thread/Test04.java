package com.thread;


import java.util.concurrent.*;

//解决的问题：传统的多线程开发时，新任务没有返回执行结果，
//引入异步任务的方案  FutureTask 任务类
//线程池：资源可控
public class Test04 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        //方法一：定义一个异步任务类，它有返回值
        FutureTask<Integer> task=new FutureTask<>(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                int count=0;
                for(int i=0;i<100;i++){
                    Thread.sleep(1);
                    count+=1;
                }
                return count;
            }
        });
        Thread t=new Thread(task);
        t.start();

        //**********最大的不同在此：可以获取  任务的执行结果
        //System.out.println("在主程序中获取新任务运行的结果："+ task.get()); //阻塞式方法
        System.out.println("在主程序中获取新任务运行的结果："+ task.get(1, TimeUnit.SECONDS)); //设置最大等待时间 //TimeoutException超时
        System.out.println("理解 get()为什么叫阻塞式方法");
    }

}


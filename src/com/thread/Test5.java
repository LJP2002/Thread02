package com.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Test5 {
    public static void main(String[] args){
        //线程池（不仅仅是提升性能，还有程序的健壮性）
        /*核心线程池的大小*/
        int corePoolSize=2;
        /*核心线程池的最大线程数*/
        int maxPoolSize=4;
        /*线程最大空闲时间*/
        long KeepAliveTime=10;
        /*时间单位*/
        TimeUnit unit=TimeUnit.SECONDS;  //enum枚举，常量
        /*有界阻塞队列 容量为2 最多允许放入两个 空闲任务*/
        BlockingQueue<Runnable> workQueue=new ArrayBlockingQueue<>(2);//10-(4正在运行的任务+2等待任务)
        /*线程创建工厂*/
        ThreadFactory threadFactory=new NameTreadFactory();
        /*线程池拒绝策略*/
        RejectedExecutionHandler handler=new MyIgnorePolicy();

        ThreadPoolExecutor executor=null;
        try {
            /*推荐的创建线程池的方式*/
            /*不推荐使用现成的API创建线程池*/
            executor=new ThreadPoolExecutor(corePoolSize,maxPoolSize,KeepAliveTime,unit,workQueue,threadFactory,handler);
            /*预启动所有核心线程  提升效率*/
            executor.prestartAllCoreThreads();
            /*任务数量*/
            int count=10;
            for(int i=1;i<=count;i++){
                RunnableTask task=new RunnableTask(String.valueOf(i));
                executor.submit(task);//提交任务到线程池   还有4个任务无法执行
            }
        }finally {
            assert executor!=null; //断言，可开关   -ea   -da
            executor.shutdown();
        }
    }

    static class NameTreadFactory implements ThreadFactory{

        /*线程id  AtomicInteger原子类*/
        private final AtomicInteger threadId=new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            Thread t=new Thread(r,"线程-"+threadId.getAndIncrement());//i++  => i  I+1  =>赋值
            System.out.println(t.getName()+"已经被创建");
            return t;
        }
    }
    /*线程池拒绝策略*/
    public static class MyIgnorePolicy implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            doLog(r,executor);
        }

        private void doLog(Runnable r, ThreadPoolExecutor executor) {
            //可做日志记录等。
            System.err.println("线程池："+executor.toString()+r.toString()+"被拒绝执行");
        }

    }
    /*任务类*/
    static class RunnableTask implements Runnable{
        private String name;
        public RunnableTask(String name){
            this.name=name;
        }
        @Override
        public void run() {
            try {
                System.out.println(this.toString()+"is running!");
                //让任务执行慢点
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "RunnableTask{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}

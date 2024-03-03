package com.thread.pro2_knn;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Test3_ThreadPool {
    public static void main(String[] args) {
        //线程池的引入
        //问题：程序中创建的线程太多了，
        //复习一下线程新知识
        //Array->Arrays(工具类)
        //Collection->Collections
        //扩展：ThreadPoolExecutor 的快速线程池创建的工具类   Executors
        //Executors.newFixedThreadPool();  //Fixed固定线程数
        //Executors.newCachedThreadPool();//Cached无界线程池，即线程数没有上限
        //Executors.newSingleThreadExecutor();//只有一个线程
        //Executors.newWorkStealingPool();//工作窃取线程池
        ThreadPoolExecutor executor= (ThreadPoolExecutor) Executors.newFixedThreadPool((int)(1.5*Runtime.getRuntime().availableProcessors()));
        executor.submit(new RunnableTask("任务类"));
    }
    static class RunnableTask implements Runnable{
        private String name;
        public RunnableTask(String name){this.name=name;}
        @Override
        public void run() {
            try {
                System.out.println(this.toString()+"is running!!!");
                //让任务执行慢点。
                Thread.sleep(3000);
            } catch (InterruptedException e) {
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

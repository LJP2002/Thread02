package com.thread;

import java.util.Date;

public class Test7 {
    public static void main(String[] args) {
        Thread ttt=new Thread(()->{
            //运行的操作
            while(true){
                Date d=new Date();
                System.out.println("lambda类显示的时间："+d);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        });
        ttt.setDaemon(true);//垃圾回收，资源释放   被设置为守护线程
        ttt.start();
        System.out.println("haha");
    }
}

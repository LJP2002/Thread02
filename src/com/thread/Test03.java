package com.thread;

import java.util.Date;

public class Test03 {
    public static void main(String[] args) {
        //任务要与线程绑定，再将线程运行
        Thread t=new Thread(new ShowTimeTask());
        t.start();
        Thread tt=new Thread(new InnerShowTimeTask());
        tt.start();



        //lambda写法：函数式写法
        Thread ttt=new Thread(()->{
            //运行的操作
            while(true){
                Date d=new Date();
                System.out.println("显示的时间："+d);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        });
        ttt.start();
        new Thread(()->{
            //运行的操作
            while(true){
                Date d=new Date();
                System.out.println("lambda--2显示的时间："+d);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }).start();
    }
    static class  InnerShowTimeTask implements Runnable{

        @Override
        public void run() {
            while(true){
                Date d=new Date();
                System.out.println("内部类显示的时间："+d);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
class ShowTimeTask implements Runnable{

    @Override
    public void run() {
        while(true){
            Date d=new Date();
            System.out.println("lambda显示的时间："+d);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

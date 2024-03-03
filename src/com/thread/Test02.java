package com.thread;

import java.util.Date;

public class Test02 {
    public static void main(String[] args) {
        //创建线程
        ShowTime showTime=new ShowTime();
        //线程启动
        showTime.start();//jvm-->线程类中的ShowTime()的run()..
        //内部类写法
        InnerShowTime ist=new InnerShowTime();
        ist.start();
        //匿名内部类写法
        Thread tt=new Thread(){
            @Override
            public void run() {
                //运行的操作
                while(true){
                    Date d=new Date();
                    System.out.println("匿名内部类显示的时间："+d);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
        };
        tt.start();
        System.out.println("Bye");
    }
    //内部类写法：如果只有Test2这个类使用到了这个线程类
    static class InnerShowTime extends Thread{
        @Override
        public void run() {
            //运行的操作
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

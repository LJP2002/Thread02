package com.thread;

import java.util.Date;
//外部类  Thread:线程
public class ShowTime extends Thread{
    @Override
    public void run() {
        //运行的操作
        while(true){
            Date d=new Date();
            System.out.println(d);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

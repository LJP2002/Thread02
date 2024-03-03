package com.thread;

import java.util.Date;

//priority  ->优先级(1-10)
//只是理论上被jvm调度到的机会多一些，并不保证。
public class Test8 {
    public static void main(String[] args) {
        ShowTime2 task = new ShowTime2();

        Thread stt = new Thread(task);
        stt.setPriority(10);
        stt.setName("线程一");//设置线程名
        stt.start();

        Thread t = new Thread(task,"线程二");
        t.setPriority(1);
        t.start();
    }
}

class ShowTime2 implements Runnable{
    @Override
    //run加入在线程中完成的操作
    public void run() {
        while (true){
            Date d = new Date();
            System.out.println( Thread.currentThread().getName() +  "时间输出："+ d);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println( e );
            }
        }
    }
}


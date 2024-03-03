package com.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test14_interrupt {
    public static void main(String[] args) throws InterruptedException {
        //当前线程main
        String threadName=Thread.currentThread().getName();
        NewThread nt=new NewThread();//创建新线程
        System.out.println(nt.printDate()+threadName+"线程启动");
        //启动新线程
        nt.start();
        //主线程休眠3秒，让子线程先输出三次时间后再发出中断信号
        Thread.sleep(3000);
        System.out.println(nt.printDate()+threadName+"发出中断信号，设置子线程中断");
        //对新线程  设置线程中断（发出中断信号）
        nt.interrupt();
        //主线程休眠3秒
        Thread.sleep(3000);
        System.out.println(nt.printDate()+threadName+"运行结束");
    }
}
class NewThread extends Thread{
    @Override
    public void run() {
        boolean flag=true;//业务开关
        //当前线程
        String threadName=Thread.currentThread().getName();
        int i=0;
        //for循环等待线程中断，只要当前线程不是中断态，则继续，是中断，则推出当前线程
        while(flag&&!Thread.currentThread().isInterrupted()) {//中断控制
            System.out.println(printDate() + threadName + "线程正在执行第：" + (++i) + "次");
            try {
                //耗时的操作
                //应该会执行3次
                //线程阻塞，如果线程收到中断操作信号将抛出异常
                Thread.sleep(1000);
            } catch (InterruptedException e) {//true=>false
                System.out.println(printDate()+threadName+"线程正在执行，收到中断1信号，进入catch块处理");
                //检测线程是否中断
                System.out.println(printDate()+threadName+"的状态："+this.isInterrupted());//false
                //如果需要维护中断状态，则需要重新设置中断状态
                //如果不需要，则不用调用，如果调用  interrupt()的话，则当前的线程的状态变为中断，这个while循环退出，程序结束
                Thread.currentThread().interrupt();//true  如果注释这一句话，则中断不起作用，while循环仍会继续，想像一下，这样对一些偶然出
            }
        }
            System.out.println(printDate()+threadName+"线程是否被中断（while的后面）："+this.isInterrupted());
            System.out.println(printDate()+threadName+"线程退出");
        }
        public String printDate(){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(new Date())+" ";
        }
    }

package com.thread;

import java.util.Random;

public class Test18_ticket {
    public static void main(String[] args) {
        SellTicketOp sto=new SellTicketOp(240);
        for(int i=0;i<24;i++) {
            Thread counter1 = new Thread(sto, "售票员----"+(i+1));
            counter1.start();
        }
    }
}
//售票任务
class SellTicketOp implements Runnable{
    int tickets;//票数：资源
    Random r=new Random();
    //private Object o=new Object();
    //构造方法完成初始化
    public SellTicketOp(int tickets){
        this.tickets=tickets;
    }
    @Override
    public void run() {
        //循环售票
        while (true) {
            //24个线程-》争抢SellTicketOp类的对象锁
            synchronized (this) {//this->SellTicketOp类的对象
                if (this.tickets > 0) {
                    System.out.println(Thread.currentThread().getName() + "正在售出第" + (tickets--) + "张票");
                    try {
                       // Thread.sleep(r.nextInt(50));//sleep当前线程不会释放锁
                        //Object的wait(时间)
                        wait(r.nextInt(50));//在等待时会释放锁--》其他线程就可以争抢锁，节省等待时间
                        //以上程序不结束？--》wait状态的线程，在等待队列，没有唤醒的话，是否会进行就绪态，系统调试不到这个任务
                        this.notifyAll();//notifyAll()通知所有的等待状态的线程，从等待队列唤醒
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    return;
                }
            }
        }
    }
}

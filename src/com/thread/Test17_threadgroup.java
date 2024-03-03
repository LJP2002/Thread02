package com.thread;

public class Test17_threadgroup {
    public static void main(String[] args) {
        TestThread task1=new TestThread();
        TestThread task2=new TestThread();
        ThreadGroup threadGroup=new ThreadGroup("新建线程组1");
        Thread t0=new Thread(threadGroup,task1);//将两个新线程加入到同一个组
        Thread t1=new Thread(threadGroup,task2);
        t0.start();
        t1.start();
        //通过线程组来管理线程
        System.out.println("活动的线程数："+threadGroup.activeCount());
        System.out.println("线程组的名称为："+threadGroup.getName());
        //线程组中断，则这个组中所有的线程全部中断
        threadGroup.interrupt();//发出中断信号
    }
}
class TestThread implements Runnable{

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()){//发出中断信号  interrupt()
                System.out.println("线程名："+Thread.currentThread().getName());
                Thread.sleep(3000);

            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
package com.thread;


//内存可见volatile
//在A线程中通过一个变量想控制B线程的运行
public class Test21_volatile {
    public static void main(String[] args) {
        Task task=new Task();
        Thread thread1=new Thread(task,"线程1");
        thread1.start();
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("在线程2中改变stop的值，以停止thread1");
                    //想控制thread1的运行，只要改stop的值为true即可
                    task.stop=true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread2.start();
    }
}
class Task implements Runnable{
   volatile boolean stop=false;//标量，用于标识当前线程运行的状态
    int i=0;
    @Override
    public void run() {
        long start=System.currentTimeMillis();
        while (!stop){
            i++;
        }
        System.out.println("线程退出，运行时间："+(System.currentTimeMillis()-start));
    }
}
package com.thread;

public class Test15_yield {
    //挂起线程
    public static void main(String[] args) {
        YieldOne y1=new YieldOne();
        YieldOne y2=new YieldOne();
        Thread t1=new Thread(y1,"a");
        Thread t2=new Thread(y1,"b");
        t1.setPriority(10);
        t1.start();
        t2.setPriority(1);//第二个线程的优先级高
        t2.start();
    }
}
class YieldOne implements Runnable{

    @Override
    public void run() {
        if("a".equals(Thread.currentThread().getName())){
            Thread.yield();//yield只会将执行权放给优先级高的线程
           /* try {
                Thread.sleep(100);//sleep不管优先级，只要调用sleep，则当前线程睡，其他接过执行权
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName()+":"+i);
        }
    }
}
package com.thread;




public class Test16_join {
    //main线程
    public static void main(String[] args) throws InterruptedException {
        //当前是main线程
        LifeCircle lc=new LifeCircle();
        System.out.println(lc.isAlive());//线程状态值
        lc.start();
        System.out.println(lc.isAlive());
        lc.join();//让lc先运行完，再执行main,挂起当前线程（main）
        System.out.println("主线程的其他操作。。。。。。");
        System.out.println(lc.isAlive());
        //FutureTask
    }
}
class LifeCircle extends Thread{
    @Override
    public void run() {
        int i=0;
        while ((++i)<10){
            System.out.println(Thread.currentThread().getName()+":"+i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
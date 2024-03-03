package com.thread;

public class Test19_deadlock implements Runnable{

    public int flag=1;
    static Object o1=new Object(),o2=new Object();//创建两个对象，以使用其俩个对象锁

    public static void main(String[] args) {
        Test19_deadlock td1=new Test19_deadlock();//任务一
        Test19_deadlock td2=new Test19_deadlock();//任务二
        td1.flag=1;
        td2.flag=0;
        Thread t1=new Thread(td1);
        Thread t2=new Thread(td2);
        t1.start();
        t2.start();
    }
    @Override
    public void run() {
        System.out.println("flg="+flag);
        if(flag==1){//对任务一：取o1,sleep(500),不释放o1->再取o2
            synchronized (o1){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2){
                    System.out.println("1");
                }
            }

        }
        if(flag==0){//对任务二：取o2,sleep(500),不释放o2->再取o1
            synchronized (o2){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1){
                    System.out.println("0");
                }
            }
        }
    }
}

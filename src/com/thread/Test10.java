package com.thread;

class DeadLock implements Runnable {
    private boolean flag;//标记属性
    private Object obj1;//锁住的对象
    private Object obj2;//锁住的对象

    public DeadLock(boolean flag, Object obj1, Object obj2) {
        this.flag = flag;
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    @Override
    public void run() {
        //true
        if (flag) {//如果设置为true,就让线程1进入到if语句中
            synchronized (obj1) {//锁住的是obj1对象
                //线程1持有obj1锁
                System.out.println(Thread.currentThread().getName() + "拿到了锁1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("等待锁2的释......");
                //我想在线程1中去使用线程2中的那个锁2 obj2
                //线程1里面想用obj2锁对象
                //也走不下去了
                //线程1也没有释放obj1
                synchronized (obj2) {
                    System.out.println("123");
                    System.out.println(Thread.currentThread().getName() + "拿到了锁1");

                }
            }
        }
        if (!flag) {//如果设置为false，就让线程2进入到if语句中
            synchronized (obj2) {//锁住的是obj2对象
                //线程2持有obj2这个锁
                System.out.println(Thread.currentThread().getName() + "拿到了锁2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("等待锁1的释......");
                //只有obj1释放掉以后，才能在线程2中对obj1加锁
                //想一个问题，如果obj1锁对象没有被释放，那么下面这个代码
                //线程2中去锁obj1
                //在这等着呢 往下走不下去了 线程2没有释放obj2对象
                synchronized (obj1) {
                    System.out.println("456");
                    System.out.println(Thread.currentThread().getName() + "拿到了锁1");
                }
            }
        }

    }
}
public class Test10 {
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();
        //线程1可以进入到run方法中 if (flag)
        DeadLock deadLock = new DeadLock(true, obj1, obj2);
        new Thread(deadLock, "线程1").start();
        //线程2 可以进入倒run方法中if(!flag)
        DeadLock deadLock1 = new DeadLock(false, obj1, obj2);
        new Thread(deadLock1, "线程2").start();
    }
}

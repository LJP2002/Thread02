package com.thread;

public class Test9 {
    public static void main(String[] args) {
        //通过interrupted()方法检测线程是否被中断
        //Thread.currentThread() 主线程
        System.out.println(Thread.currentThread().getName() + "线程是否中断：" + Thread.interrupted());//interrupted()

        //设置线程中断   发出线程中断信号，=》底层 interrupted属性修改
        Thread.currentThread().interrupt();

        //Thread.currentThread.stop();

        //通过interrupted()方法检测线程是否被中断  interrupted()也可以检测中断状态，同时还可以清除中断状态
        System.out.println(Thread.currentThread().getName() + "线程是否中断：" + Thread.interrupted());//返回true后，恢复状态
        //检测interrupted()是否会清除线程状态
        System.out.println(Thread.currentThread().getName() + "线程是否中断：" + Thread.interrupted());

        //对比：
        //检测当前线程是否被中断
       /* System.out.println(Thread.currentThread().getName() + "线程是否中断：" + Thread.currentThread().isInterrupted());//检测线程是否被中断，但不会改变线程状态
        //检测线程终端状态是否被清除
        System.out.println(Thread.currentThread().getName() + "线程是否中断：" + Thread.currentThread().isInterrupted());*/
    }

}

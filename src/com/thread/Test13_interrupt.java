package com.thread;

public class Test13_interrupt {
    public static void main(String[] args) {
        //通过interrupted()方法检测线程是否被中断
        Thread thread= Thread.currentThread();
        System.out.println(thread.getName() + "线程是否中断：" + thread.isInterrupted());//FALSE

        //测试这句话设置线程中断标识
        thread.interrupt();
        //检测当前线程是否被中断，不修改状态
        System.out.println(thread.getName() + "线程是否中断：" + Thread.currentThread().isInterrupted());//检测线程是否被中断，但不会改变线程状态 true
        //检测线程终端状态是否被清除
        System.out.println(thread.getName() + "线程是否中断：" + Thread.currentThread().isInterrupted());//true

        try {//响应线程中断的方式
            //线程休眠2秒
            Thread.sleep(20000);//本来要在main线程中睡眠20S的，但因为interrupt()被调用了，所有sleep被打断了
            System.out.println(thread.getName()+"线程休眠未被中断....");
        } catch (InterruptedException e) {
            //捕获到sleep被中断，且自动地将状态恢复 -》false(不用 interrupted)
            e.printStackTrace();
            //中断的处理
            System.out.println(thread.getName()+"线程休眠被中断....");
            //判断线程是否被中断，因为异常已经被处理完，所以状态恢复，扩展：在catch写处理代码。。
            System.out.println(thread.getName()+"线程是否被中断："+thread.isInterrupted());//在异常中响应了中断，即将中断又恢复了。false
        }
        System.out.println(thread.getName()+"线程是否被中断:"+thread.isInterrupted());//false
    }
}

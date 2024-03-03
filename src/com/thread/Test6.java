package com.thread;

public class Test6 {
    //主线程相关信息
    public static void main(String []args){
        System.out.println(Thread.currentThread().getId()+"\t"+Thread.currentThread().getName()+
                "\t"+Thread.currentThread().getPriority()+"\t"+Thread.currentThread().getThreadGroup());
        System.out.println(Thread.currentThread().getState());
    }
}

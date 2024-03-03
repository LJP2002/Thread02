package com.thread;

import java.util.Date;

public class Test01 {
//单线程版本
    public static void main(String[] args) {
	//运行任务
        for(int i=0;i<=1000;i++){
            System.out.println(new Date());
        }
        System.out.println("Bye..........");
    }
}

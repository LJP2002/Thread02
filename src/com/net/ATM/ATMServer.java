package com.net.ATM;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ATM服务器类
 */
public class ATMServer {
    //引入线程池：1.工具类  Executors
    //            2.new ThreadPoolExecutor
    public static void main(String[] args) {
        int port=12000;
        if(args!=null&&args.length>0){
            port=Integer.parseInt(args[0]);
        }
        int numThreads=Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor tpe= (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
        Bank bank=new Bank();//在整个服务器中，只有一个Bank对象

        //创建  ServerSocket
        ServerSocket ss= null;
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(ss.getLocalPort()+"端口被占用....");//不能抛出异常影响别人
        }
        System.out.println("bank服务器"+ss.getInetAddress()+"启动");
        boolean flag=true;
        while (flag){
            Socket s= null;//创建套接字
            try {
                s = ss.accept();//socket怎么关？？？？
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("客户端连接服务器出问题了......");
                continue;//下一次循环
            }
            BankTask task= new BankTask(s,bank);
            tpe.execute(task);//提交任务
        }
        tpe.shutdown();
    }
}

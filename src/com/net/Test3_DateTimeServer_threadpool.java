package com.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Test3_DateTimeServer_threadpool {
    //需求：开发一台时间校准服务器，只要有客户端连上来，则向客户端回送一个北京时间
    public static void main(String[] args) throws IOException {
        final int port=10002;//服务器对外提供服务的端口
        ServerSocket ss=new ServerSocket(port);
        System.out.println("服务器："+ss.getInetAddress().getHostName()+"启动，监听了端口："+ss.getLocalPort());
        ThreadPoolExecutor pool= (ThreadPoolExecutor) Executors.newFixedThreadPool(2*Runtime.getRuntime().availableProcessors());
    while (true){
        Socket socket=ss.accept();//阻塞方法
        System.out.println("客户端："+socket.getRemoteSocketAddress()+"联接到了本服务器");

        /*Thread t=new Thread(new SocketTimeTask(socket));
        t.start();*/

        pool.execute(new SocketTimeTask(socket));


       }
    }
}
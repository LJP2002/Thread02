package com.net;

import java.io.IOException;
import java.net.Socket;

public class Test2_Socket_client {
    public static void main(String[] args) throws IOException {
        //Socket编程
        Socket s=new Socket("www.baidu.com",80);
        System.out.println(s);//localport指的是本机用于发送数据的商品
        System.out.println("Local Port: " + s.getLocalPort());//本机用于发送数据的端口号
    }

}




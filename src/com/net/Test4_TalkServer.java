package com.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Test4_TalkServer {
    public static void main(String[] args) throws IOException {
        final int port = 10002;//服务器对外提供服务的端口
        ServerSocket ss = new ServerSocket(port);
        System.out.println("服务器：" + ss.getInetAddress().getHostName() + "启动，监听了端口：" + ss.getLocalPort());
        Scanner keyword=new Scanner(System.in);//从键盘输入
        while (true) {
            //因为我们确定这是一个聊天服务器，只可能传输文本
            try(    Socket socket = ss.accept();//阻塞方法
                    Scanner sc=new Scanner(socket.getInputStream());//从socket获取字节流转为字符流
                    PrintWriter out=new PrintWriter(socket.getOutputStream());
            ) {
                System.out.println("客户端：" + socket.getRemoteSocketAddress() + "联接到了本服务器");
                do{
                    String response=sc.nextLine();//先获取客户端向我说的话
                    System.out.println("客户端向服务器说："+response);
                    if("bye".equalsIgnoreCase(response)){
                        System.out.println("客户端"+socket.getRemoteSocketAddress()+"主动断开与服务器的连接。。。。");
                        break;
                    }
                    System.out.println("请输入服务器想向客户端发送的话：");
                    String line=keyword.nextLine();
                    //发出line这句话到客户端
                    out.println(line);
                    out.flush();
                    if("bye".equalsIgnoreCase(line)){
                        System.out.println("服务器"+ss.getInetAddress()+"不想搭理客户端"+socket.getRemoteSocketAddress()+"断开与客户端连接");
                    }
                }while (true);
                System.out.println("此轮聊天结束，服务器结束与客户端"+socket.getRemoteSocketAddress()+"的聊天");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

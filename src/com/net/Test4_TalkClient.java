package com.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Test4_TalkClient {
    public static void main(String[] args) throws IOException {
        Socket s=new Socket("173.18.12.77",10004);
        System.out.println("客户端连接服务器成功"+s);

        Scanner keyboard=new Scanner(System.in);
        try(Scanner iis=new Scanner(s.getInputStream());
            PrintWriter out=new PrintWriter(s.getOutputStream());
            ) {
            do {
                System.out.println("请输入您想向服务小姐姐发送的话：");
                String line=keyboard.nextLine();
                //发出line这句话到客户端
                out.println(line);
                out.flush();
                if("bye".equalsIgnoreCase(line)){
                    System.out.println("客户端"+s.getInetAddress()+"不想搭理服务器"+s.getRemoteSocketAddress()+",断开与客户端连接");
                    break;
                }
                String response=iis.nextLine();//先获取客户端向我说的话
                System.out.println("服务器向客户端说："+response);
                if("bye".equalsIgnoreCase(response)){
                    System.out.println("服务器"+s.getRemoteSocketAddress()+"主动断开与服务器的连接。。。");
                    break;
                }
            }while (true);
            s.close();
            System.out.println("客户端关机");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

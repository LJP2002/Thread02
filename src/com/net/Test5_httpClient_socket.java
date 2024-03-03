package com.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Test5_httpClient_socket {
    public static void main(String[] args) throws IOException {
        //需求：请开发一个客户端，用于访问web服务器
        //Socket开发
        String website="www.baidu.com";
        int port=80;
        String protocol="GET / HTTP/1.0\r\nHost: www.baidu.com\r\n\r\n";//http协议，-》应用层
        try(Socket s=new Socket(website,port);
            OutputStream oos=s.getOutputStream();
            InputStream iis=s.getInputStream();
            ByteArrayOutputStream baos=new ByteArrayOutputStream();//内存缓冲流
        ) {
            oos.write(protocol.getBytes());
            oos.flush();//发送http协议
            byte[]bs=new byte[1024];
            int length=-1;
            while ((length=iis.read(bs))!=-1){
                baos.write(bs,0,length);
            }
            baos.flush();
            byte[] result=baos.toByteArray();
            String str=new String(result);
            System.out.println(str);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

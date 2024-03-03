package com.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
//
public class Test3_DateTimeClient {
    public static void main(String[] args) throws IOException {
        //需求：连接到服务器，获取最新的时间
        //扩展一下天气预报，黄历啥的
        Socket s=new Socket("localhost",10002);
        System.out.println("客户端连接服务器成功"+s);
        //通过流来解释接收  方向：输入 字节
        try(InputStream iis=s.getInputStream();
            ByteArrayOutputStream baos=new ByteArrayOutputStream();) {
            byte[] bs=new byte[1024];
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

package com.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test_Address {
    public static void main(String[] args) throws UnknownHostException {
        //1.地址处理
        InetAddress addr=InetAddress.getLocalHost();//本机的地址
        System.out.println(addr);
        InetAddress[] ia2=InetAddress.getAllByName("www.baidu.com");
        if(ia2!=null&&ia2.length>0){
            for(InetAddress ia:ia2){
                System.out.println(ia);
            }
        }
    }
}

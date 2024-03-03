package com.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Test6_httpclient_URL_URLConnection_httpurlconnection {
    public static void main(String[] args) throws IOException {
        //1.URL类  统一资源定位符
        URL url=new URL("http://www.baidu.com");//URL地址：协议://ip或域名:端口
        //                                           这里没有写端口是因为http默认端口就是80
        URLConnection con=url.openConnection();
        String contentType=con.getContentType();
        int length=con.getContentLength();
        String encoding=con.getContentEncoding();
        System.out.println(encoding+"\t"+length+"\t"+contentType);

        //urlconnection肯定可以获取流
        try(InputStream iis=con.getInputStream();
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
                ){
            byte[] bs=new byte[1024];
            length=-1;
            while ((length=iis.read(bs))!=-1){
                baos.write(bs,0,length);
            }
            baos.flush();
            byte[]result=baos.toByteArray();
            String str=new String(result);
            System.out.println(str);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

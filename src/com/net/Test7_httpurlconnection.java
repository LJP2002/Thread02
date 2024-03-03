package com.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
// webservice服务:
// soap | http +http+xml
//Rest API服务
//http+json
public class Test7_httpurlconnection {
    public static void main(String[] args) throws IOException {
        //连接是http://www.hyycinfo.com   http服务器
        URL url=new URL("http://www.hyycinfo.com");
        //class HttpURLConnection extends URLConnection
        HttpURLConnection con= (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Host","www.hyycinfo.com");
        con.setRequestMethod("GET");
        // ......
        con.setDoInput(true);//表示可以获取输入流
        con.setDoOutput(true);//表示可以获取输出流

        String contentType=con.getContentType();
        int length=con.getContentLength();
        String encoding=con.getContentEncoding();
        System.out.println(contentType+"\t"+length+"\t"+encoding);


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

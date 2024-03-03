package com.net;

import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Test3_DateTimeServer_thread {
    //需求：开发一台时间校准服务器，只要有客户端连上来，则向客户端回送一个北京时间
    public static void main(String[] args) throws IOException {
        final int port=10002;//服务器对外提供服务的端口
        ServerSocket ss=new ServerSocket(port);
        System.out.println("服务器："+ss.getInetAddress().getHostName()+"启动，监听了端口："+ss.getLocalPort());
    while (true){
        Socket socket=ss.accept();//阻塞方法
        System.out.println("客户端："+socket.getRemoteSocketAddress()+"联接到了本服务器");



       }
    }
}

class SocketTimeTask implements Runnable{

    private Socket socket;

    public SocketTimeTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //从socket中取出  请求的客户端的真实的ip,
        //http://ws.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx?op=getCountryCityByIp  根据ip查找地址区名
        String cityName="长沙";
        List<String> weatherInfo=null;
        /*try {
            weatherInfo=WeatherUtil.getWeatherInfo(cityName);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }*/

        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //哪种流？方向(输入，（输出）) ，（字节）/字符。
        try(
            Socket ss=this.socket;
            OutputStream oos=socket.getOutputStream()){
            LocalDateTime dateTime=LocalDateTime.now();
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
            String standardTime="伟哥报时器："+dateTime.format(formatter);
            String result=standardTime+"\n";
            if(weatherInfo!=null){
                result+="天气状况为："+weatherInfo.toString();
            }
            oos.write(result.getBytes());
            oos.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("服务器断开与客户端的联系。。。。。");
    }
}
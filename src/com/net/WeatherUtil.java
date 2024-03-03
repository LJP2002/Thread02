package com.net;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherUtil {

//客户端
    public static List<String> getWeatherInfo(String cityName) throws ParserConfigurationException, IOException, SAXException,DocumentException {
        List<String> weatherList = new ArrayList<>();
        URL url = new URL("http://ws.webxml.com.cn/WebServices/WeatherWebService.asmx/getWeatherbyCityName?theCityName=" + cityName);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Host", "ws.webxml.com.cn");
        // ......
        con.setDoInput(true);//表示可以获取输入流
        con.setDoOutput(true);//表示可以获取输出流
        //解析xml  .  (***dom,sax,->>>框架  dom4j,jdom)
        try (InputStream iis = con.getInputStream();) {//天气预报输入流
            Document doc = null;
            //表示使用  dom4j中的sax解析方式
            SAXReader saxReader = new SAXReader();
            doc = saxReader.read(iis);//利用sax解析器实时解析   文件流中的xml数据
            Element root = doc.getRootElement();//获取xml的根节点
            List<Element> nodeList = root.elements("string");//根节点下只n个<string>节点
            if (nodeList != null && nodeList.size() > 0) {
                for (int i = 0; i < nodeList.size(); i++) {
                    Element node = nodeList.get(i);
                    weatherList.add(node.getStringValue());
                }
            }
            //return weatherList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherList;
    }

    /**
     * http://ws.webxml.com.cn/WebServices/WeatherWebService.asmx/getWeatherbyCityName?theCityName=城市名
     * GET /WebServices/WeatherWebService.asmx/getWeatherbyCityName?theCityName=城市名 HTTP/1.1
     * Host: ws.webxml.com.cn
     *
     * @param cityName
     * @return
     */

    public static String getWeather(String cityName) throws IOException {
        URL url = new URL("http://ws.webxml.com.cn/WebServices/WeatherWebService.asmx/getWeatherbyCityName?theCityName=" + cityName);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Host", "ws.webxml.com.cn");
        // ......
        con.setDoInput(true);//表示可以获取输入流
        con.setDoOutput(true);//表示可以获取输出流

        try (InputStream iis = con.getInputStream();
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            byte[] bs = new byte[1024];
            int length = -1;
            while ((length = iis.read(bs)) != -1) {
                baos.write(bs, 0, length);
            }
            baos.flush();
            byte[] result = baos.toByteArray();
            String str = new String(result, "utf-8");
            //解析这个str,这个str是一个xml数据
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}

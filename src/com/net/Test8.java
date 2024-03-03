package com.net;

import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Test8 {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, DocumentException {
        /*String str=WeatherUtil.getWeather("长沙");
        System.out.println(str);*/


        List<String >weatherInfo=WeatherUtil.getWeatherInfo("长沙");
        System.out.println(weatherInfo);

    }
}

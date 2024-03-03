package com.thread.pro2_knn;

import com.thread.pro2_knn.bean.BankMarketing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据加载器：读取数据文件data和测试文件test
 */
public class BankMarketingLoader {
    public static List<BankMarketing> load(String path){
        List<BankMarketing> list=new ArrayList<BankMarketing>();
        //TODO:读取path位置的文件，并解析成 BankMarketing对象
        // 技术：java.io流  File类，InputStream->二进制流
        //                                     =》利用InputStreamReader 包装成Reader  =>BufferedReader(装饰模式，)
        //                                                                                        .readLine() 读一行，

        //2.采用线程安全的文件操作类
        //BIO:BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        //NIO:new FileInputStream(path)=>InputStream iis=Files.newInputStream(path);

        //3.异常处理 try...catch...finally
        //转成jdk8以后  try...resource...
        Path p= Paths.get(path);
        try(InputStream iis= Files.newInputStream(p);
            //InputStreamReader  将字节流转为字符流
            //BufferedReader 缓冲流（按行读取）
            BufferedReader reader=new BufferedReader(new InputStreamReader(iis));
            ){//()中加入的是可以close()的资源（只要一个类实现Closable接口）,比如我们的流
            String line=null;
            while((line=reader.readLine())!=null){
                String data[]=line.split(";");
                BankMarketing bank=new BankMarketing();
                bank.setData(data);
                list.add(bank);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return list;
    }
}

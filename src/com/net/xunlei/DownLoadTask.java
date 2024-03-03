package com.net.xunlei;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadTask implements Runnable{
    private int i;//第几个线程
    private long fileSize;//文件总大小
    private int threadSize;//线程数
    private long sizePerThread;//每个线程要下载的文件大小
    private String url;//待下载的地址
    private String path;//下载到本地后的保存位置

    public DownLoadTask(int i, long fileSize, int threadSize, long sizePerThread, String url, String path) {
        this.i = i;
        this.fileSize = fileSize;
        this.threadSize = threadSize;
        this.sizePerThread = sizePerThread;
        this.url = url;
        this.path = path;
    }

    @Override
    public void run() {
        //计算当前线程的起始和终止位置
        long start=i*sizePerThread;
        long end=(i+1)*sizePerThread-1;
        long total=0;
        try(RandomAccessFile raf=new RandomAccessFile(path,"rw");){
            //RandomAccessFile叫随机文件访问   可以指定开始位置
            raf.seek(start);
            //创建url对象，开始下载
            URL u=new URL(this.url);
            HttpURLConnection con= (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(3000);
            //***  关键点： http的分段下载协议   Range
            con.setRequestProperty("Range","byte="+start+"-"+end);
            try (InputStream iis=new BufferedInputStream(con.getInputStream())){
                byte[] bs=new byte[100*1024];
                int len=-1;
                while ((len=iis.read(bs))!=-1){
                    raf.write(bs,0,len);
                    total+=len;
                }
                System.out.println("第"+i+"个线程下载完毕，下载的总数据量："+total+",下载位置："+start+"-"+end);
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package com.net.xunlei3;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

//增加线程池=》阻塞方案  CountDownLatch来代替原来的join()
public class XunLeiClient {
    public static void main(String[] args) {
        String s="http://dl.baofeng.com/baofeng5/bf5_new.exe";
        if(args!=null && args.length==1){
            s=args[0];
        }
        //1.待下载文件的大小
        long fileSize=getDownLoadFileSize(s);
        System.out.println("待下载的文件大小："+fileSize);
        //2.生成空文件（文件大小,文件名按时间生成+后缀名与待下载文件）
        String path=generateEmptyFile(fileSize,s);
        System.out.println("生成的空文件的路径："+path);
        //3.根据cpu资源确定线程数
        int threadSize=Runtime.getRuntime().availableProcessors();
        //每个线程要下载的文件大小  100   150
        long sizePerThread=fileSize%threadSize==0?fileSize/threadSize:fileSize/threadSize+1;

        ThreadPoolExecutor tpe= (ThreadPoolExecutor) Executors.newFixedThreadPool(threadSize);
        CountDownLatch cdl=new CountDownLatch(threadSize);
        //List<Thread> list=new ArrayList<>();
        //4.生成线程，开始下载
        for (int i=0;i<threadSize;i++){
            DownLoadTask task=new DownLoadTask(i,fileSize,threadSize,sizePerThread,s,path,cdl);
            tpe.execute(task);
            /*Thread t=new Thread(task);
            t.start();
            list.add(t);*/
        }
        try {
            cdl.await();//阻塞主线程，相当于下面的join()
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*for(Thread t:list){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        System.out.println("下载成功");
        tpe.shutdown();
    }

    /**
     * 根据fileSize , s 生成空文件
     * @param fileSize
     * @param s
     * @return
     */
    private static String generateEmptyFile(long fileSize, String s) {
        //文件的后缀名
        String extensionName=s.substring(s.lastIndexOf("."));
        System.out.println("待下载文件的后缀名："+extensionName);
        //文件的文件名：按时间
        LocalDateTime ldt=LocalDateTime.now();
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fileName=ldt.format(dtf);
        //文件的路径：user.home  环境变量=》取的是用户目录，这个目录我们有完全操作权限
        String path=System.getProperty("user.home")+ File.separator+fileName+extensionName;
        //文件的大小 fileSize==>用的类为RandomAccessFile()类  .setLength(文件大小)
        try(RandomAccessFile randomAccessFile=new RandomAccessFile(path,"rw");){
            randomAccessFile.setLength(fileSize);
        }catch (Exception e){
            e.printStackTrace();
        }
        return path;
    }

    //待下载文件的大小：HEAD->只会获取响应头，再取  Content-Length
    private static long getDownLoadFileSize(String s) {
        long length=-1;
        URL url= null;
        HttpURLConnection con=null;
        try {
            url = new URL(s);
            con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("HEAD");
            con.setConnectTimeout(3000);
            con.connect();
            length=con.getContentLength();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }
}

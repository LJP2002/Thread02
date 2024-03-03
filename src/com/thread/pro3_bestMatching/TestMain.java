package com.thread.pro3_bestMatching;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String path=System.getProperty("user.home");
        //System.out.println(path);
        path=path+ File.separator+"文件案例"+File.separator+"UK Advanced Cryptics Dictionary.txt";
        //System.out.println(path);
        List<String> list=WordsLoader.load(path);
        //System.out.println(list.size());
        //System.out.println(list);
        String word="ab";
        BestMatchingTask task=new BestMatchingTask(list,word,0,list.size());
        FutureTask<BestMatchingData> futureTask=new FutureTask<>(task);
        Thread t=new Thread(futureTask);
        t.start();
        BestMatchingData bestMatchingData=futureTask.get();//get()阻塞式方法，只有任务运行完了，才可以得到结果，程序继续向下。
        System.out.println(bestMatchingData.getDistance()+"\n"+bestMatchingData.getWords());
    }
}

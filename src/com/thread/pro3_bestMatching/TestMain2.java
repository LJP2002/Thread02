package com.thread.pro3_bestMatching;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TestMain2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String path=System.getProperty("user.home");
        //System.out.println(path);
        path=path+ File.separator+"文件案例"+File.separator+"UK Advanced Cryptics Dictionary.txt";
        //System.out.println(path);
        List<String> list=WordsLoader.load(path);
        System.out.println("词汇表总量："+list.size());
        Date start=new Date();
        String word="sdt";
        BestMatchingData result=BestMatchingConcurrentCalculation.getBestMatchingWords(word,list);
        Date end=new Date();
        int minDistance=result.getDistance();
        List<String> wordList=result.getWords();
        System.out.println("最短编辑距离为："+minDistance);
        for(String s:wordList){
            System.out.println(s+"\t");
        }
        System.out.println("最短执行为："+(end.getTime()-start.getTime()));
    }
}

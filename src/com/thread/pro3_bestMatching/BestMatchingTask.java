package com.thread.pro3_bestMatching;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * 任务类
 */
public class BestMatchingTask implements Callable<BestMatchingData> {
    private List<String> words;
    private String word;
    private int startIndex;
    private int endIndex;
    private CountDownLatch controller;

    public BestMatchingTask(List<String> words, String word, int startIndex, int endIndex) {
        this.words = words;
        this.word = word;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public BestMatchingTask(List<String> words, String word, int startIndex, int endIndex, CountDownLatch controller) {
        this.words = words;
        this.word = word;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.controller = controller;
    }

    @Override
    public BestMatchingData call() throws Exception {
        List<String> minDistanceWords=new ArrayList<>();//最短距离的单词列表
        int minDistance=Integer.MAX_VALUE;
        int distance;//计算的距离
        //循环words集合从 startIndex到endIndex,计算word与这些单词之间的编辑距离
        for(int i=startIndex;i<endIndex;i++){
            //记录最小的编辑距离 及它对应的单词列表。
            distance=EditDistance.calculate(word,words.get(i));
            //找更短距离的单词
            if(distance<minDistance){
                minDistanceWords.clear();//先清空原来的集合
                minDistance=distance;//记录新的最短距离
                minDistanceWords.add(words.get(i));//存这个新的最短距离 对应的单词列表
            }else if(distance==minDistance){
                minDistanceWords.add(words.get(i));//如果这个单词与原来的最短单词距离一样，直接存
            }
        }
        //Thread.sleep(2000);
        //将最小的编辑距离 及单词列表包装成 BestMatchingData对象返回
        BestMatchingData result=new BestMatchingData();
        result.setDistance(minDistance);
        result.setWords(minDistanceWords);
        this.controller.countDown();//1
        return result;
    }
}

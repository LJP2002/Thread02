package com.thread.pro2_knn.group2;

import com.thread.pro2_knn.DistanceCalculator;
import com.thread.pro2_knn.EuclideanDistanceCalculator;
import com.thread.pro2_knn.bean.BankMarketing;
import com.thread.pro2_knn.bean.Distance;
import com.thread.pro2_knn.bean.Sample;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GroupDistanceTask implements Runnable{
    private Distance[] distances;
    private int startIndex;
    private int endIndex;
    private List<BankMarketing> dataSet;
    private Sample example;

    private DistanceCalculator edc=new EuclideanDistanceCalculator();
    private CountDownLatch latch;

    public GroupDistanceTask(Distance[] distances, int startIndex, int endIndex, List<BankMarketing> dataSet, Sample example) {
        this.distances = distances;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.dataSet = dataSet;
        this.example = example;
    }

    public GroupDistanceTask(Distance[] distances, int startIndex, int endIndex, List<BankMarketing> dataSet, Sample example, DistanceCalculator edc) {
        this.distances = distances;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.dataSet = dataSet;
        this.example = example;
        this.edc = edc;
    }

    public GroupDistanceTask(Distance[] distances, int startIndex, int endIndex, List<BankMarketing> dataSet, Sample example, DistanceCalculator edc, CountDownLatch latch) {
        this.distances = distances;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.dataSet = dataSet;
        this.example = example;
        this.edc = edc;
        this.latch = latch;
    }

    public void setEdc(DistanceCalculator edc){
        this.edc=edc;
    }
    @Override
    public void run() {
        //循环从startIndex到endIndex  计算  example与dataSet中各条数据的距离
        for(int index=startIndex;index<endIndex;index++){
            Sample sample=dataSet.get(index);
            //计算sample与测试数据example的距离
            distances[index]=new Distance();
            distances[index].setIndex(index);
            distances[index].setDistance(this.edc.calculate(sample,example));
        }
        latch.countDown();
    }
}

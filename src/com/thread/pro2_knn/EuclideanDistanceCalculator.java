package com.thread.pro2_knn;

import com.thread.pro2_knn.bean.Sample;

/**
 * 欧式距离计算算法
 */
public class EuclideanDistanceCalculator implements DistanceCalculator{
    //  父类的引用指向子类对象
    @Override
    public double calculate(Sample sample1, Sample sample2) {
        double ret=0.0;
        double[] data1=sample1.getExample();  //样本一的数据
        double[] data2=sample2.getExample();  //样本二的数据

        if(data1.length!=data2.length){
            throw new IllegalArgumentException("样本数据维度不一致");
        }
        for(int i=0;i<data1.length;i++){
            ret+=Math.pow(data1[i]-data2[i],2);
        }
        return Math.sqrt(ret);
    }
}

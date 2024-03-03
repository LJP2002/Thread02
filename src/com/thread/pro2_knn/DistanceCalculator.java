package com.thread.pro2_knn;

import com.thread.pro2_knn.bean.Sample;

/**
 * 距离计算接口
 */
public interface DistanceCalculator {

    public double calculate(Sample sample1, Sample sample2);
}

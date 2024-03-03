package com.thread.pro2_knn.bean;

/**
 * 这个类主要用于比较  两个Distance之间的大小
 */
public class Distance implements Comparable<Distance>{
    private int index;//拿测试集中的一条与data训练集中的39129
    //第index条数据的距离
    private double distance;
    //比较当前这个distance与传进来的参数o的大小
    @Override
    public int compareTo(Distance o) {
        if(this.distance>o.distance){
            return 1;
        }else if(this.distance==o.distance){
            return 0;
        }
        return -1;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}

package com.thread.pro3_bestMatching;

import java.util.List;

/**
 * sit  =》1
 *        sid  sie  sif....
 * 编辑距离最短的单词列表
 */
public class BestMatchingData {
    private int distance;//最短距离
    private List<String> words;//这个距离，对应的单词列表

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}

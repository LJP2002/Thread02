package com.thread.pro1_matrix.row;

import java.util.ArrayList;
import java.util.List;

/**
 * 并行第二版：结果矩阵中的每一行一个任务
 */
public class ParallelRowMultiplier {
    public static  void mutiply(double[][] matrix1,double[][] matrix2,double[][] result){
        List<Thread> threads=new ArrayList<Thread>();
        int rows1=matrix1.length;//第一个矩阵的行
        int column1=matrix1[0].length;//第一个矩阵的列
        int row2=matrix2.length;//第二个矩阵的行
        int column2=matrix2[0].length;//第二个矩阵的列
        for(int i=0;i<rows1;i++){
                //总共  rows1=2000个线程
                //创建线程
                Thread t=new Thread(new RowMultiplierTask(result,matrix1,matrix2,i));
                t.start();
                threads.add(t);
                if(threads.size()%10==0){
                    waitForThread(threads);
                }

        }
    }
    //如何知道电脑有几个核？ java.lang包-》Runtime.getRuntime() .
    //几个核就是几个线程？--》负载    1.    2.
    //2000/12=167行
    //0: 0-166行
    //1: 167-332行
    //   ...
    private static void waitForThread(List<Thread> threads){
        for (Thread t:threads){
            try {
                t.join();//t先运行，其他的线程等
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

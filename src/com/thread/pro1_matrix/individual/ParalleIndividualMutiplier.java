package com.thread.pro1_matrix.individual;

import java.util.ArrayList;
import java.util.List;

/**
 * 串行的乘法器
 */
public class ParalleIndividualMutiplier {
    public static  void mutiply(double[][] matrix1,double[][] matrix2,double[][] result){
        List<Thread> threads=new ArrayList<Thread>();
        int rows1=matrix1.length;//第一个矩阵的行
        int column1=matrix1[0].length;//第一个矩阵的列
        int row2=matrix2.length;//第二个矩阵的行
        int column2=matrix2[0].length;//第二个矩阵的列
        for(int i=0;i<rows1;i++){
            for(int j=0;j<column2;j++){
                //创建线程
                Thread t=new Thread(new IndividualMultiplierTask(result,matrix1,matrix2,i,j));
                t.start();
                threads.add(t);
                if(threads.size()%10==0){
                    waitForThread(threads);
                }
            }
        }
    }
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

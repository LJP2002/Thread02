package com.thread.pro1_matrix;

import com.thread.pro1_matrix.group.ParallelGroupMultiplier;

public class Test4_ParallelGroupMain {
    public static void main(String[] args) {
        //生成两个矩阵
        double matrix1[][]=MatrixGenerator.generate(2000,2000);
        double matrix2[][]=MatrixGenerator.generate(2000,2000);//4 000 000
        //结果矩阵
        double result[][]=new double[matrix1.length][matrix2[0].length];
        long start,end;
        start=System.currentTimeMillis();
        //SerialMultiplier.multiply(matrix1,matrix2,result);//单线程计算矩阵乘法
        //ParalleIndividualMutiplier.mutiply(matrix1,matrix2,result);//结果矩阵的每个元素一个线程的版本：细粒度多线程版
        //ParallelRowMultiplier.mutiply(matrix1,matrix2,result);
        ParallelGroupMultiplier.mutiply(matrix1,matrix2,result);
        end=System.currentTimeMillis();
        System.out.println("细粒度版__按cpu的核数 生成任务的版本：计算矩阵乘法的时间："+(end-start));//10313
    }
}

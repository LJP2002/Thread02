package com.thread.pro1_matrix;

import com.thread.pro1_matrix.single.SerialMultiplier;

public class Test1_SerialMatrix {
    public static void main(String[] args) {
        //生成两个矩阵
        double matrix1[][]=MatrixGenerator.generate(2000,2000);
        double matrix2[][]=MatrixGenerator.generate(2000,2000);
        //结果矩阵
        double result[][]=new double[matrix1.length][matrix2[0].length];
        long start,end;
        start=System.currentTimeMillis();
        SerialMultiplier.mutiply(matrix1,matrix2,result);//单线程矩阵乘法
        //ParalleIndividualMutiplier.mutiply(matrix1,matrix2,result);//多线程矩阵乘法
        end=System.currentTimeMillis();
        System.out.println("串行计算矩阵乘法的时间："+(end-start));//45079
    }
}

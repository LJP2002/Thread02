package com.thread.pro1_matrix.single;

/**
 * 串行的乘法器
 */
public class SerialMultiplier {
    public static  void mutiply(double[][] matrix1,double[][] matrix2,double[][] result){
        int rows1=matrix1.length;//第一个矩阵的行
        int column1=matrix1[0].length;//第一个矩阵的列
        int row2=matrix2.length;//第二个矩阵的行
        int column2=matrix2[0].length;//第二个矩阵的列
        for(int i=0;i<rows1;i++){
            for(int j=0;j<column2;j++){
                result[i][j]=0;
                for(int k=0;k<column1;k++){
                    result[i][j]+=matrix1[i][k]*matrix2[k][j];
                }
            }
            //放到Runnable中run()中
            //参数：结果矩阵，矩阵1，矩阵2，结果矩阵中i,j
        }


    }
}

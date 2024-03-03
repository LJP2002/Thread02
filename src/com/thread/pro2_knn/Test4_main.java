package com.thread.pro2_knn;

import com.thread.pro2_knn.bean.BankMarketing;
import com.thread.pro2_knn.group2.ParallelGroupKnnClassifier;

import java.util.List;

public class Test4_main {
    public static void main(String[] args) {
        String trainpath=System.getProperty("user.dir")+"\\out\\production\\Thread01\\com\\yc\\pro2_knn\\data\\bank.data";
        List<BankMarketing> train=BankMarketingLoader.load(trainpath);//训练集
        System.out.println("训练集大小："+train.size());
        String testpath=System.getProperty("user.dir")+"\\out\\production\\Thread01\\com\\yc\\pro2_knn\\data\\bank.test";
        List<BankMarketing> test=BankMarketingLoader.load(testpath);//训练集
        System.out.println("测试集大小："+test.size());

        //knn的k的确定
        int k=10;
        //java   Test2_main
        if(args!=null&&args.length>0){
            k=Integer.parseInt(args[0]);
        }
        //2059  *  12线程（39129）
        //定义两个变量存测试集，通过模型   预测准确率
        int success=0,mistakes=0;
        int numThreads=Runtime.getRuntime().availableProcessors();
        ParallelGroupKnnClassifier classifier=new ParallelGroupKnnClassifier(k,numThreads,true,train);

        long start,end;
        start=System.currentTimeMillis();
        //循环测试集中的每一条，调用这个模型进行预测
        for(BankMarketing testData:test){
            //tag就是模型预测的类别
            String tag=classifier.classify(testData);
            if(tag.equals(testData.getTag())){
                success++;
            }else {
                mistakes++;
            }
        }
        end=System.currentTimeMillis();
        System.out.println("按cpu的核数生成任务的版本：计算矩阵乘法的时间："+(end-start));//17655
        System.out.println("正确数为："+success+",错误数为："+mistakes+",正确率为："+((double)success/(success+mistakes)));
    }
}

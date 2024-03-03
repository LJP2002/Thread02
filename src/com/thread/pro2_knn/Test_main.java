package com.thread.pro2_knn;

import com.thread.pro2_knn.bean.BankMarketing;
import com.thread.pro2_knn.bean.Sample;

import java.util.Arrays;
import java.util.List;

/**
 * 大数据机器学习：
 * 1.数据清洗
 * 2.建模
 * 3.验证模型
 * 4.上线运行
 * 5.反馈
 * 因为数据脱敏，所以数据语义不明，但可以知道数据结构    数据+标签
 *                                             data 训练集 -》训练模型
 *                                             test 测试集 -》验证模型
 *分析算法（二分类算法）-》knn算法，k近邻算法
 * 1.计算一条数据与训练集中各数据的距离(2059*39129)
 * 2.距离排序
 * 3.取前k条数据的标签    Map<String,int>
 *                         标签名   数值
 *取数量最多的标签
 *
 *分析项目结构：
 * 1.数据读取：按行读取数据，一行一个对象，数据切割（一般按逗号）
 * 2.数据bean
 * 3.算法类
 * 4.线程调度类+任务类
 *
 * 划分多线程：
 * 计算一条数据与训练集中各数据的距离
 * 粗粒度版本：按cpu的资源划分线程数：12核=》12线程
 *
 * 距离排序
 * Distance distance[39129]
 * 引入并行排序：  Arrays.parallelSort(x);
 *
 */
public class Test_main {
    public static void main(String[] args) {
        //问题：路径：
        //用System.getProperty("user.dir");  项目的执行路径
        //System.getProperty("user.home");   用户目录：c:\\users\86151(window)          /home/用户名(linux)
        System.out.println(System.getProperty("user.dir")+"哈哈");
        //以上返回：D:\java\IDEAProjects\Thread01
        //out\production\Thread01\com\yc\pro2_knn\data
        String path=System.getProperty("user.dir")+"\\out\\production\\Thread01\\com\\yc\\pro2_knn\\data\\bank.data";
        System.out.println(path+"你好");
        List<BankMarketing> list=BankMarketingLoader.load(path);
        for(BankMarketing bm:list){
            System.out.println(bm);
        }
        System.out.println(list.size());

        EuclideanDistanceCalculator edc=new EuclideanDistanceCalculator();
        System.out.println(edc.calculate(new Student(2,4),new Student(3,6)));
        Student s=new Student(2,4);
        System.out.println(Arrays.toString(s.getExample()));
        System.out.println(s.toString());




    }
}
class Student extends Sample{

    private double weight;
    private double height;

    public Student(double weight, double height) {
        this.weight = weight;
        this.height = height;
    }

    @Override
    public String getTag() {
        return null;
    }

    @Override
    public double[] getExample() {
        return new double[]{weight,height};
    }

    @Override
    public String toString() {
        return "Student{" +
                "weight=" + weight +
                ", height=" + height +
                '}';
    }
}
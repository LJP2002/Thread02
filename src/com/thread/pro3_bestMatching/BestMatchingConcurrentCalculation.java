package com.thread.pro3_bestMatching;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池的任务分配及调度，结果汇总
 */
public class BestMatchingConcurrentCalculation {
    /**
     * 线程池的任务分配及调度，结果汇总
     * @param word
     * @param dictionary
     * @return
     */
    public static BestMatchingData getBestMatchingWords(String word, List<String> dictionary) throws InterruptedException, ExecutionException {
        BestMatchingData result=new BestMatchingData();
        //1.任务数
        int numCores=Runtime.getRuntime().availableProcessors();
        System.out.println(numCores+"核数：");
        ThreadPoolExecutor executor= (ThreadPoolExecutor) Executors.newFixedThreadPool(numCores);
        //System.out.println(executor+"..");
        CountDownLatch controller=new CountDownLatch(numCores);//2

        //2.计算每个任务对应的词汇量   startIndex endIndex
        int size=dictionary.size();
        int step=size/numCores;//任务的分区的大小
        int startIndex,endIndex;

        //3.创建   BestMatchingTask
        List<BestMatchingTask> tasks=new ArrayList<>();
        List<Future<BestMatchingData>> listFutures=new ArrayList<>();//2

        for(int i=0;i<numCores;i++){
            startIndex=i * step;
            //endIndex
            if(i==numCores-1){
                endIndex=size;
            }else {
                endIndex=(i+1)*step;
            }
            //BestMatchingTask task=new BestMatchingTask(dictionary,word,startIndex,endIndex);
            BestMatchingTask task=new BestMatchingTask(dictionary,word,startIndex,endIndex,controller);//2
            tasks.add(task);

            Future<BestMatchingData> future=executor.submit(task);//2
            //System.out.println(executor+"ok..");
            listFutures.add(future);//2
        }
        //4.将上面创建的BestMatchingTask提交给Executor线程池执行器运行
        //invokeAll()一次性提交任务后，只有所有的任务全部执行完毕，才会向下运行
        //换句说，即程序会阻塞此处
        //List<Future<BestMatchingData>> listFutures=executor.invokeAll(tasks);//一次性提交所有任务
        controller.await();//2
        System.out.println("如果任务没有完成，invokeAll阻塞此处");
        //System.out.println(executor+"ok..++");
        executor.shutdown();//因为任务完成了，所以关闭执行器
        //5.通过FutureTask.get()获取以上任务的执行结果  BestMatchingData,汇总（找最小距离及对应的词汇表）
        int minDistance=Integer.MAX_VALUE;
        List<String> words=new ArrayList<>();//最短距离 对应的单词
        for(Future<BestMatchingData>future:listFutures){
            BestMatchingData bestMatchingData=future.get();
            if(bestMatchingData.getDistance()<minDistance){
                words.clear();
                minDistance=bestMatchingData.getDistance();
                words.addAll(bestMatchingData.getWords());
            }else if(bestMatchingData.getDistance()==minDistance){
                //这个任务找到最短距离与已知的一样，则合并单词列表
                words.addAll(bestMatchingData.getWords());
            }
        }
        //6.包装成BestMatchingData
        result.setDistance(minDistance);
        result.setWords(words);
        return result;
    }
}

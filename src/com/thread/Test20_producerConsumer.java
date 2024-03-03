package com.thread;

import java.util.Random;

//生产者消费者
public class Test20_producerConsumer {
    public static void main(String[] args) {
        AppleBox ab=new AppleBox();
        //创建生产者
        Producer p=new Producer(ab);
        //Producer p2=new Producer(ab); //不管有多少个producer,他们操作同一个AppleBox
        //创建消费者
        Consumer c1=new Consumer(ab);
        Consumer c2=new Consumer(ab);
        //以上是任务，要用线程绑定
        new Thread(p).start();
        /*new Thread(p2).start();*/
        new Thread(c1).start();
        new Thread(c2).start();
    }
}

class Apple{
    int id;
    Apple(int id){
        this.id=id;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "id=" + id +
                '}';
    }

}
//消息中间键：存消息（apple）
class AppleBox{
    Apple[] apples=new Apple[5];
    int index=0;//index表示当前有几条消息

    //生产后存消息
    public synchronized void deposite(Apple apple){
        while (index==apples.length){
            try {
                this.wait();//notifyAll()  notify()
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        apples[index]=apple;
        this.notifyAll();//因为生产了一条消息，所以notifyAll()其他的线程（包括消费线程）可以运行了
        index++;
    }
    //消费消息
    public synchronized Apple withdraw(){
        while (index==0){//index==0
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           /* try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        index--;//index==0
        this.notifyAll();
        return apples[index];
    }

}
//生产消息的任务
class Producer implements Runnable{
    AppleBox ab=null;//生产完的消息要存放到appleBox中
    Producer(AppleBox ab){
        this.ab=ab;
    }

    @Override
    public void run() {
        Random r=new Random();
        for(int i=0;i<10;i++){
            Apple a=new Apple(i);
            ab.deposite(a);//存消息
            System.out.println(Thread.currentThread().getName()+"生产了消息："+a);//a.toString()
            try {
                Thread.sleep(r.nextInt(500));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class Consumer implements Runnable{
    AppleBox ab=null;//生产完的消息要存放到appleBox中
    Consumer(AppleBox ab){
        this.ab=ab;
    }
    public void run() {
        Random r=new Random();
        //从applebox中取消息出来消费
        for(int i=0;i<5;i++){
            Apple a=ab.withdraw();//取消息出来消费
            System.out.println(Thread.currentThread().getName()+"消费了消息："+a);//a.toString()
            try {
                Thread.sleep(r.nextInt(300));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
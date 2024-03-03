package com.thread;

//创建这个类的目的，就是实例化出来对象，然后拿这个对象
//调用wait方法和notify方法
//wait方法和notify方法是object对象的方法
class Message {
    private String message;

    public Message(String message) {

        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }

}
//导致当前线程等待，直到另一个线程调用该对象的[`notify()`
class WaiterThread implements Runnable {
    //想一个问题？WaiterThread  使用message对象调用
    //wait() 咋解决？
    private Message msg;

    public WaiterThread(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        //先获取当前线程名字
        String name = Thread.currentThread().getName();
        System.out.println(name + "等待唤醒时间:" +System.currentTimeMillis());
        //让等待线程去阻塞，去等待  这个线程执行不下去了
        //锁的是msg对象
        synchronized (msg) {//为啥是哟个wait的时候要加锁？等会将
            try {
                msg.wait();//代码走到这，当前这个线程阻塞，不往下走了
                //咱们得想办法让这个等待线程继续执行下去，咋办？
                //在另外一个线程中去调用notify方法那么等待线程就会执行下去
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("123");
            System.out.println(name + "被唤醒的时间:" + System.currentTimeMillis());
        }


    }
}
//唤醒线程
class NotifyThread implements Runnable {
    //也要用同一个对象是WaiterThread线程中同一个对象调用notify()方法
    private Message msg;

    public NotifyThread(Message msg) {
        this.msg = msg;
    }
    @Override
    public void run() {
        try {
            //我的想法是不能先让唤醒线程执行
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String name = Thread.currentThread().getName();
        System.out.println(name + "开始唤醒等待线程");
        synchronized (msg) {
            msg.setMessage("我是修改之后的message值");
            //msg.notify();
            msg.notifyAll();//唤醒所有线程
        }

    }
}
public class Test11 {
    public static void main(String[] args) {
        Message message = new Message("我是message属性");
        WaiterThread waiterThread = new WaiterThread(message);
        NotifyThread notifyThread = new NotifyThread(message);
        //如果等待线程好几个 咋办呢？
        new Thread(waiterThread, "等待线程1").start();
        new Thread(waiterThread, "等待线程2").start();
        new Thread(waiterThread, "等待线程3").start();
        new Thread(notifyThread, "唤醒线程").start();

        //等待线程等待唤醒时间:1660187660718   等待线程
        //唤醒线程开始唤醒等待线程        唤醒线程
        //123  等待线程
        //等待线程被唤醒的时间:1660187661740  等待线程
        //这叫线程之间的通信问题！！！
    }
}

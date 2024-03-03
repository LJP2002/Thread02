package com.thread;


//为啥要新建这类Goods？
//还记得上午讲的Message类
//因为两个线程要通信。这个Goods就是通信的桥梁！！！
//goods.wait()  消费者等待
//goods.notoify()  生产者唤醒消费者
class Goods {
    private String name;//商品名字
    private double price;//商品价格
    private boolean isProduct;//
    //isProduct是否有这个商品， true  没有这个产品需要生产
    //false  有这个产品，不需要生产
    //有参构造


    public Goods(String name, double price, boolean isProduct) {
        this.name = name;
        this.price = price;
        this.isProduct = isProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isProduct() {
        return isProduct;
    }

    public void setProduct(boolean product) {
        isProduct = product;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", isProduct=" + isProduct +
                '}';
    }
}
//接下来搞两个线程？一个消费者线程  一个是生产者线程
class Customer implements Runnable {
    //为啥要定义这个goods变量? 因为两个线程要共享同一个资源！！！

    private Goods goods;

    public Customer(Goods goods) {
        this.goods = goods;
    }

    @Override
    public void run() {
        //写业务逻辑，业务比较麻烦
        while (true) {//一直消费
            synchronized (goods) {
                //goods.isProduct  true 需要生产，没有商品  false 不需要生产
                if (!goods.isProduct()) {
                    //不需要生产的，消费者直接购买
                    System.out.println("消费者购买了:" + goods.getName() + ",价格为:" + goods.getPrice());
                    //购买完以后 商品没有了 将isProduct这个变量修改为true
                    goods.setProduct(true);
                    //大家有没有想过这个问题?消费者在购买的时候，生产者等待
                    //唤醒生产者去生产车了
                    goods.notify();//可以先防一下，等会再看！！！


                } else {
                    //需要生产的！！！，没商品（咋办）
                    //消费者进入到阻塞状态！！！
                    try {
                        goods.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }

    }
}
class Producter implements  Runnable {
    //为啥要定义这个goods变量?
    private Goods goods;
    public Producter(Goods goods) {
        this.goods = goods;
    }
    @Override
    public void run() {
        int count = 0;
        //生产者，业务逻辑比较麻烦
        while (true) {//你一直消费，我一直生产
            synchronized (goods) {
                if (goods.isProduct()) {//true  需要生产的！！
                    //造车，就是赋值 对goods对象进行赋值
                    //奇数造一种车， 偶数造另外一种车
                    if (count % 2 == 0) {//偶数
                        goods.setName("奥迪A8");
                        goods.setPrice(78.9);
                    } else {//奇数
                        goods.setName("五菱大光");
                        goods.setPrice(12.9);
                    }
                    //生产一辆车，一定要记得有车了
                    //标记就改为 false  就证明不需要再生产了
                    goods.setProduct(false);
                    System.out.println("生产者生产了:" + goods.getName() + ",价格为:" + goods.getPrice());

                    count++;
                    //生产完以后，唤醒消费者。让消费者去提车
                    goods.notify();

                } else {
                    //不需要生产
                    //不需要生产 有货，生产着歇着，阻塞
                    try {
                        goods.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
public class Test12 {
    public static void main(String[] args) {
        Goods goods = new Goods("东风41", 67.8, false);
        Producter producter = new Producter(goods);
        new Thread(producter).start();

        Customer customer = new Customer(goods);
        new Thread(customer).start();
        /**
         * 谁先抢到线程？消费者？还是生产者?
         * //如果是消费者抢到执行权，不用说！！！直接打印消费者购买了东风
         * //如果生产者抢到执行权，生产者wait,那就意味着必须去执行消费者线程
         * 消费者购买了:东风41,价格为:67.8
         * //此时isProduct是true 需要时生产
         * 还是消费者和生产者抢这个执行权
         * //假如生产者抢到了  就会打印生产者生产了啥。
         * //假如消费者抢到了执行权，消费者进入倒阻塞状态！！！
         * //消费者进入倒阻塞，那么肯定生产者得执行了
         * 生产者生产了:奥迪A8,价格为:78.9
         *
         * 还是两个线程抢这个执行权
         * 消费者购买了:奥迪A8,价格为:78.9
         * 生产者生产了:五菱大光,价格为:12.9
         * 消费者购买了:五菱大光,价格为:12.9
         * 生产者生产了:奥迪A8,价格为:78.9
         * 消费者购买了:奥迪A8,价格为:78.9
         * 生产者生产了:五菱大光,价格为:12.9
         * 消费者购买了:五菱大光,价格为:12.9
         * 生产者生产了:奥迪A8,价格为:78.9
         * 消费者购买了:奥迪A8,价格为:78.9
         */
    }
}

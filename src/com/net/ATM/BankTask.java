package com.net.ATM;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BankTask implements Runnable{
    private Socket s;//怎么关闭   . close  ？  try(能close的资源定义代码)  ... resource
    private Bank b;
    private boolean flag;

    public BankTask(Socket s, Bank b) {
        this.s = s;
        this.b = b;
    }

    @Override
    public void run() {
        String command=null;
        int id=0;
        double money=0.0;
        BankAccount ba=null;
        try(
                Socket socket=this.s;//重新定义了一个socket变量指向s，引用
                Scanner reader=new Scanner(s.getInputStream());
                PrintWriter pw=new PrintWriter(s.getOutputStream());
                ){
            //这里与客户端的一个socket中可能有多次交互，所以要用来循环，这是一个耗时的操作(线程循环操作)
            while (!Thread.currentThread().isInterrupted()  &&  !flag){
                //异常处理：1.如果客户端只连接了服务器，但没有传命令过来
                //解压缩
                //解密
                //处理
                if(!reader.hasNext()){
                    System.out.println("atm客户端"+socket.getRemoteSocketAddress()+"掉线了...");
                    break;
                }
                //现在接收  协议的第一部分（后面是一个空白符）
                command= reader.next();//按空白符来分隔数据进行读取
                if("DEPOSITE".equalsIgnoreCase(command)){
                    id=reader.nextInt();
                    money= reader.nextDouble();
                    ba=b.deposite(id,money);
                }else if("WITHDRAW".equalsIgnoreCase(command)){
                    id=reader.nextInt();
                    money= reader.nextDouble();
                    ba=b.withdraw(id,money);
                }else if("BALANCE".equalsIgnoreCase(command)){
                    id=reader.nextInt();
                    ba=b.search(id);
                }else if("QUIT".equalsIgnoreCase(command)){
                    System.out.println("客户端主动断开与服务器的连接。。。。。。"+s.getRemoteSocketAddress());
                    break;
                }else {
                    System.out.println("客户端的请求存在风险，请求的命令不支持。。。。");
                    pw.println("客户端的请求存在风险，请求的命令不支持。。。。断开与此客户端的联系");
                    pw.flush();
                    break;
                }
                pw.println(ba.getId()+" "+ba.getBalance());
                pw.flush();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("服务器断开与客户端："+this.s.getRemoteSocketAddress()+"的连接");
    }
}

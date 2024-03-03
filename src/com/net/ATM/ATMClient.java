package com.net.ATM;

import javax.xml.stream.events.Comment;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ATMClient {
    public static void main(String[] args) {
        boolean flag=true;
        String host="localhost";
        int port=12000;
        if(args!=null&&args.length==2){
            host=args[0];
            port=Integer.parseInt(args[1]);
        }
        Scanner keyboard=new Scanner(System.in);
        //文本型协议（流选用字符流）
        //PrintWriter.println()        Scanner.nextLine()   支持按行操作
        //加入加解密  密钥
        //压缩
        try(Socket s=new Socket(host,port);//字节流
            PrintWriter pw=new PrintWriter(s.getOutputStream());//输出流用完就要flush
            Scanner sc=new Scanner(s.getInputStream())

        ){
            System.out.println("连接ATM服务器"+s.getRemoteSocketAddress()+"成功");
            do{
                System.out.println("====================中国银行伟哥欢迎您======================");
                System.out.println("1.存");
                System.out.println("2.取");
                System.out.println("3.查询");
                System.out.println("4.退出");
                System.out.println("请输入您的选项：");
                String comment=keyboard.nextLine();
                String protocol="";
                if("1".equalsIgnoreCase(comment)){
                    //提示用户输入金额，账号
                    protocol="DEPOSITE 1 100";
                    pw.println(protocol);
                }else if("2".equalsIgnoreCase(comment)){
                    //提示用户输入金额，账号
                    protocol="WITHDRAW 1 100";
                    pw.println(protocol);
                }else if("3".equalsIgnoreCase(comment)){
                    //提示用户输入账号
                    protocol="BALANCE 1";
                    pw.println(protocol);
                }else if("4".equalsIgnoreCase(comment)){
                    protocol="QUIT";
                    pw.println(protocol);
                    break;
                }else {
                    System.out.println("没有这个选项");
                    continue;
                }
                pw.flush();
                //接收服务器的响应-》scanner按行读取响应
                String response=sc.nextLine();
                System.out.println("服务端的响应为："+response);
            }while (flag);
            System.out.println("ATM机客户端正在退出.....");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

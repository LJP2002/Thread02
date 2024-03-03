package com.net.ATM;

import com.sun.source.doctree.ThrowsTree;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<BankAccount> accounts=new ArrayList<>();

    public Bank(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    public Bank() {
        //初始化一些账号信息
        for(int i=0;i<10;i++){
            accounts.add(new BankAccount(i+1,10));
        }
    }
    //根据id查询余额(改账号)
    public BankAccount search(int id) throws Exception{
        for(BankAccount ba:accounts){
            if(ba.getId()==id){
                return ba;
            }
        }
        //return null;   ->调用方要用if
        throw new Exception("查无此用户");//调用方  try...catch
    }

    //存款
    public BankAccount deposite(int id,double money) throws Exception{
        BankAccount ba=search(id);
        synchronized (ba){
            ba.setBalance(ba.getBalance()+money);
        }
        return ba;
    }
    //取款
    public BankAccount withdraw(int id,double money) throws Exception{
        BankAccount ba=search(id);
        synchronized (ba){
            ba.setBalance(ba.getBalance()-money);
        }
        return ba;
    }
}

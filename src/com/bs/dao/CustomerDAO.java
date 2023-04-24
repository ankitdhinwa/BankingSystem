

package com.bs.dao;

import java.util.List;

//import java.util.List;

import com.bs.bean.CustomerBean;
import com.bs.bean.TransactionBean;
import com.bs.exception.CustomerException;

public interface CustomerDAO {

    public CustomerBean LoginCustomer(String username, String password, int accountno)throws CustomerException;

    public float viewBalance(int cACno) throws CustomerException;

    public int Deposit(int cACno, float amount) throws CustomerException;

    public float Withdraw(int cACno, float amount) throws CustomerException;

    public int Transfer(int cACno, float amount, int cACno2) throws CustomerException;

    public List<TransactionBean> viewTransaction(int cACno) throws CustomerException;

}

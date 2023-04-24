package com.bs.dao;

import com.bs.bean.AccountantBean;
import com.bs.bean.CustomerBean;
import com.bs.exception.AccountException;
import com.bs.exception.AccountantException;
import com.bs.exception.CustomerException;

import java.sql.SQLException;

public interface AccountantDAO {

    public AccountantBean LoginAccountant(String username, String password)throws AccountantException;

    public int addCustomer(String cname,String cmail, String cpass, String cmob, String cadd) throws CustomerException;

    public int viewAccountNo(int cid,String name,String cmail,String cmob) throws CustomerException, SQLException;

    public String addAccount(float cbal,int cid) throws AccountException;

    public String updateCustomerAddress(int cACno,String cadd) throws CustomerException;

    public String updateCustomerMobile(int cACno,String cmob) throws CustomerException;

    public String updateCustomerEmail(int cACno,String cmail) throws CustomerException;

    public  CustomerBean viewCustomer(String cACno) throws CustomerException;

    public int getCustomer(String cmail,String cmob) throws CustomerException;

    public CustomerBean viewAllCustomer() throws CustomerException;

    public String deleteAccount(int cACno) throws CustomerException;


}

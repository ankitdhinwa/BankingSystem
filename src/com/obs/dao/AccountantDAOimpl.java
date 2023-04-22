package com.obs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.obs.bean.AccountantBean;
import com.obs.bean.CustomerBean;
import com.obs.exception.AccountException;
import com.obs.exception.AccountantException;
import com.obs.exception.CustomerException;
import com.obs.utility.DBUtil;

public class AccountantDAOimpl implements AccountantDAO{

    @Override
    public AccountantBean LoginAccountant(String username, String password) throws AccountantException {

        AccountantBean acc = null;

        try(Connection conn = DBUtil.provideConnection()) {

            PreparedStatement ps= conn.prepareStatement("select * from InfoAccountant where email = ? AND epass = ?");

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs= ps.executeQuery();


            if(rs.next()) {

                String n=rs.getString("ename");

                String e= rs.getString("email");

                String p= rs.getString("epass");


                acc=new AccountantBean(n,e,p);


            }else
                throw new AccountantException("Invalid Username/Password....Try Again! ");


        } catch (SQLException e) {
            throw new AccountantException(e.getMessage());
        }


        return acc;

    }


//######################################################################################


    @Override
    public int addCustomer(String cname, String cmail, String cpass, String cmob,
                           String cadd) throws CustomerException {

        int cid=-1;
        int cACno=-1;

        try(Connection conn= DBUtil.provideConnection()) {

            PreparedStatement ps=conn.prepareStatement("insert into InfoCustomer(cname,cmail,cpass,cmob,cadd) values(?,?,?,?,?)");
            ps.setString(1,cname);
            ps.setString(2,cmail);
            ps.setString(3,cpass);
            ps.setString(4,cmob);
            ps.setString(5,cadd);

            int x=ps.executeUpdate();

            if(x > 0) {
                PreparedStatement ps2=conn.prepareStatement("select cid from InfoCustomer where cmail=? AND cmob=?");
                ps2.setString(1, cmail);
                ps2.setString(2, cmob);

                ResultSet rs=ps2.executeQuery();

                if(rs.next()) {
                    cid=rs.getInt("cid");
                }


            }else
                System.out.println("Inserted data is not correct");

        }catch(SQLException e) {

            e.printStackTrace();

        }

        return cid;
    }

    @Override
    public int viewAccountNo(int cid,String cname, String cmail, String cmob) throws CustomerException {

        int cACno=-1;

        try(Connection conn= DBUtil.provideConnection())
        {

            PreparedStatement ps3 = conn.prepareStatement("select cACno from InfoCustomer i inner join Account a on a.cid=i.cid where cname=? and cmail=? and  cmob=?");
            ps3.setString(1,cname);
            ps3.setString(2,cmail);
            ps3.setString(3,cmob);


            ResultSet rs1 = ps3.executeQuery();
            if(rs1.next()){
                cACno = rs1.getInt("cACno");
            }
            else
            {
                throw new CustomerException("Invalid Input!!");
            }


        }catch(SQLException e) {

            throw new CustomerException(e.getMessage());

        }

        return cACno;
    }


//##################################################################################


    @Override
    public String addAccount(float cbal, int cid) throws AccountException
    {

        String message=null;

        try(Connection conn= DBUtil.provideConnection()) {

            PreparedStatement ps=conn.prepareStatement("insert into Account(cbal,cid) values(?,?)");

            ps.setFloat(1,cbal);
            ps.setInt(2,cid);


            int x=ps.executeUpdate();

            if(x > 0) {
                System.out.println("Account added successfully..!");
            }else
                System.out.println("Inserted data is not correct");

        }catch(SQLException e) {

            e.printStackTrace();
            message=e.getMessage();
        }


        return message;
    }


//######################################################################################

    @Override
    public String updateCustomerMobile(int cACno, String cmob) throws CustomerException
    {
        String message=null;
        try(Connection conn = DBUtil.provideConnection())
        {
            PreparedStatement ps=conn.prepareStatement(" update infocustomer i inner join account a on i.cid=a.cid AND a.cACno=? set i.cmob=?;");

            ps.setInt(1, cACno);
            ps.setString(2,cmob);

            int x=ps.executeUpdate();

            if(x > 0) {
                System.out.println("Mobile Number updated successfully..!");
                System.out.println("-------------------------------");
            }else {
                System.out.println("Updation failed....Account Not Found");
                System.out.println("--------------------------------------");
            }
        }catch(SQLException e) {

            e.printStackTrace();
            message=e.getMessage();
        }
        return message;
    }

    @Override
    public String updateCustomerAddress(int cACno,String cadd) throws CustomerException
    {
        // TODO Auto-generated method stub
        String message=null;
        try(Connection conn= DBUtil.provideConnection()) {



                PreparedStatement ps=conn.prepareStatement(" update infocustomer i inner join account a on i.cid=a.cid AND a.cACno=? set i.cadd=?;");

                ps.setInt(1, cACno);
                ps.setString(2,cadd);

                int x=ps.executeUpdate();

                if(x > 0) {
                    System.out.println("Address updated successfully..!");
                    System.out.println("-------------------------------");
                }else {
                    System.out.println("Updation failed....Account Not Found");
                    System.out.println("--------------------------------------");
                }

        }catch(SQLException e) {

            e.printStackTrace();
            message=e.getMessage();
        }

        return message;
    }

    public String updateCustomerEmail(int cACno, String cmail) throws CustomerException
    {
        String message=null;
        try(Connection conn = DBUtil.provideConnection())
        {
            PreparedStatement ps=conn.prepareStatement(" update infocustomer i inner join account a on i.cid=a.cid AND a.cACno=? set i.cmail=?;");

            ps.setInt(1, cACno);
            ps.setString(2,cmail);

            int x=ps.executeUpdate();

            if(x > 0) {
                System.out.println("Email updated successfully..!");
                System.out.println("-------------------------------");
            }else {
                System.out.println("Updation failed....Account Not Found");
                System.out.println("--------------------------------------");
            }
        }catch(SQLException e) {

            e.printStackTrace();
            message=e.getMessage();
        }
        return message;
    }

//	##################################################################################



    @Override
    public String deleteAccount(int cACno) throws CustomerException {

        String message=null;
        try(Connection conn= DBUtil.provideConnection()) {



            PreparedStatement ps=conn.prepareStatement("delete i from infocustomer i inner join account a on i.cid=a.cid where a.cACno=?;");

            ps.setInt(1, cACno);


            int x=ps.executeUpdate();

            if(x > 0) {
                System.out.println("Account deleted successfully..!");
                System.out.println("-------------------------------");
            }else {
                System.out.println("Deletion failed...Account Not Found");
                System.out.println("------------------------------------");
            }
        }catch(SQLException e) {

            e.printStackTrace();
            message=e.getMessage();
        }

        return message;

    }


//	###################################################################################



    @Override
    public CustomerBean viewCustomer(String cACno) throws CustomerException {
        // TODO Auto-generated method stub
        CustomerBean cb = null;

        try(Connection conn = DBUtil.provideConnection()) {


            PreparedStatement ps= conn.prepareStatement("select * from InfoCustomer i inner join Account a on a.cid=i.cid where cACno = ?");

            ps.setString(1, cACno);


            ResultSet rs= ps.executeQuery();


            if(rs.next()) {

                int a=rs.getInt("cACno");

                String n=rs.getString("cname");

                int b=rs.getInt("cbal");

                String e= rs.getString("cmail");

                String p= rs.getString("cpass");

                String m= rs.getString("cmob");

                String ad= rs.getString("cadd");

                cb=new CustomerBean(a,n,b,e,p,m,ad);

            }else
                throw new CustomerException("Invalid Account No ");




        } catch (SQLException e) {
            throw new CustomerException(e.getMessage());
        }




        return cb;
    }






//	################################################################################


    @Override
    public CustomerBean viewAllCustomer() throws CustomerException {

        CustomerBean cb = null;

        try(Connection conn = DBUtil.provideConnection()) {


            PreparedStatement ps= conn.prepareStatement("select * from InfoCustomer i inner join Account a on a.cid=i.cid");


            ResultSet rs= ps.executeQuery();


            while(rs.next()) {

                int a=rs.getInt("cACno");

                String n=rs.getString("cname");

                float b=rs.getFloat("cbal");

                String e= rs.getString("cmail");

                String p= rs.getString("cpass");

                String m= rs.getString("cmob");

                String ad= rs.getString("cadd");

                System.out.println("******************************");
                System.out.println("Account No: " + a);
                System.out.println("Name: " + n);
                System.out.println("Balance: " + b);
                System.out.println("Email: " + e);
                System.out.println("Password: " + p);
                System.out.println("Mobile: " + m);
                System.out.println("Address: " + ad);
                System.out.println("******************************");

                cb=new CustomerBean(a,n,b,e,p,m,ad);

            }


        } catch (SQLException e) {
            throw new CustomerException("Invalid Account No.");
        }


        return cb;
    }

    //#######################################################################################



    @Override
    public int getCustomer(String cmail, String cmob) throws CustomerException {

        int cid=-1;

        try(Connection conn = DBUtil.provideConnection()){

            PreparedStatement ps2=conn.prepareStatement("select cid from InfoCustomer where cmail=? AND cmob=?");
            ps2.setString(1, cmail);
            ps2.setString(2, cmob);

            ResultSet rs=ps2.executeQuery();

            if(rs.next()) {
                cid=rs.getInt("cid");
            }


        }catch(SQLException e) {
            throw new CustomerException("Invalid Account No.");
        }
        return cid;

    }







}

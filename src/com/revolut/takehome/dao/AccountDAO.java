package com.revolut.takehome.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.*;
import com.revolut.takehome.service.*;
public class AccountDAO {
	private String currencyStmnt="select currency from accounts where id=?";
	private String debitAccount="update accounts set funds=funds-? where id=?";
	private String creditAccount="update accounts set funds=funds+? where id=?";
	private String sufficientFunds="select funds from accounts where id=?"; 
	DataSource ds;
	public String getCurrency(Long id) {
		
		return "";		
	}
	public TransferResult transferMoney(Long source,Long dest,BigDecimal amount) {
		TransferResult result;
		try(Connection conn=ds.getConnection()){
			try(
				PreparedStatement debit=conn.prepareStatement(debitAccount);
					PreparedStatement credit=conn.prepareStatement(creditAccount);
					PreparedStatement fundCheck=conn.prepareStatement(sufficientFunds))
				{
					conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
					conn.setAutoCommit(false);
					try(ResultSet rs=fundCheck.executeQuery()){
						if(rs.next()) {
							BigDecimal availableFunds=rs.getBigDecimal(1);
							if(availableFunds.compareTo(amount)<0) {
								result=new TransferResult(false,"Insufficient funds");
							}else {
								if(debit.executeUpdate()==1) {
									if(credit.executeUpdate()==1) {
										conn.commit();
										result=new TransferResult(true,"Success");
									}else {
										conn.rollback();
										result=new TransferResult(false,"Rollback on failed credit");
									}
								}else {
									conn.rollback();
									result=new TransferResult(false,"Rollback on failed debit");
								}
							}							
						}else {
							result=new TransferResult(false,"Couldn't find source funds");
							conn.rollback();
						}
						return result;
					}
				}
			catch(Exception e) {
				conn.rollback();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new TransferResult(false,"Unknown failure in transfer");
	}

}

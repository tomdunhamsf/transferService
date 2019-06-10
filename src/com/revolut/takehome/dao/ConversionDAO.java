package com.revolut.takehome.dao;
import java.math.BigDecimal;
import java.sql.*;

import javax.sql.DataSource;
public class ConversionDAO {
	private String prepStatement="select rate from exchange_rate where source=? and destination=?";
	DataSource ds;
	public BigDecimal getConversion(String sourceCurrency,String destCurrency) throws Exception{
		BigDecimal multi=new BigDecimal(1.000000000000);
		
		try(Connection conn=ds.getConnection();
			PreparedStatement stmnt=conn.prepareStatement(prepStatement);)
		{
			stmnt.setString(1, sourceCurrency);
			stmnt.setString(2, destCurrency);
			try(ResultSet rs=stmnt.executeQuery();){
				if(rs.next()) {
					multi=rs.getBigDecimal(1);
				}else {
					throw new Exception("No currency conversion result");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return multi;
	}

}

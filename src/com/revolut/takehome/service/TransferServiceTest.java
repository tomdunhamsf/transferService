package com.revolut.takehome.service;


import java.math.BigDecimal;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.revolut.takehome.dao.*;
import com.revolut.takehome.request.TransferRequest;
import mockit.integration.junit4.*;
import mockit.*;
@RunWith(JMockit.class)
public class TransferServiceTest {
	public TransferServiceTest() {
		
	}
	@Mocked ConversionDAO currDao;
	@Mocked AccountDAO acctDao;
	private TransferService service=new TransferService();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNeg() {
		TransferRequest req=new TransferRequest("USD",new BigDecimal(-300.00),1l,2l);
		TransferResult result=service.processTransfer(req);
		assertFalse(result.getSuccess());
	}
	
	@Test 
	public void testInvalidDestCurrency() {
		new Expectations() {{
			acctDao.getCurrency(2l);
			result="GBP";
		}};
		TransferRequest req=new TransferRequest("USD",new BigDecimal(300.00),1l,2l);
		TransferResult result=service.processTransfer(req);
		assertFalse(result.getSuccess());
	}
	
	@Test
	public void testCurrencyConverted(){
		try {
		new Expectations() {{
			acctDao.getCurrency(2l);
			result="GBP";
			acctDao.getCurrency(1l);
			result="USD";
			currDao.getConversion("USD", "GBP");
			result=new BigDecimal("0.800");
			acctDao.transferMoney(1l, 2l, new BigDecimal("8.000"));
			result=new TransferResult(true,"success");
		}};
		TransferRequest req= new TransferRequest("GBP",new BigDecimal(10.00),1l,2l);
		TransferResult result=service.processTransfer(req);
	}catch(Exception e) {
		e.printStackTrace();
	}
	}

}

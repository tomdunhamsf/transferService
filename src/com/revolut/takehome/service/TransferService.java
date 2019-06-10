package com.revolut.takehome.service;

import java.math.BigDecimal;

import com.revolut.takehome.dao.AccountDAO;
import com.revolut.takehome.dao.ConversionDAO;
import com.revolut.takehome.request.TransferRequest;

public class TransferService {
	ConversionDAO conDao=new ConversionDAO();
	AccountDAO accountDao=new AccountDAO();
	public TransferResult processTransfer(TransferRequest req) {
		if(req.getAmount().compareTo(new BigDecimal(0.0))==-1) {
			TransferResult result=new TransferResult(false,"Negative amounts cannot be transfered.");
			return result;
		}
		String destCurrency=accountDao.getCurrency(req.getDestAccountId());
		boolean validDest=destCurrency.equals(req.getCurrencyCode());
		if(!validDest) {
			TransferResult result=new TransferResult(validDest,"The destination account is not denominated in "+req.getCurrencyCode());
			return result;
		}
		String originCurrency=accountDao.getCurrency(req.getSourceAccountId());
		BigDecimal transferAmount=req.getAmount();
		if(!originCurrency.equals(req.getCurrencyCode())) {
			BigDecimal multi=new BigDecimal("1.0000");
			try {
				multi=conDao.getConversion(originCurrency, destCurrency);
			}catch(Exception e) {
				TransferResult result=new TransferResult(false,"Currency conversion required, but a failure occurred");
				return result;
			}
			
			transferAmount=transferAmount.multiply(multi);
		}
		TransferResult result=accountDao.transferMoney(req.getSourceAccountId(), req.getDestAccountId(), transferAmount);
		return result;
	}

}

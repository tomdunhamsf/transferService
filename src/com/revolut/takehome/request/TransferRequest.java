package com.revolut.takehome.request;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransferRequest {

	@JsonProperty(required = true)
	private String currencyCode;

	@JsonProperty(required = true)
	private BigDecimal amount;

	@JsonProperty(required = true)
	private Long sourceAccdountId;

	@JsonProperty(required = true)
	private Long destAccountId;

	public TransferRequest() {
	}

	public TransferRequest(String currencyCode, BigDecimal amount, Long fromAccountId, Long toAccountId) {
		this.currencyCode = currencyCode;
		this.amount = amount;
		this.sourceAccdountId = fromAccountId;
		this.destAccountId = toAccountId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Long getSourceAccountId() {
		return sourceAccdountId;
	}

	public Long getDestAccountId() {
		return destAccountId;
	}


}

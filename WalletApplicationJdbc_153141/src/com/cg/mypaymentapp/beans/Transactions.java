package com.cg.mypaymentapp.beans;

import java.math.BigDecimal;
import java.util.Date;

public class Transactions {
	private String mobileno;
	private BigDecimal amount;
	private String transactionType;
	private Date dateofTransaction;

	@Override
	public String toString() {
		return "Transactions [mobileno=" + mobileno + ", amount=" + amount + ", transactionType=" + transactionType
				+ ", dateofTransaction=" + dateofTransaction + "]";
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getDateofTransaction() {
		return dateofTransaction;
	}

	public void setDateofTransaction(Date dateofTransaction) {
		this.dateofTransaction = dateofTransaction;
	}

	public Transactions(String mobileno2, BigDecimal amount, String transactionType, Date dateofTransaction) {
		super();
		this.mobileno = mobileno2;
		this.amount = amount;
		this.transactionType = transactionType;
		this.dateofTransaction = dateofTransaction;
	}

	public Transactions() {
		// TODO Auto-generated constructor stub
	}
}

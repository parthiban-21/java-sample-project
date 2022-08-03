package com.chainsys.bank;

public class Bank {
	private int accountNo;
	private String dod;
	private float balance;
	
	public int getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}
	public String getDod() {
		return dod;
	}
	public void setDod(String dod) {
		this.dod = dod;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Bank [accountNo=" + accountNo + ", doj=" + dod + ", balance=" + balance + "]";
	}
}

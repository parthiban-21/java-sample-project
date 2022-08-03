package com.chainsys.bank;

import java.sql.SQLException;

public interface BankInterface {
	
	public void addCustomer(Bank bank,Customer customer) throws SQLException, ClassNotFoundException;
	public void removeCustomer(Bank bank,Customer customer) throws SQLException, ClassNotFoundException;
	public void displayInfo(Bank bank,Customer customer) throws SQLException, ClassNotFoundException;
	public void deposite(Bank bank,Customer customer) throws SQLException, ClassNotFoundException;
}

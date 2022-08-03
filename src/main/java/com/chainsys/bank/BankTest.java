package com.chainsys.bank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Bank bk = new Bank();
		Customer cust = new Customer();
		BankImpl imp = new BankImpl();
		
		imp.addCustomer(bk, cust);
		//imp.deposite(bk, cust);
		//imp.displayInfo(bk, cust);
		//imp.removeCustomer(bk, cust);
	}

}

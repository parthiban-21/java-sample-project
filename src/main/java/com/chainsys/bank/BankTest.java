package com.chainsys.bank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner ip = new Scanner(System.in);
		Bank bk = new Bank();
		Customer cust = new Customer();
		BankImpl imp = new BankImpl();

		System.out.println("Banking Operations:\n1. Create Account\n2. Deposite\n3. Delete Account\n4. Display\nOther to Exit.");
		System.out.print("Choose the Above Operation: ");
		int choose = ip.nextInt();
		switch (choose){
		case 1:
			imp.addCustomer(bk, cust);
		case 2:
			imp.deposite(bk, cust);
		case 3:
			imp.removeCustomer(bk, cust);
		case 4:
			imp.displayInfo(bk, cust);
		default:

		}
	}
}

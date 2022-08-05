package com.chainsys.bank;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.chainsys.ConnectionClass;

public class BankImpl implements BankInterface {
	Scanner ip = new Scanner(System.in);
	BankValidation val = new BankValidation();

	public void addCustomer(Bank bank,Customer customer) throws SQLException, ClassNotFoundException {

		//Get Name From User
		System.out.print("Enter the Name: ");
		String name = ip.next();
		name = val.checkName(name);
		customer.setName(name);
		
		//Get Date of Birth from User
		System.out.print("Enter the DOB (yyyy-MM-dd): ");
		String dob = ip.next();
		customer.setDob(dob);
		LocalDate dateOfBirth = LocalDate.parse(customer.getDob());
		Date date = Date.valueOf(dateOfBirth);

		//Get Date of Birth from User
		System.out.print("Enter the Address: ");
		String address = ip.next();
		address = val.checkName(address);
		customer.setAddress(address);
		try 
		{
			Connection c = ConnectionClass.getConnection();
			String inst="INSERT into CUSTOMER(acc_no,name,dob,age,address)values(ACCNOSEQ.nextval,?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(inst);
			ps.setString(1, customer.getName());
			ps.setDate(2, date);
			ps.setInt(3, ageCalculation(customer.getDob()));
			ps.setString(4, customer.getAddress());
			ps.executeUpdate();
			System.out.println("Created Successfully");
			customer.setAccountNo(getAccountNo(name, date));
			bank.setAccountNo(customer.getAccountNo());
			firstDeposite(bank, customer);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}		
	}

	public void removeCustomer(Bank bank,Customer customer) throws SQLException, ClassNotFoundException {
		System.out.print("Enter the Account No: ");
		int accountNo = ip.nextInt();
		bank.setAccountNo(accountNo);

		if(val.checkAccNo(accountNo))
		{
			try 
			{
				Connection c = ConnectionClass.getConnection();
				String inst="delete from bank where acc_no = (?)";
				PreparedStatement ps = c.prepareStatement(inst);
				ps.setInt(1, bank.getAccountNo());
				ps.executeUpdate();
				System.out.println("Account Closed Successfully");

				String del="delete from customer where acc_no = (?)";
				PreparedStatement ps1 = c.prepareStatement(del);
				ps1.setInt(1, bank.getAccountNo());
				ps1.executeUpdate();
				System.out.println("Removed Customer Successfully");
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}
		}

	}

	//Displays all records
	public void displayInfo(Bank bank,Customer customer) throws SQLException, ClassNotFoundException {
		try 
		{
			Connection c = ConnectionClass.getConnection(); //Gets Connection
			//Statement to Insert Values
			String print="select customer.acc_no,customer.name,customer.dob,customer.age,customer.address,bank.balance,bank.dod from customer Inner Join bank on customer.acc_no = bank.acc_no";
			PreparedStatement ps = c.prepareStatement(print);
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				System.out.println("Account No: "+rs.getInt(1));
				System.out.println("Name: "+rs.getString(2));
				System.out.println("Date of Birth: "+rs.getDate(3));
				System.out.println("Age: "+rs.getInt(4));
				System.out.println("Address: "+rs.getString(5));
				System.out.println("Balance: "+rs.getFloat(6));
				System.out.println("Late Transaction Date: "+rs.getDate(7)+"\n");
			}
			rs.close();
			ps.close();
			c.close();
		}

		catch(Exception e) 
		{
			e.printStackTrace();
		}	

	}

	//Get Account No for New User
	public int getAccountNo(String name,Date date)
	{
		int i=0;
		try 
		{
			Connection c = ConnectionClass.getConnection(); //Gets Connection
			//Statement to Insert Values
			String print="select acc_no from customer where name = (?) and dob = (?)";
			PreparedStatement ps = c.prepareStatement(print);
			ps.setString(1, name);
			ps.setDate(2, date);

			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				i = rs.getInt(1);
			}
		}

		catch(Exception e) 
		{
			e.printStackTrace();
		}	
		return i;
	}

	//Get Existing Balance
	public int getExistingBalance(int accountno)
	{
		int i=0;
		try 
		{
			Connection c = ConnectionClass.getConnection(); //Gets Connection
			//Statement to Insert Values
			String print="select balance from bank WHERE acc_no = (?)";
			PreparedStatement ps = c.prepareStatement(print);
			ps.setInt(1, accountno);

			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				i = rs.getInt(1);
			}
		}

		catch(Exception e) 
		{
			e.printStackTrace();
		}	
		return i;
	}

	//Age Calculation
	public int ageCalculation(String date) 
	{
		LocalDate birthday = LocalDate.parse(date);
		LocalDate today = LocalDate.now();

		int age = today.compareTo(birthday);
		return age;
	}

	public void firstDeposite(Bank bank, Customer customer) throws SQLException, ClassNotFoundException {

		if(val.checkAccNo(customer.getAccountNo()))
		{
			//Get Amount to be Deposited
			System.out.print("Enter Your First Deposite Amount :");
			float deposite = ip.nextFloat();
			bank.setBalance(deposite);


			//Date of Deposit
			LocalDate today = LocalDate.now();
			Date date = Date.valueOf(today);
			try 
			{
				Connection c = ConnectionClass.getConnection();
				String inst="INSERT into Bank(acc_no,dod,balance)values(?,?,?)";
				PreparedStatement ps = c.prepareStatement(inst);
				ps.setInt(1, bank.getAccountNo());
				ps.setDate(2, date);
				ps.setFloat(3, bank.getBalance());
				ps.executeUpdate();
				System.out.println("Added Successfully");
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void deposite(Bank bank, Customer customer) throws SQLException, ClassNotFoundException {
		//Get Account No
		System.out.print("Enter the Account No: ");
		int accountNo = ip.nextInt();
		bank.setAccountNo(accountNo);

		if(val.checkAccNo(accountNo))
		{
			//Get Amount to be Deposited
			System.out.print("Enter the Amount to Deosite:");
			float deposite = ip.nextFloat();
			float newBalance = getExistingBalance(accountNo)+deposite;
			bank.setBalance(newBalance);

			//Date of Deposit
			LocalDate today = LocalDate.now();
			Date date = Date.valueOf(today);
			try 
			{
				Connection c = ConnectionClass.getConnection();
				String inst= "UPDATE bank SET balance = (?), dod = (?) WHERE acc_no = (?)";
				PreparedStatement ps = c.prepareStatement(inst);
				ps.setFloat(1, bank.getBalance());
				ps.setDate(2, date);
				ps.setInt(3, bank.getAccountNo());
				ps.executeUpdate();
				System.out.println("Added Successfully");
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}
		}

	}

}

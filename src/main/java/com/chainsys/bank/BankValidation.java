package com.chainsys.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chainsys.ConnectionClass;

public class BankValidation {
	Scanner ip = new Scanner(System.in);
	public boolean checkAccNo(int find) 
	{
		try 
		{
			Connection c = ConnectionClass.getConnection(); //Gets Connection
			//Statement to Insert Values
			String print="select * FROM customer where acc_no = (?)";
			PreparedStatement ps = c.prepareStatement(print);
			int i=0;
			while(i==0)
			{
				ps.setInt(1, find);
				int t = ps.executeUpdate();
				if(t==0)
				{
					System.out.print("Account No not Found...\nEnter Account No Again :");
					find = ip.nextInt();
				}
				else
				{
					++i;	
				}
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public String checkName(String name) {
		String userPattern = "^(?!.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.*[!@#&()–[{}]:;',?/*~$^+=<>]).{4,20}$";
		Pattern pattern = Pattern.compile(userPattern);
		Matcher match = pattern.matcher(name);
		if (match.matches())
			return name;
		else {
			int i=0;
			String rname="";
			while(i==0)
			{
				System.out.println("Invalied Input\nTry Again...");
				name = ip.next();
				match = pattern.matcher(name);
				if(match.matches()){
					++i;
					rname = name;
				}
				else {
					i=0;
				}
			}
			return rname;
		}
	}
}

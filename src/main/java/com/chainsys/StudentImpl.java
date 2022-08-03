package com.chainsys;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.*;

public class StudentImpl implements StudentInterface {
	Scanner ip = new Scanner(System.in);
	Student obj = new Student();

	//Insert New Row in the DataBase
	public void insertRow() throws SQLException, ClassNotFoundException {

		System.out.print("Enter the Name: ");
		String name = ip.next();
		obj.setName(name);

//		System.out.print("Enter the Roll No: ");
//		int rollNo = ip.nextInt();
//		obj.setRollNo(rollNo);

//		System.out.print("Enter the Age: ");
//		int age = ip.nextInt();
//		obj.setAge(age);
	
		System.out.print("Enter the DOB: ");
		String dob = ip.next();
		obj.setDob(dob);
		LocalDate dateOfBirth = LocalDate.parse(dob);
		Date date = Date.valueOf(dateOfBirth);

		try 
		{
			Connection c = ConnectionClass.getConnection(); //Gets Connection
			//Statement to Insert Values
			String insert="insert into student(name,rollNo,age,dob)values(?,rollNoSeq.nextval,?,?)";
			PreparedStatement ps = c.prepareStatement(insert);

			ps.setString(1, obj.getName());
			ps.setInt(2, ageCalculation(obj.getDob()));
			ps.setDate(3, date);
			int i = ps.executeUpdate();
			System.out.println(i+" Rows Inserted Newly");
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}		
	}

	public void deleteRow() throws SQLException, ClassNotFoundException {

		System.out.print("Enter the Roll No you want to Delete: ");
		int rollNo = ip.nextInt();

		try 
		{
			Connection c = ConnectionClass.getConnection(); //Gets Connection
			//Statement to Insert Values
			String delete="delete from student where rollNo = (?)";
			PreparedStatement ps = c.prepareStatement(delete);

			int i=0;
			while(i==0)
			{
				ps.setInt(1, rollNo);
				int t = ps.executeUpdate();
				if(t==0)
				{
					System.out.print("Roll No not Found...\nEnter Roll No Again :");
					rollNo = ip.nextInt();
				}
				else 
				{
					System.out.println("Roll No "+rollNo+" Deleted");
					++i;
				}
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}		
	}

	public void createTable() throws SQLException, ClassNotFoundException {
		try 
		{
			Connection c = ConnectionClass.getConnection(); //Gets Connection
			//Statement to Insert Values
			String create="create table studentNew (ID int not null ,Name varchar(20) not null, Location varchar(20) not null, primary key (ID))";
			PreparedStatement ps = c.prepareStatement(create);

			ps.executeUpdate();
			System.out.println("Table Created Succesfully.!");
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}	

	}

	public void createSequence() throws SQLException, ClassNotFoundException {
		try 
		{
			Connection c = ConnectionClass.getConnection(); //Gets Connection
			//Statement to Insert Values
			String sequence="CREATE SEQUENCE rollNoSeq start with 3001 increment by 1 minvalue 3001 maxvalue 4000 cycle";
			PreparedStatement ps = c.prepareStatement(sequence);

			ps.executeUpdate();
			System.out.println("Sequence Created Succesfully.!");
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}	

	}

	public void displayTable() throws SQLException, ClassNotFoundException {
		Student s = new Student();
		try 
		{
			Connection c = ConnectionClass.getConnection(); //Gets Connection
			//Statement to Insert Values
			String print="select * FROM student";
			PreparedStatement ps = c.prepareStatement(print);
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				String name = rs.getString(1);
				int rollNo = rs.getInt(2);
				int age = rs.getInt(3);

				s.setName(name);
				s.setRollNo(rollNo);
				s.setAge(age);
				System.out.println(s);
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

	public void displayInfo() throws SQLException, ClassNotFoundException {

		System.out.print("Enter the Roll No you want to Get Infomation: ");
		int find = ip.nextInt();
		try 
		{
			Connection c = ConnectionClass.getConnection(); //Gets Connection
			//Statement to Insert Values
			String print="select * FROM student where rollno = (?)";
			PreparedStatement ps = c.prepareStatement(print);
			int i=0;
			while(i==0)
			{
				ps.setInt(1, find);
				int t = ps.executeUpdate();
				if(t==0)
				{
					System.out.print("Roll No not Found...\nEnter Roll No Again :");
					find = ip.nextInt();
				}
				else
				{
					ResultSet rs = ps.executeQuery();
					while(rs.next())
					{
						String name = rs.getString(1);
						int rollNo = rs.getInt(2);
						int age = rs.getInt(3);

						System.out.println("Name: "+name+"\nRoll No: "+rollNo+"\nAge: "+age);
					}
					++i;
				}
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	public int ageCalculation(String date) 
	{
		LocalDate birthday = LocalDate.parse(date);
		LocalDate today = LocalDate.now();
		
		int age = today.compareTo(birthday);
		return age;
	}
}

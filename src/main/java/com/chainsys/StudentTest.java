package com.chainsys;

import java.sql.SQLException;
import java.util.Scanner;

public class StudentTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		
		StudentImpl imp=new StudentImpl();
		Student obj = new Student();
		
		imp.insertRow();
//		imp.deleteRow();
//		imp.createTable();
//		imp.displayTable();
//		imp.createSequence();
//		imp.displayInfo();
	}
}

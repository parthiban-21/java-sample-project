package com.chainsys;

import java.sql.SQLException;

public interface StudentInterface {
	public void insertRow() throws SQLException, ClassNotFoundException;
	public void deleteRow() throws SQLException, ClassNotFoundException;
	public void createTable() throws SQLException, ClassNotFoundException;
	public void createSequence() throws SQLException, ClassNotFoundException;
	public void displayTable() throws SQLException, ClassNotFoundException;
	public void displayInfo() throws SQLException, ClassNotFoundException;
	public int ageCalculation(String date);
}

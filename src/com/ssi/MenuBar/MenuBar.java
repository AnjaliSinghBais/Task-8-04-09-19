package com.ssi.MenuBar;

import java.sql.*;
import java.util.Scanner;

public class MenuBar {

	// MENU DRIVEN PROGRAM TO DO SOME OPERATIONS ON EMPLOYEE TABLE DATA
	static Connection con;

	static Scanner sc = new Scanner(System.in);

	private static Object PreparedStatement;

	private static java.sql.PreparedStatement ps;

	private static ResultSet rs;

	// INSERT NEW EMPLOYEE TO TABLE USING PROCEDURE
	public static void addEmployee() throws Exception {
		System.out.println("Enter id for Employee");
		int id = sc.nextInt();
		System.out.println("Enter name for Employee");
		String ename = sc.next();
		System.out.println("Enter Department for Employee");
		String dept = sc.next();
		System.out.println("Enter sal for Employee");
		int sal = sc.nextInt();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "anjali", "abc1234");
		System.out.println("x");
		CallableStatement cs = con.prepareCall("{call Insert_Into_Empl(?,?,?,?)}");
		cs.setInt(1, id);
		cs.setString(2, ename);
		cs.setString(3, dept);
		cs.setInt(4, sal);
		cs.execute();
		System.out.println("executed");

	}

	// SEARCH EMPLOYEE USING FUNCTION
	public static void search() throws Exception {
		System.out.println("Enter id for Employee");
		int id = sc.nextInt();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "anjali", "abc1234");
		CallableStatement cs = con.prepareCall("{?=call SearchEmp(?)}");
		cs.setInt(2, id);
		cs.registerOutParameter(1, java.sql.Types.VARCHAR);
		cs.execute();
		String output = cs.getString(1);
		System.out.println(output);

	}

	// SEARCH EMPLOYEE
	public static void searchEmp() throws Exception {
		System.out.println("Enter Eno of employee whom you want to search");
		int search = sc.nextInt();
		PreparedStatement ps = con.prepareStatement("select id,ename,dept,sal from emp where id=?");
		ps.setInt(1, search);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println("Employee no is " + rs.getInt(1));
			System.out.println("Employee name is " + rs.getString(2));
			System.out.println("Employee department is " + rs.getString(3));
			System.out.println("Employee salary is " + rs.getInt(4));

		}
	}

	// REMOVE SPECIFIC DATA FROM TABLE
	public static void removeEmp() throws Exception {
		System.out.println("Enter Eno of employee whom you want to delete");
		int del = sc.nextInt();
		PreparedStatement ps = con.prepareStatement("Delete from emp where id=?");
		ps.setInt(1, del);
		int x = ps.executeUpdate();
		if (x > 0)
			System.out.println("Employee Deleted");
		else
			System.out.println("Employee Not Found Check Id");
	}

	// CHANGE SALARY OF SPECIFIC EMPLOYEE
	public static void changeSal() throws Exception {
		System.out.println("Enter the id of the Employee to Change Sal");
		int id = sc.nextInt();
		System.out.println("Enter the New Salary for the Employee to Change Sal");
		int sal = sc.nextInt();
		PreparedStatement ps = con.prepareStatement("update emp set sal=? where id=?");
		ps.setInt(1, sal);
		ps.setInt(2, id);
		int x = ps.executeUpdate();
		if (x > 0)
			System.out.println("Salary Updated");
		else
			System.out.println("Not Updated");

	}

	// VIEW DEPARTMENT SPECIFIC DATA
	public static void viewDeptWise() throws Exception {
		System.out.println("Enter the of the Department Name");
		String dept = sc.next();
		PreparedStatement ps = con.prepareStatement("select * from emp where dept=?");
		ps.setString(1, dept);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.print("ENO :" + "" + rs.getInt(1));
			System.out.print(" Ename :" + rs.getString(2));
			System.out.print(" DEPT :" + rs.getString(3));
			System.out.println(" Salary :" + rs.getString(4));

		}

	}

	// VIEW ENTIRE DATA OF EMPLOYEE TABLE
	public static void select() throws Exception {
		System.out.println("DATA OF THE EMPLOYEES");
		PreparedStatement ps = con.prepareStatement("select * from EMP");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println("Empid-> " + rs.getInt(1) + "   EmpName->" + rs.getString(2) + "  dept->"
					+ rs.getString(3) + " salary->" + rs.getInt(4));

		}
	}

	// DELETE THE ENTIRE TABLE
	public static void clearData() throws SQLException {
		PreparedStatement ps = con.prepareStatement("DELETE table Emp");
		int x = ps.executeUpdate();
		if (x > 0)
			System.out.println("Table Deleted");
		else
			System.out.println("Table Not Deleted");

	}

	// MENU DRIVEN INPUTS FROM USER
	public static void sortedEmp() throws Exception {
		System.out.println("On What Basis You Want To Sort Employee List");
		System.out.println("1. Employee ID");
		System.out.println("2. Employee Name");
		System.out.println("3. Employee Department");
		System.out.println("4. Employee Salary");
		int x = sc.nextInt();
		System.out.println(x);

		if (x == 1) {
			PreparedStatement ps = con.prepareStatement("select * from emp Order by id");
			rs = ps.executeQuery();
		} else if (x == 2) {
			PreparedStatement ps = con.prepareStatement("select * from emp Order by ename");
			rs = ps.executeQuery();
		} else if (x == 3) {
			PreparedStatement ps = con.prepareStatement("select * from emp Order by dept");
			rs = ps.executeQuery();
		} else if (x == 4) {
			PreparedStatement ps = con.prepareStatement("select * from emp Order by sal");
			rs = ps.executeQuery();
		} else if (x > 4) {
			System.out.print("INVALID NUMBER");
		}

		while (rs.next()) {

			System.out.print("   ENO :" + rs.getInt(1));
			System.out.print("   Ename :" + rs.getString(2));
			System.out.print("   DEPT :" + rs.getString(3));
			System.out.println(" Salary :" + rs.getInt(4));

		}
		System.out.println("press 8 for main menu");
	}

	public static void main(String[] args) throws SQLException {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/college", "root", "root");

			System.out.println("Enter YOUR CHOICE");
			System.out.println("1.Add New Employee ");
			System.out.println("2.View All Employees ");
			System.out.println("3.Search For Employee");
			System.out.println("4.Remove Specific Employee ");
			System.out.println("5.Delete Whole Employee Data ");
			System.out.println("6.Change Salary Of Employee ");
			System.out.println("7.View Dept Wise ");
			System.out.println("8.viewSortedEmployee");
			System.out.println("9. EXIT");

			while (true) {
				int choice = sc.nextInt();

				switch (choice) {

				case 1:
					addEmployee();
					break;

				case 2:
					select();
					break;

				case 3:
					searchEmp();
					break;

				case 4:
					removeEmp();
					break;

				case 5:
					// clearData();
					break;

				case 6:
					changeSal();
					break;

				case 7:
					viewDeptWise();
					break;

				case 8:
					sortedEmp();
					break;
				case 9:
					System.exit(1);
					break;
				default:
					System.out.println("INVALID ENTRY");
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
			sc.close();
		}
	}
}

package com.lio.sc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractDAO {

	
	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			 connection = DriverManager.getConnection("jdbc:derby:SHOPPING_CART;create=true");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public boolean checkIfDBExists(String tableName){
		
		Connection connection = getConnection();
		
		Statement stmt;
		try {
			stmt = connection.createStatement();
			try{
			ResultSet rs = stmt.executeQuery("select * from "+tableName);
			if(rs != null){
				rs.close();
				stmt.close();
				connection.close();
				return true;
			}
			}
			catch(java.sql.SQLSyntaxErrorException tnf){
				System.out.println("Table would not have been created so far.");
				return false;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
		
		return false;
	}
	
	public void closeConnection(org.apache.derby.client.am.Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
}

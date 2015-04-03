package com.lio.sc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DerbyDataLoader {
	
	public void loadProductsFromFile(String path) throws Exception{

		
		File customerDF = new File(path);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(customerDF));
		
		BufferedReader bfReader = new BufferedReader(reader);
		Connection connection = getDBConnection();
		Statement stmt = connection.createStatement();
		String createString = "CREATE TABLE PRODUCTS (PRODUCTID INTEGER NOT NULL" +
				", PRODUCTNAME VARCHAR(50) NOT NULL" +
				", SUPPLIERID INTEGER " +
				", CATEGORYID INTEGER" +
				", UNIT VARCHAR(50) " +
				", PRICE DOUBLE)" ;
				//", COUNTRY VARCHAR(50))";
		stmt.executeUpdate(createString);
		PreparedStatement pstmt = connection.prepareStatement("insert into PRODUCTS values (?,?,?,?,?,?)");
		
		int i=0; //ignore first Line
		while(bfReader.ready()){
			if(i==0){
				i++;
				bfReader.readLine();
				continue;
			}
			String[] columns = null;
			try{
				 columns = bfReader.readLine().split("\t");
				pstmt.setLong(1,Long.parseLong(columns[0]));
				pstmt.setString(2, columns[1]);
				pstmt.setLong(3, (columns[2] != null && columns[2] != "")?Long.parseLong(columns[2]):0);
				pstmt.setLong(4, (columns[3] != null && columns[3] != "")?Long.parseLong(columns[3]):0);
				pstmt.setString(5, (columns[4] != null && columns[4] != "")?columns[4]:"");
				pstmt.setDouble(6, (columns[5] != null && columns[5] != "")?Double.parseDouble(columns[5]):0);
				pstmt.addBatch();
			}
			catch(java.lang.ArrayIndexOutOfBoundsException exe){
				System.out.println("Ignoring: "+columns);
			}
		}
		System.out.println("Insert COmplete");
		pstmt.executeBatch();
		stmt.close();
		pstmt.close();
		connection.close();
		
	
	}
	
	public void  loadCustomersFromFile(String path) throws Exception{
		
		File customerDF = new File(path);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(customerDF));
		
		BufferedReader bfReader = new BufferedReader(reader);
		Connection connection = getDBConnection();
		Statement stmt = connection.createStatement();
		String createString = "CREATE TABLE Customers (CUSTOMERID VARCHAR(32) NOT NULL" +
				", CUSTOMERNAME VARCHAR(50) NOT NULL" +
				", CONTACTNAME VARCHAR(50) " +
				", ADDRESS VARCHAR(50)" +
				", CITY VARCHAR(50) " +
				", POSTALCODE VARCHAR(50)" +
				", COUNTRY VARCHAR(50))";
		stmt.executeUpdate(createString);
		PreparedStatement pstmt = connection.prepareStatement("insert into Customers values (?,?,?,?,?,?,?)");
		
		int i=0; //ignore first Line
		while(bfReader.ready()){
			if(i==0){
				i++;
				bfReader.readLine();
				continue;
			}
			String[] columns = null;
			try{
				 columns = bfReader.readLine().split("\t");
				pstmt.setString(1, columns[0]);
				pstmt.setString(2, columns[1]);
				pstmt.setString(3, (columns[2] != null && columns[2] != "")?columns[2]:"");
				pstmt.setString(4, (columns[3] != null && columns[3] != "")?columns[3]:"");
				pstmt.setString(5, (columns[4] != null && columns[4] != "")?columns[4]:"");
				pstmt.setString(6, (columns[5] != null && columns[5] != "")?columns[5]:"");
				pstmt.setString(7, (columns[6] != null && columns[6] != "")?columns[6]:"");
				pstmt.addBatch();
			}
			catch(java.lang.ArrayIndexOutOfBoundsException exe){
				System.out.println("Ignoring: "+columns);
			}
		}
		
		pstmt.executeBatch();
		stmt.close();
		pstmt.close();
		connection.close();
		
	}
	 private Connection getDBConnection() throws Exception {
		 String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		    String dbName = "SHOPPING_CART";
		    String connectionURL = "jdbc:derby:" + dbName + ";create=true";
		    Class.forName(driver);

		    Connection conn = DriverManager.getConnection(connectionURL);

		return conn;
	}
	static Connection conn;

	  public static void main(String[] args) throws Exception {
		  
		  DerbyDataLoader db = new DerbyDataLoader(); 
		  
		  db.loadCustomersFromFile(DerbyDataLoader.class.getResource("/com/lio/data/Customers.txt").getFile());
	  	 // db.loadProductsFromFile(DerbyDataLoader.class.getResource("/com/lio/data/Products.txt").getFile()) ; 
	  }

	public void loadDataFor(String table) throws Exception {
		if(table.equalsIgnoreCase("CUSTOMERS")){
			loadCustomersFromFile(DerbyDataLoader.class.getResource("/com/lio/data/Customers.txt").getFile());
		}
		else if(table.equalsIgnoreCase("PRODUCTS")){
			loadProductsFromFile(DerbyDataLoader.class.getResource("/com/lio/data/Products.txt").getFile());
		}
	}
}

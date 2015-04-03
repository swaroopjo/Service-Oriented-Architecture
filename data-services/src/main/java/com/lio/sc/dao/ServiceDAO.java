package com.lio.sc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lio.sc.beans.HttpServiceBean;
import com.lio.sc.service.ServiceBusinessFacade;
import com.lio.sc.util.DerbyDataLoader;

public class ServiceDAO extends AbstractDAO{
	
	private static final Logger logger = LoggerFactory
			.getLogger(ServiceDAO.class);
	
	// Check whether Database exists and the Tables have data in them. 
	// THis should not be necessary when the database is present. 
	public ServiceDAO(){
		
		String[] tables = new String[]{"CUSTOMERS","PRODUCTS"};
		for(String table:tables){
			if(!checkIfDBExists(table)){
				try{
					new DerbyDataLoader().loadDataFor(table);
				}
				catch(Exception e){
					logger.error("Error Loading the Data into the Derby");
					e.printStackTrace();
				}
			}
		}
		
	}

	private static final String query = "SELECT * FROM ";
	public JSONObject execute(HttpServiceBean serviceObj){
		logger.info("Calling appropriate service"+serviceObj.getServiceName());
		JSONObject result = new JSONObject();
		
		if(serviceObj.getServiceName().contains("getAll")){
			String dbQuery = query + serviceObj.getTableName();
			Connection connection  = getConnection();
			
			try {
				PreparedStatement pStmt = connection.prepareStatement(dbQuery);
				ResultSet rs = pStmt.executeQuery();
				ResultSetMetaData metaData = rs.getMetaData();
				JSONArray jsonArray = new JSONArray();
				int index = 0;
				while(rs.next()){
					
					JSONObject object = new JSONObject();
					for(int i=1;i<=metaData.getColumnCount();i++){
						object.put(metaData.getColumnName(i),rs.getString(metaData.getColumnName(i)));
						
					}
					jsonArray.put(index, object);
					index++;
				}
				 
				result.put("matches", jsonArray);
				
				pStmt.close();
				rs.close();
				connection.close();
				
			} catch (SQLException e) {
				logger.error("Error Preparing the Data");
				e.printStackTrace();
			}
			
		}
		return result;
	}
}

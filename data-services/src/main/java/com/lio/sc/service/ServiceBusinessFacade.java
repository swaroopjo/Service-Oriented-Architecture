package com.lio.sc.service;

import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lio.sc.beans.HttpServiceBean;
import com.lio.sc.controllers.ControlServlet;
import com.lio.sc.dao.ServiceDAO;

public class ServiceBusinessFacade {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ServiceBusinessFacade.class);
	
	ServiceFactory factory = new ServiceFactory(); 
	

	public JSONObject invokeService(String url,Map<String,String> params)throws Exception{
		logger.info("Check Whether the requested Service Exists ");
		HttpServiceBean service = factory.getService(url);
		// Also check if the User is authorized.
		if(service == null){
			logger.error("Service not found Please check the API for allowed services");
			throw new Exception("Service ("+url+") not found in the factory");
		}
		
		service.setParams(params);
		return new ServiceDAO().execute(service);
		
	}
}

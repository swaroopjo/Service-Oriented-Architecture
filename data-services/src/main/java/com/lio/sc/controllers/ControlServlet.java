package com.lio.sc.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lio.sc.service.ServiceBusinessFacade;
import com.lio.sc.service.ServiceFactory;

/**
 * ControlServlet aces as a front Controller that accepts all the urls that start with /rest. 
 */
public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	private static final Logger logger = LoggerFactory
			.getLogger(ControlServlet.class);
	
	
    /**
     * Default constructor. 
     */
    public ControlServlet() {
    	
    }
    ServiceBusinessFacade facade = new ServiceBusinessFacade(); 
    

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Control Servlet Serving Request. "+request.getSession().getId());
		if(request.getRequestURI().split(request.getServletPath()).length > 2){
			logger.info("Service url invalid");
			return;
		}
		
		PrintWriter out = response.getWriter();
		// We are only going to Process Requests of URL type /rest/*
		String requestedResource = request.getRequestURI().split(request.getServletPath())[1];
		logger.info("Requested Resource: "+requestedResource);
		JSONObject result = null;
			try {
				result = facade.invokeService(requestedResource, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
			
			out.print(result);
	}

	
}

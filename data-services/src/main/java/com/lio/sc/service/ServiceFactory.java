package com.lio.sc.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.lio.sc.beans.HttpServiceBean;

/**
 * Service Factory provides the service object based on the name. 
 * */
public class ServiceFactory {

	private Map<String,HttpServiceBean> serviceMap = new HashMap<String,HttpServiceBean>();
	private Properties urlMappings = new Properties(); 
	public ServiceFactory(){
		try{
			loadPropertiesFromService();
		 InitializeServiceMap();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void loadPropertiesFromService() {
		// TODO Auto-generated method stub
		
	}

	public HttpServiceBean getService(String url){
		//TODO: Make it thread safe
		if(urlMappings.getProperty(url) != null){
			return serviceMap.get(urlMappings.getProperty(url));
		}
		return null;
		
	}
	
	private void InitializeServiceMap() throws ParserConfigurationException, SAXException, IOException {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		
		DefaultHandler handler = new DefaultHandler(){

			HttpServiceBean service = null;
			
			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				if(service == null){
					service = new HttpServiceBean(); 
					
				}
				
				for(int i=0;i<attributes.getLength();i++){
					
					if(attributes.getQName(i).equalsIgnoreCase("name")){
						service.setServiceName(attributes.getValue(i));
					}
					if(attributes.getQName(i).equalsIgnoreCase("table")){
						service.setTableName(attributes.getValue(i));
					}
					if(attributes.getQName(i).equalsIgnoreCase("url")){
						service.setUrl(attributes.getValue(i));
					}
					if(attributes.getQName(i).equalsIgnoreCase("method")){
						service.setMethod(attributes.getValue(i));
					}
					if(attributes.getQName(i).equalsIgnoreCase("return")){
						service.setReturnType(attributes.getValue(i));
					}
					if(service.getServiceName() != null && service.getUrl() != null){
						urlMappings.put(service.getUrl(), service.getServiceName());
					}
				}
			}

			@Override
			public void endElement(String uri, String localName, String qName)
					throws SAXException {
				if(qName.equalsIgnoreCase("service")){
					serviceMap.put(service.getServiceName(), service);
					service = null;
				}
			}

		};
		File servDirectory  = new File(this.getClass().getResource("/com/lio/services").getFile());
		for(File file: servDirectory.listFiles()){
			saxParser.parse(file, handler);
		}
		
	}
	
}

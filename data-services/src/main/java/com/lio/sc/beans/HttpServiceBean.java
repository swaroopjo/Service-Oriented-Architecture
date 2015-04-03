package com.lio.sc.beans;

import java.util.Map;

public class HttpServiceBean {

	private String serviceName; 
	private String tableName; 
	private String url;
	private String method; 
	private String returnType;
	private Map<String,String> params;
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	@Override
	public String toString() {
		return "Service [serviceName=" + serviceName + ", tableName="
				+ tableName + ", url=" + url + ", method=" + method
				+ ", returnType=" + returnType + "]";
	}
	
}

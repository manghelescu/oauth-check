package com.threepillar.servlet.http;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


@SuppressWarnings({"rawtypes" })
public class ExtendedHttpServletRequest extends HttpServletRequestWrapper{

	private Vector<String> extendedHeaderNames = new Vector<String>();
	private HashMap<String,Vector<String>> extendedHeadersMap = new HashMap<String,Vector<String>>(); 
			
	public ExtendedHttpServletRequest(HttpServletRequest httpRequest){
		super(httpRequest);
		
		Enumeration headerNames = httpRequest.getHeaderNames();
		
		while(headerNames.hasMoreElements()){
			String headerName = headerNames.nextElement().toString();
			extendedHeaderNames.add(headerName);
		}
	}
	
	public void addExtendedHeader(String name, String value){
		extendedHeaderNames.add(name);
		Vector<String> values = new Vector<String>();
		values.add(value);
		extendedHeadersMap.put(name, values);
	}
	
	@Override
	public Enumeration getHeaderNames() {
		return extendedHeaderNames.elements();
	}
	
	@Override
	public String getHeader(String name) {
		Vector<String> headerValues = extendedHeadersMap.get(name);
		
		if(headerValues!=null){
			if(headerValues.size()>0){
				return headerValues.get(0);
			}else{
				return null;
			}
		}
		
		return super.getHeader(name);
	}
	
	@Override
	public Enumeration getHeaders(String name) {
		Vector<String> headerValues = extendedHeadersMap.get(name);
		
		if(headerValues!=null){
			return headerValues.elements();
		}
		
		return super.getHeaders(name);
	}
}

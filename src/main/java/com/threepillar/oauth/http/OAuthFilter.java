package com.threepillar.oauth.http;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.threepillar.oauth.api.OAuthApi;
import com.threepillar.servlet.http.ExtendedHttpServletRequest;

public class OAuthFilter implements Filter{

	private OAuthApi oAuthApi;	
	
	public void setOAuthApi(OAuthApi oAuthApi){
		this.oAuthApi = oAuthApi;
	}
	
	@Override
	public void destroy() {		
	}
	
	private String getRequestProperty(HttpServletRequest request, String name){
		String value = request.getHeader(name);
		
		if(value!=null){
			return value;
		}
		
		return request.getParameter(name);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String applicationId = getRequestProperty(httpRequest, "applicationId");
		String token = getRequestProperty(httpRequest, "token");
		String userId = oAuthApi.getUserId(applicationId, token);
		
		if(userId==null){			
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "No way Jose, I can't let you in 'cause I don't know who you are!");			
		}else{						
			ExtendedHttpServletRequest requestWrapper = new ExtendedHttpServletRequest(httpRequest);
			requestWrapper.addExtendedHeader("userId", userId);
			
			filterChain.doFilter(requestWrapper, response);
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:*oauth-filter.xml");
		OAuthApi oAuthApi = context.getBean("oAuthApi", OAuthApi.class);
		this.setOAuthApi(oAuthApi);
	}
}

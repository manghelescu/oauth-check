package com.threepillar.oauth.api;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.threepillar.oauth.OAuthProviderService;
import com.threepillar.oauth.api.OAuthApi;
import com.threepillar.oauth.api.impl.OAuthApiImpl;



public class TestOAuthApi {
	private OAuthApi oAuthApi;
	
	@Before
	public void setup(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:oauth-filter.xml");
		oAuthApi = context.getBean("oAuthApi", OAuthApi.class);		
	}
	
	public OAuthApi getOAuthApi(){
		return oAuthApi;
	}
	
	@Test
	public void test() {
		OAuthApiImpl oAuthApi = (OAuthApiImpl) getOAuthApi();
		
		Set<String> supportedProviders = oAuthApi.getSupportedProviderNames();
		
		for(String providerName : supportedProviders){
			System.out.println("Get OAuth provider service for "+providerName);
			OAuthProviderService service = oAuthApi.getOauthProviderServiceByName(providerName);			
			Assert.assertNotNull(service);
		}
	}	
}

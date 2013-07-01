package com.threepillar.oauth;
import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

import com.threepillar.oauth.handler.DataHandler;


public class OAuthProviderService {
	private OAuthService providerService;
	private OAuthProviderConfig providerConfig;
	private String oauthCallbackUrl;
	private DataHandler dataHandler;
	
	public void setDataHandler(DataHandler dataHandler){
		this.dataHandler = dataHandler;
	}
	
	public OAuthProviderService(OAuthProviderConfig providerConfig, String oauthCallbackUrl){
		this.providerConfig = providerConfig;
		this.oauthCallbackUrl = oauthCallbackUrl;
		
		ServiceBuilder serviceBuilder = new ServiceBuilder();
		serviceBuilder = serviceBuilder.provider(providerConfig.getProviderClass())
				.apiKey(providerConfig.getApplicationKey()).apiSecret(providerConfig.getApplicationSecret())
				.callback(this.oauthCallbackUrl);
		
		if(providerConfig.getScope()!=null){
			serviceBuilder = serviceBuilder.scope(providerConfig.getScope());
		}
		
		providerService = serviceBuilder.build();
	}
	
	public OAuthProviderConfig getConfig(){
		return this.providerConfig;
	}
	
	public String getUserEmail(String token){
		return dataHandler.getUserEmail(token, providerService, providerConfig);
	}

	public String getAuthorizationUrl(){		
		return dataHandler.getAuthorizationUrl(oauthCallbackUrl, providerService, providerConfig);
	}
}

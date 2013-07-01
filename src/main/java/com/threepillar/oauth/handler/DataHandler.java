package com.threepillar.oauth.handler;

import org.scribe.oauth.OAuthService;

import com.threepillar.oauth.OAuthAccess;
import com.threepillar.oauth.OAuthProviderConfig;

public interface DataHandler {

	public String getAuthorizationUrl(String callbackUrl, OAuthService providerService, OAuthProviderConfig providerConfig);
	
	public String getTokenCheckUrl(String token, OAuthService providerService, OAuthProviderConfig providerConfig);
	
	public OAuthAccess checkToken(String token, OAuthService providerService, OAuthProviderConfig providerConfig);
	
	public String getUserEmail(String token, OAuthService providerService, OAuthProviderConfig providerConfig);
	
}

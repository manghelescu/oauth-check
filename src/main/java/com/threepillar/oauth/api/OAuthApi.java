package com.threepillar.oauth.api;

import java.util.Set;


public interface OAuthApi {
	
	public String getUserId(String applicationKey, String token);
	
	public Set<String> getSupportedProviderNames();
	
	public String getAuthorizationUrl(String providerName);
	
}

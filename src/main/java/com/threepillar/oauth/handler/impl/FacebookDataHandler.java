package com.threepillar.oauth.handler.impl;

import java.util.Date;

import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.threepillar.oauth.OAuthAccess;
import com.threepillar.oauth.OAuthProviderConfig;


public class FacebookDataHandler extends DataHandlerAdapter {
	
	@Override
	public String getAuthorizationUrl(String callbackUrl, OAuthService providerService, OAuthProviderConfig providerConfig) {
		return providerService.getAuthorizationUrl(null);
	}	
	
	@Override
	public String getTokenCheckUrl(String token, OAuthService providerService,
			OAuthProviderConfig providerConfig) {
		
		return providerConfig.getCheckTokenBaseUrl()+"?"
				+"&client_id="+providerConfig.getApplicationKey()
				+"&access_token="+token;
	}
	
	@Override
	public OAuthAccess checkToken(String token,
			OAuthService providerService, OAuthProviderConfig providerConfig) {
		
		String tokenCheckUrl = this.getTokenCheckUrl(token, providerService, providerConfig);
		
		OAuthRequest request = new OAuthRequest(Verb.GET, tokenCheckUrl);
		Response response = request.send();
		Date lastCheckDate = new Date();
		
		if(200 != response.getCode()){
			return null;
		}
		
		try {
			JSONObject jsonResponse = new JSONObject(response.getBody());
			
			String error = jsonResponse.getString("error");
			
			if(error!=null){
				return null;
			}
			
			int expirationSeconds = Integer.parseInt(jsonResponse.getString("expires_in"));
			
			OAuthAccess tokenInfo = new OAuthAccess();
			tokenInfo.setToken(token);
			tokenInfo.setExpirationSeconds(expirationSeconds);
			tokenInfo.setLastCheckDate(lastCheckDate);
			
			return tokenInfo;
			
		} catch (Throwable e) {			
			e.printStackTrace();
			return null;
		}
	}
}

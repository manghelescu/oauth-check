package com.threepillar.oauth.handler.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.threepillar.oauth.OAuthAccess;
import com.threepillar.oauth.OAuthProviderConfig;

public class GoogleDataHandler extends DataHandlerAdapter {
	@Override
	public String getUserEmail(String token, OAuthService providerService, OAuthProviderConfig providerConfig) {
		try{
			OAuthRequest request = new OAuthRequest(Verb.GET, providerConfig.getProtectedUrl()+token);
			Response response = request.send();
			
			if(200 != response.getCode()){
				return null;
			}
			
			JSONObject jsonResponse = new JSONObject(response.getBody()); 
			
			return jsonResponse.getString("email");
			
		}catch(Throwable t){
			t.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String getAuthorizationUrl(String callbackUrl, OAuthService providerService, OAuthProviderConfig providerConfig) {
		try {
			return providerConfig.getBaseAuthorizationUrl()
			+ "&client_id="+providerConfig.getApplicationKey()
			+ "&scope=" + URLEncoder.encode(providerConfig.getScope(), "UTF-8")
			+ "&redirect_uri=" + URLEncoder.encode(callbackUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String getTokenCheckUrl(String token, OAuthService providerService,
			OAuthProviderConfig providerConfig) {
		
		return providerConfig.getCheckTokenBaseUrl()+token;
	}
	
	@Override
	public OAuthAccess checkToken(String token, OAuthService providerService, OAuthProviderConfig providerConfig) {
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

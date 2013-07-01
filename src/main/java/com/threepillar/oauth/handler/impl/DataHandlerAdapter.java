package com.threepillar.oauth.handler.impl;

import org.json.JSONObject;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.threepillar.oauth.OAuthProviderConfig;
import com.threepillar.oauth.handler.DataHandler;

public abstract class DataHandlerAdapter implements DataHandler {
	
	@Override
	public String getUserEmail(String token, OAuthService providerService, OAuthProviderConfig providerConfig) {		
		
		try{			
			Verifier verifier = new Verifier(token);		
			Token accessToken = providerService.getAccessToken(OAuthConstants.EMPTY_TOKEN, verifier);
			
			OAuthRequest request = new OAuthRequest(Verb.GET, providerConfig.getProtectedUrl());
			
			providerService.signRequest(accessToken, request);
			Response response = request.send();
			
			System.out.println("Attempting to get user id from "+providerConfig.getProviderName()+" with token ["+token+"]");
			System.out.println("response code "+response.getCode());
			System.out.println("response body ["+response.getBody()+"]");
			
			if( 200 != response.getCode()){
				return null;
			}
			
			JSONObject jsonResponse = new JSONObject(response.getBody());
			return jsonResponse.getString("email");
		}catch(Throwable t){
			//t.printStackTrace();
			return null;
		}
	}	
}

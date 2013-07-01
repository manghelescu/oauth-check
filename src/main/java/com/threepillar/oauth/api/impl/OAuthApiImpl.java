package com.threepillar.oauth.api.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.threepillar.oauth.OAuthAccess;
import com.threepillar.oauth.OAuthAccessCache;
import com.threepillar.oauth.OAuthProviderConfig;
import com.threepillar.oauth.OAuthProviderService;
import com.threepillar.oauth.api.OAuthApi;

public class OAuthApiImpl implements OAuthApi {
	
	private Map<String, OAuthProviderService> oAuthProviderServices;
	private Map<String,String> applicationIdToProvider=new HashMap<String,String>();
	
	private OAuthAccessCache oAuthAccessCache;
	
	public void setOAuthProviderServices(Map<String, OAuthProviderService> oAuthProviderServices){
		this.oAuthProviderServices = oAuthProviderServices;
		
		if(oAuthProviderServices!=null){
			Set<String> providers = oAuthProviderServices.keySet();
						
			for(String providerName :providers){
				OAuthProviderService service = oAuthProviderServices.get(providerName);
				String applicationId = service.getConfig().getApplicationKey();
				applicationIdToProvider.put(applicationId, providerName);
			}
		}
	}
	
	public void setOAuthAccessCache(OAuthAccessCache oAuthAccessCache){
		this.oAuthAccessCache = oAuthAccessCache;
	}
	
	public OAuthProviderConfig getOauthProviderConfigByName(String name){
		OAuthProviderService service = getOauthProviderServiceByName(name);
		
		if(service==null){
			return null;
		}
		
		return service.getConfig();
	}
	
	public OAuthProviderService getOauthProviderServiceByName(String name){
		if(oAuthProviderServices==null){
			return null;
		}
		
		return oAuthProviderServices.get(name);
	}
	
	public String getProviderNameByApplicationId(String applicationId){
		return applicationIdToProvider.get(applicationId);
	}
	
	@Override
	public String getUserId(String applicationId, String token){
		String providerName = getProviderNameByApplicationId(applicationId);
		
		if(providerName==null){
			return null;
		}
		
		OAuthProviderService service = getOauthProviderServiceByName(providerName);
				
		if(service==null){
			return null;
		}
		
		if(oAuthAccessCache.isCached(token)){
			if(!oAuthAccessCache.isExpired(token)){
				System.out.println("token is cached and still valid, returning user id from cache");
				return oAuthAccessCache.getCachedAccess(token).getEmail();
			}else{
				System.out.println("token is cached and invalid, will be cleared");
				oAuthAccessCache.clear(token);
				return null;
			}
		}
		
		System.out.println("token is not cached, needs to be checked with "+service.getConfig().getProviderName());
		String email = service.getUserEmail(token);
		
		if(email==null){
			System.out.println("token is invalid, nu user id is available");
			return null;
		}
		
		OAuthAccess access = new OAuthAccess();
		access.setToken(token);
		access.setEmail(email);
		access.setLastCheckDate(new Date());
		access.setExpirationSeconds(service.getConfig().getExpirationSeconds());
				
		oAuthAccessCache.cache(access);
		System.out.println("token is cached");
		
		return email;
	}
	
	@Override
	public String getAuthorizationUrl(String providerName) {		
		OAuthProviderService service = getOauthProviderServiceByName(providerName);
		
		if(service==null){
			return null;
		}
				
		return service.getAuthorizationUrl();
	}

	@Override
	public Set<String> getSupportedProviderNames() {
		return oAuthProviderServices.keySet();
	}
	
}

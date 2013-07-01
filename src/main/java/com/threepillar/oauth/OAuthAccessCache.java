package com.threepillar.oauth;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OAuthAccessCache {
	private Map<String, OAuthAccess> tokens = new HashMap<String, OAuthAccess>();
	
	public synchronized boolean isCached(String token){
		OAuthAccess access = tokens.get(token);
		
		if(access==null){
			return false;
		}
				
		return true;
	}
	
	public synchronized void cache(OAuthAccess access){
		tokens.put(access.getToken(), access);
	}
	
	public synchronized void clear(String token){
		tokens.remove(token);
	}
	
	public synchronized boolean isExpired(String token){
		if(!isCached(token)){
			return true;
		}
		
		Date currentDate = new Date();		
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentDate);
		
		OAuthAccess accessToken = tokens.get(token);
		int expirationSeconds = accessToken.getExpirationSeconds();
		
		Date lastCheckDate = accessToken.getLastCheckDate();
		Calendar expirationCalendar = Calendar.getInstance();
		expirationCalendar.setTime(lastCheckDate);
		expirationCalendar.add(Calendar.SECOND, expirationSeconds);
				
		return currentCalendar.after(expirationCalendar);		
	}
	
	public synchronized OAuthAccess getCachedAccess(String token){
		return tokens.get(token);
	}
	
	public synchronized Set<String> getTokens(){
		return tokens.keySet();
	}
}

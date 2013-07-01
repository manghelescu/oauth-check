package com.threepillar.oauth;

import java.util.Set;

public class CacheCleaner implements Runnable{
	private OAuthAccessCache cache;
	private long sleepBetweenActions;
	
	public CacheCleaner(OAuthAccessCache cache, long sleepBetweenActions){
		this.cache = cache;
		this.sleepBetweenActions = sleepBetweenActions;
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(sleepBetweenActions);
		} catch (InterruptedException e) {			
			//e.printStackTrace();
		}
		
		try{
			Set<String> tokens = cache.getTokens();
			
			for(String token : tokens){
				if(cache.isExpired(token)){
					cache.clear(token);
				}
			}
		}catch(Throwable t){
			
		}
	}
	
}

package com.threepillar.oauth;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.threepillar.oauth.OAuthAccess;
import com.threepillar.oauth.OAuthAccessCache;

public class TestCache {

	@Test
	public void testCache(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:oauth-filter.xml");
		OAuthAccessCache cache = context.getBean("oAuthAccessCache", OAuthAccessCache.class);
				
		String testToken = "testToken";
		OAuthAccess access = new OAuthAccess();
		access.setEmail("dummy@dummy.com");
		access.setExpirationSeconds(5);
		access.setLastCheckDate(new Date());
		access.setToken(testToken);
		cache.cache(access);
		
		Assert.assertTrue(cache.isCached(testToken));
		Assert.assertFalse(cache.isExpired(testToken));
		Assert.assertNotNull(cache.getCachedAccess(testToken));
		
		try {
			Thread.sleep((1+access.getExpirationSeconds()) * 1000);
			Assert.assertTrue(cache.isExpired(testToken));
		} catch (InterruptedException e) {		
			e.printStackTrace();
		}
	}
}

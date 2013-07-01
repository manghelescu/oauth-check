package com.threepillar.oauth;

import java.util.Date;

public class OAuthAccess {

	private String token;
	private int expirationSeconds;
	private Date lastCheckDate;
	private String email;
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public int getExpirationSeconds() {
		return expirationSeconds;
	}
	
	public void setExpirationSeconds(int expirationSeconds) {
		this.expirationSeconds = expirationSeconds;
	}

	public Date getLastCheckDate() {
		return lastCheckDate;
	}

	public void setLastCheckDate(Date lastCheckDate) {
		this.lastCheckDate = lastCheckDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

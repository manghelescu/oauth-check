package com.threepillar.oauth;
import org.scribe.builder.api.Api;

public class OAuthProviderConfig {
	private String providerName;
	private String applicationKey;
	private String applicationSecret;
	private String protectedUrl;
	private String scope;
	private Class<? extends Api> providerClass;

	private String baseAuthorizationUrl;
	private boolean useRequestToken;
	private boolean useServiceAuthorizationUrlOnly;

	private int expirationSeconds;
	
	private String checkTokenBaseUrl;
	
	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getApplicationKey() {
		return applicationKey;
	}

	public void setApplicationKey(String applicationKey) {
		this.applicationKey = applicationKey;
	}

	public String getApplicationSecret() {
		return applicationSecret;
	}

	public void setApplicationSecret(String applicationSecret) {
		this.applicationSecret = applicationSecret;
	}

	public String getProtectedUrl() {
		return protectedUrl;
	}

	public void setProtectedUrl(String protectedUrl) {
		this.protectedUrl = protectedUrl;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Class<? extends Api> getProviderClass() {
		return providerClass;
	}

	public void setProviderClass(Class<? extends Api> providerClass) {
		this.providerClass = providerClass;
	}

	public String getBaseAuthorizationUrl() {
		return baseAuthorizationUrl;
	}

	public void setBaseAuthorizationUrl(String baseAuthorizationUrl) {
		this.baseAuthorizationUrl = baseAuthorizationUrl;
	}

	public boolean isUseRequestToken() {
		return useRequestToken;
	}

	public void setUseRequestToken(boolean useRequestToken) {
		this.useRequestToken = useRequestToken;
	}

	public boolean isUseServiceAuthorizationUrlOnly() {
		return useServiceAuthorizationUrlOnly;
	}

	public void setUseServiceAuthorizationUrlOnly(
			boolean useServiceAuthorizationUrlOnly) {
		this.useServiceAuthorizationUrlOnly = useServiceAuthorizationUrlOnly;
	}

	public String getCheckTokenBaseUrl() {
		return checkTokenBaseUrl;
	}

	public void setCheckTokenBaseUrl(String checkTokenBaseUrl) {
		this.checkTokenBaseUrl = checkTokenBaseUrl;
	}

	public int getExpirationSeconds() {
		return expirationSeconds;
	}

	public void setExpirationSeconds(int expirationSeconds) {
		this.expirationSeconds = expirationSeconds;
	}

	
}

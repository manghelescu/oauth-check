<!DOCTYPE unspecified PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
	
	</head>
	<body>
		<%
					
			org.springframework.context.ApplicationContext context = new org.springframework.context.support.ClassPathXmlApplicationContext("classpath:*oauth-filter.xml");
			com.threepillar.oauth.api.OAuthApi oAuthApi = context.getBean("oAuthApi", com.threepillar.oauth.api.OAuthApi.class);
			
			java.util.Set<String> suportedProviders = oAuthApi.getSupportedProviderNames();
			
			for(String providerName : suportedProviders){
				out.println("<br/>");
				out.println("<a href=\""+oAuthApi.getAuthorizationUrl(providerName)+"\">Login with "+providerName+"</a>");
			}
		%>
	</body>
</html>
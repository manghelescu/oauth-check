package example;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class GoogleExample2 {
	private static final String NETWORK_NAME = "Google";
	private static final String AUTHORIZE_URL = "https://accounts.google.com/o/oauth2/auth?response_type=token";
	private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
	private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email";
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String apiKey = "81795767834-2g9uvinteg0ikcihdoppaqkhqfq3pk5f.apps.googleusercontent.com";
		String callbackUrl = "http://localhost:8080/oauth-check/callback.jsp";
		
		OAuthService service = new ServiceBuilder()
				.provider(GoogleApi.class)
				.apiKey(apiKey)
				.apiSecret("2LUFnTjc4jGcq53nsT84EYkD")
				.scope(SCOPE)
				.build();
		Scanner in = new Scanner(System.in);

		System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
		System.out.println();

		System.out.println("authorization url from Scribe "+service.getAuthorizationUrl(null));
		
		String authorizationUrl = AUTHORIZE_URL
									+ "&state=tokenObtained"
									+ "&approval_prompt=force"
									+ "&client_id="+apiKey
									+ "&scope=" + URLEncoder.encode(SCOPE, "UTF-8")
									+ "&redirect_uri=" + URLEncoder.encode(callbackUrl, "UTF-8");
		
		System.out.println("Now go and authorize Scribe here:");
		//System.out.println(AUTHORIZE_URL);
		System.out.println(authorizationUrl);
		System.out.println("And paste the verifier here");
		System.out.print(">>");
		Verifier verifier = new Verifier(in.nextLine());
		System.out.println();

		// Trade the Request Token and Verfier for the Access Token
		System.out.println("Trading the Request Token for an Access Token...");
		Token accessToken = service.getAccessToken(null, verifier);
		System.out.println("Got the Access Token!");
		System.out.println("(if your curious it looks like this: "
				+ accessToken + " )");
		System.out.println();

		// Now let's go and ask for a protected resource!
		System.out.println("Now we're going to access a protected resource...");
		OAuthRequest request = new OAuthRequest(Verb.GET,
				PROTECTED_RESOURCE_URL);
		service.signRequest(accessToken, request);
		request.addHeader("GData-Version", "3.0");
		Response response = request.send();
		System.out.println("Got it! Lets see what we found...");
		System.out.println();
		System.out.println(response.getCode());
		System.out.println(response.getBody());

		System.out.println();
		System.out
				.println("Thats it man! Go and build something awesome with Scribe! :)");

	}
}
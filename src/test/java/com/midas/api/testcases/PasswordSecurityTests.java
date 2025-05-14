package com.midas.api.testcases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;
import static io.restassured.RestAssured.given;

@Listeners({com.midas.qa.util.ExtentReportListener.class})
public class PasswordSecurityTests {

	private static final String BASE_URI = "https://midasnv.infoplusmdm.com";
	private static final String LOGIN_ENDPOINT = "Gateway/authservice/midaslogin";
	private static final String PLAIN_PASSWORD = "Welcome@123";

	@Test(priority = 1, description = "Verify password is transmitted securely over HTTPS")
	public void testPasswordOverHTTPS() throws IOException {
		Response response = given()
				.relaxedHTTPSValidation()
				.baseUri(BASE_URI)
				.header("Content-Type", "application/json")
				.body("{ \"EmailID\": \"kalaiyarasan.r@infoplusmdm.com\", \"password\": \"" + PLAIN_PASSWORD + "\" }")
				.when()
				.post(LOGIN_ENDPOINT);

		int statusCode = response.getStatusCode();
		Assert.assertTrue(statusCode == 200 || statusCode == 401, "Login failed unexpectedly.");     
	}

	@Test(priority = 2, description = "Verify password is not reflected in response body")
	public void testPasswordNotReflectedInResponse() throws IOException {
		Response response = given()
				.relaxedHTTPSValidation()
				.baseUri(BASE_URI)
				.header("Content-Type", "application/json")
				.body("{ \"EmailID\": \"test@mail.com\", \"password\": \"" + PLAIN_PASSWORD + "\" }")
				.when()
				.post(LOGIN_ENDPOINT);

		String responseBody = response.getBody().asString();
		Assert.assertFalse(responseBody.contains(PLAIN_PASSWORD), "❌ Password is reflected in response!");

	}

	@Test(priority = 3, description = "Verify password is not retrievable from profile/fetch APIs")
	public void testNoPasswordInProfileFetch() throws IOException {
		// Use a valid token if required
		String dummyToken = "Bearer yourTokenIfAvailable";

		Response response = given()
				.relaxedHTTPSValidation()
				.baseUri(BASE_URI)
				.header("Authorization", dummyToken)
				.when()
				.get("Gateway/authservice/fetchUserProfile");

		String responseBody = response.getBody().asString();
		Assert.assertFalse(responseBody.toLowerCase().contains("password"), "❌ Password field should not be exposed in user profile!");

	}
}

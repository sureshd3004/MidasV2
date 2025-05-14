package com.midas.api.testcases;

import com.midas.qa.util.FileUtil;
import io.restassured.response.Response;
import java.io.IOException;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners({com.midas.qa.util.ExtentReportListener.class})
public class OAuthAPITest {

	public static final String API_URI = "https://midasnv.infoplusmdm.com:8443/";

	@Test(dependsOnMethods = "testLogInEndPoint",description = "return token for access & refreshToken")
	public void testAccessTokenEndPoint() {

		String API_END_POINT = "authservice/refreshtoken";
		
		String accessToken = FileUtil.getValueFromJsonFile("accessToken");
		String refreshToken = FileUtil.getValueFromJsonFile("refreshToken");   

		String requestBody = "{\n" +
				"   \"AccessToken\": \"" + accessToken + "\",\n" +
				"   \"RefreshToken\": \"" + refreshToken + "\"\n" +
				"}";

		Response response = com.midas.qa.util.APIRequestUtil.sendPost(API_URI + API_END_POINT, requestBody );
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8");
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
		FileUtil.saveJsonToFile(response.asString());
	
	}

	@Test(description = "gives back access token & refresh token for the UserName & Password")
	public void testLogInEndPoint() {

		String requestBody = "{  \r\n"
				+ "    \"password\": \"Welcome@123\",  \r\n"
				+ "    \"EmailID\": \"kalaiyarasan.r@infoplusmdm.com\",\r\n"
				+ "    \"loginType\": \"\"  \r\n"
				+ "}";

		String API_END_POINT = "authservice/midaslogin";

		Response response = com.midas.qa.util.APIRequestUtil.sendPost(API_URI + API_END_POINT, requestBody);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
		Assert.assertEquals(response.jsonPath().getString("responseMessage"), "Token Generated");  
		FileUtil.saveJsonToFile(response.asString());
	}

	@Test(description = "SQL Injections")
	public void testLogInEndPointSQLInjection() throws IOException {

		List<String> list = FileUtil.readAllLinesFrom("SQL_Injections.txt");

		for (String injection : list) {
			String requestBody = "{\n" +
					"    \"password\": \"Welcome@123\",\n" +
					"    \"EmailID\": \"" + injection + "\",\n" +
					"    \"loginType\": \"\"\n" +
					"}";
			String API_END_POINT = "Gateway/authservice/midaslogin";

			Response response = com.midas.qa.util.APIRequestUtil.sendPost(API_URI + API_END_POINT, requestBody);

			FileUtil.logDataInTxt(injection, response, "SQL_Results.txt");
			Assert.assertNotEquals(response.getStatusCode(), 500, "SQL Injection caused Server Error!");
			Assert.assertFalse(response.asString().toLowerCase().contains("sql"), "Potential SQL error leaked in response!");
			Assert.assertFalse(response.asString().toLowerCase().contains("syntax"), "Syntax error leaked in response!");

		}}

	@Test(description = "XSS Injection Testing on Login Endpoint")
	public void testLoginEndPointXSSInjection() throws IOException {

		List<String> xssPayloads = FileUtil.readAllLinesFrom("XSS_payloads.txt");

		for (String payload : xssPayloads) {

			String requestBody = "{\n" +
					"    \"password\": \"Welcome@123\",\n" +
					"    \"EmailID\": \"" + payload + "\",\n" +
					"    \"loginType\": \"\"\n" +
					"}";

			String API_END_POINT = "Gateway/authservice/midaslogin";

			Response response = com.midas.qa.util.APIRequestUtil.sendPost(API_URI + API_END_POINT, requestBody);
			FileUtil.logDataInTxt(payload, response, "XSS_Results.txt");
			// Assert no XSS reflected
			Assert.assertFalse(response.getBody().asString().contains(payload), "Potential XSS vulnerability detected!");
		}
	}


	@Test(description = "SSO Login EndPoint",enabled = false)
	public void testSSOLogInEndPoint() {

		String requestBody = "{  \r\n"
				+ "    \"idToken\": \"string\",  \r\n"
				+ "    \"accessToken\": \"string\",  \r\n"
				+ "    \"refreshToken\": \"string\",\r\n"       	
				+ "}";

		String API_END_POINT = "api/auth/ssologin";

		Response response = com.midas.qa.util.APIRequestUtil.sendPost(API_URI + API_END_POINT, requestBody);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getContentType(), "application/json");
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	//  System.out.println(response.getBody().asString());

	}
	@Test(description = "GET request to give the user list")
	public void testGetAllUserList() {

		String API_END_POINT = "authservice/userlist";
        String key = FileUtil.getValueFromJsonFile("accessToken");
		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_END_POINT,key); 
		System.err.println(response.toString());
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");     
	}
	@Test(description = "GET request to give the user type list")
	public void testGetUserTypeList() {

		String API_END_POINT = "authservice/usertypelist";
        String key = FileUtil.getValueFromJsonFile("accessToken");
		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_END_POINT,key); 
		System.err.println(response.toString());
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");     
	}
}

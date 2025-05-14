package com.midas.api.testcases;

import com.midas.qa.util.FileUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({com.midas.qa.util.ExtentReportListener.class})
public class VendorRequestAPITest {

	private static final String API_URI = "http://192.168.1.238/";

	@Test(description = "Request Creation Form submission")
	public void testPOSTRequestCreation() {

		String API_END_POINT = "vendorservice/request/create_vendor_request";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		String payload = "{\r\n"
				+ "  \"vendorRequestCreationID\": 30,\r\n"
				+ "  \"vendorName\": \"InfopsMD\",\r\n"
				+ "  \"vendorEmail\": \"kal@infoplusm.com\",\r\n"
				+ "  \"countryCodeID\": 1,\r\n"
				+ "  \"mobileNumber\": \"9094912620\",\r\n"
				+ "  \"requestTypeID\": 1,\r\n"
				+ "  \"vendorTypeID\": 1,\r\n"
				+ "  \"locationID\": 1,\r\n"
				+ "  \"vendorSearchTerm\": \"info\",\r\n"
				+ "  \"isSimilarProducts\": true,\r\n"
				+ "  \"estimatedOrderPerYear\": 10,\r\n"
				+ "  \"estimatedSpendForYear\": 11,\r\n"
				+ "  \"vendorCompanyCodeExtentionID\": 1,\r\n"
				+ "  \"financeForwardingEmail\": \"kalaimca26@gmail.com\",\r\n"
				+ "  \"requestReason\": \"Testing\",\r\n"
				+ "  \"createdUser\": \"Kalaiyarasan Ramanathan\",\r\n"
				+ "  \"createdUserKey\": \"1\"\r\n"
				+ "}";
		Response response = com.midas.qa.util.APIRequestUtil.sendPost(API_URI + API_END_POINT, access ,payload);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
	@Test(description = "verify vendor email")
	public void testPOSTVerifyEmail() {

		String API_END_POINT = "Gateway/vendorservice/request/update_email_verification";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		String payload="27294533-EFBB-40EE-A27E-E9FA1A7B6119";

		Response response = com.midas.qa.util.APIRequestUtil.sendPost(API_URI + API_END_POINT, access ,payload);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
	@Test(description = "verify vendor")
	public void testGETVendor() {

		String API_END_POINT = "Gateway/vendorservice/request/get_vendor_request?VendorRequestCreationID=2";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_URI + API_END_POINT, access);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
	@Test(description = "search vendor List")
	public void testGETVendorList() {

		String API_END_POINT = "Gateway/vendorservice/request/get_vendor_requestlist?SearchText=haris";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_URI + API_END_POINT, access);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
	@Test(description = "GET Request ID byToken")
	public void testGETRequestIDbyToken() {

		String API_END_POINT = "Gateway/vendorservice/request/get_vendor_requestid?EmailVerificationToken=27294533-EFBB-40EE-A27E-E9FA1A7B6119&URLToken=719EB172-609B-4DFE-A9DC-149F02C83C1C";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_URI + API_END_POINT, access);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
	@Test(description = "GET Country Code")
	public void testGETCountryCode() {
		String API_END_POINT = "Gateway/vendorservice/request/countrycodes";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_URI + API_END_POINT, access);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
	@Test(description = "POST Geneate Email Token")
	public void testPOSTGeneateEmailToken() {
		String API_END_POINT = "Gateway/vendorservice/request/generate_email_verification_token";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		String payload="{\r\n"
				+ "  \"vendorRequestCreationID\": 2,\r\n"
				+ "  \"UpdateType\":\"Re-Trigger\"\r\n"
				+ "}";

		Response response = com.midas.qa.util.APIRequestUtil.sendPost(API_URI + API_END_POINT, access ,payload);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
	@Test(description = "POST Geneate URL Token")
	public void testPOSTGeneateURLToken() {
		String API_END_POINT = "Gateway/vendorservice/request/generate_url_access_token";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		String payload="{\r\n"
				+ "  \"vendorRequestCreationID\": 2,\r\n"
				+ "  \"UpdateType\":\"Re-Trigger\"\r\n"
				+ "}";

		Response response = com.midas.qa.util.APIRequestUtil.sendPost(API_URI + API_END_POINT, access ,payload);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
	@Test(description = "GET Vendor Trash List")
	public void testGETVendorTrashList() {
		String API_END_POINT = "Gateway/vendorservice/request/get_vendor_removedlist";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_URI + API_END_POINT, access);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
	@Test(description = "DELETE Request temporary")
	public void testDELETERequestTemporary() {
		String API_END_POINT = "vendor/api/request/remove_vendor_request?VendorRequestCreationID=2&DeletionType=temporary";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendDelete(API_URI + API_END_POINT, access);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
	@Test(description = "DELETE Request Temp")
	public void testDELETERequestPermanent() {
				
		int VendorRequestCreationID = 1;
		String API_END_POINT = "vendor/api/request/remove_vendor_request?VendorRequestCreationID="+VendorRequestCreationID +"&DeletionType=Permanent";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendDelete(API_URI + API_END_POINT, access);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
	@Test(description = "GET Company List")
	public void testGETConpanyList() {
		String API_END_POINT = "Gateway/vendorservice/request/get_vendor_company_list";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_URI + API_END_POINT, access);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
	@Test(description = "Activate Removed Request")
	public void testPOSTActivateRemovedRequest() {
		String API_END_POINT = "Gateway/vendorservice/request/activate_removed_request";	
		String access = FileUtil.getValueFromJsonFile("accessToken");

		String payload = "{\r\n"
				+ "    \"VendorRequestCreationID\":10\r\n"
				+ "}"; 

		Response response = com.midas.qa.util.APIRequestUtil.sendPost(API_URI + API_END_POINT, access ,payload);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	
}
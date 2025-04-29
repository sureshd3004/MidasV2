package com.midas.api.testcases;

import com.midas.qa.util.FileUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({com.midas.qa.util.ExtentReportListener.class})
public class VendorServiceAPITest {

	private static final String API_URI = "http://192.168.1.238/";

	@Test(description = "return field types")
	public void testGETFieldTypes() {

		String API_END_POINT = "Gateway/vendorservice/field/type";	
		String access =  FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_URI + API_END_POINT, access );
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}

	@Test(description = "gives back FieldCategories")
	public void testGETFieldCategories() {
		String API_END_POINT = "Gateway/vendorservice/field/category";
		String access =  FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_URI + API_END_POINT, access );
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}

	@Test(description = "gives back VendorTypes")
	public void testGETVendorTypes() {
		String API_END_POINT = "Gateway/vendorservice/field/vendortype";
		String access =  FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_URI + API_END_POINT, access );
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}

	@Test(description = "gives back FieldList")
	public void testGETFieldList() {
		String API_END_POINT = "Gateway/vendorservice/field/fieldlist";
		String access =  FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_URI + API_END_POINT, access );
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	@Test(description = "gives back FieldNames")
	public void testGETFieldNames() {
		String API_END_POINT = "Gateway/vendorservice/field/fieldnames";
		String access =  FileUtil.getValueFromJsonFile("accessToken");

		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_URI + API_END_POINT, access );
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
	@Test(description = "gives back Default Fields")
	public void testGETDefaultFields() {
		String API_END_POINT = "Gateway/vendorservice/field/default";
		String access =  FileUtil.getValueFromJsonFile("accessToken");
		Response response = com.midas.qa.util.APIRequestUtil.sendGet(API_URI + API_END_POINT, access );
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2000ms");
	}
}

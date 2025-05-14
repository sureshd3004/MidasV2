package com.midas.requestor.testcases;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.midas.qa.base.TestBase;
import com.midas.requestor.pages.VendorRequestPageRequestor;
import com.midas.requestor.pages.LoginPageRequestor;


public class RequestorLoginPageTest extends TestBase{
	
	LoginPageRequestor loginPageRequestor;
	VendorRequestPageRequestor homePage;
	SoftAssert softAssert;
	
	public RequestorLoginPageTest(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		initialization();
		loginPageRequestor = new LoginPageRequestor();	
		softAssert = new SoftAssert();
	}
	
	@Test(priority=1)
	public void loginPageTitleTest(){
		String title = loginPageRequestor.validateLoginPageTitle();
		Assert.assertEquals(title, "Sign-in");
	}
	
	@Test()
	public void LogosTest(){
		boolean midaslogo = loginPageRequestor.validateMidasLogo();
		softAssert.assertTrue(midaslogo);	
		
		boolean clientlogo = loginPageRequestor.validateClientLogo();
		softAssert.assertTrue(clientlogo);	
		
		softAssert.assertTrue(loginPageRequestor.validateInfoplusLogo());
		softAssert.assertAll();
	}
	@Test()
	public void TextContentTest() {
		softAssert.assertEquals(true, loginPageRequestor.verifyMidasContent().length()>200);
		softAssert.assertEquals(true, loginPageRequestor.verifyClientContent().length()>200);
		softAssert.assertAll();
	}

	@Test()
 	public void loginRequestorTestWithSSO() throws InterruptedException {  	
		loginPageRequestor.requestorSSOLogin( prop.getProperty("username"), prop.getProperty("password"));		
 		assertEquals(loginPageRequestor.verifyNotificationText("Successfully"), "Login Successfully");
 	}
	
	@Test(enabled = false)
 	public void RequestorSSOloginTest() throws InterruptedException {  	
		loginPageRequestor.requestorSSOLogin(prop.getProperty("username"), "wrong password");  
		assertEquals(false, null);
	}
	
}

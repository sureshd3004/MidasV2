package com.midas.requestor.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.midas.qa.base.TestBase;
import com.midas.qa.util.Log;
import com.midas.qa.util.TestUtil;
import com.midas.requestor.pages.HomePageRequestor;
import com.midas.requestor.pages.LoginPageRequestor;

public class RequestorHomePageTest extends TestBase {
	LoginPageRequestor loginPage;
	HomePageRequestor homePage;
	TestUtil testUtil;

	public RequestorHomePageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
		testUtil = new TestUtil();
		loginPage = new LoginPageRequestor();
		homePage = loginPage.userLogin(prop.getProperty("username"), prop.getProperty("password"));
		Log.info("Test Starts");
	}	
	
	@Test()
	public void verifyHomePageTitleTest(){		
		String homePageTitle = homePage.verifyHomePageTitle();
		Assert.assertEquals(homePageTitle, "ssruat.infoplusmdm","Home page title not matched");		
	}
	
	@Test()
	public void verifyuserName(){	
		Assert.assertTrue(homePage.validateUserName());		
	}
	

}

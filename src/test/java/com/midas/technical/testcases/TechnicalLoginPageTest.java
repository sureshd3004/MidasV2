package com.midas.technical.testcases;

import static org.testng.Assert.assertEquals;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.midas.qa.base.TestBase;
import com.midas.requestor.pages.HomePageRequestor;
import com.midas.requestor.pages.LoginPageRequestor;


public class TechnicalLoginPageTest extends TestBase{
	
	LoginPageRequestor loginPage;
	HomePageRequestor homePage;
	
	public TechnicalLoginPageTest(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		initialization();
		loginPage = new LoginPageRequestor();	
	}
	
	@Test(priority=1,enabled = false)
	public void loginPageTitleTest(){
		String title = loginPage.validateLoginPageTitle();
		Assert.assertEquals(title, "HomePageRequestor");
	}
	
	@Test(priority=2,enabled = true)
	public void LogoImageTest(){
		boolean logo = loginPage.validateImage();
		Assert.assertTrue(logo);		
	}
	
    @Test(enabled = true)
	public void loginFunctionTestWithUserID(){
		String ScessMesg = loginPage.useridlogin(prop.getProperty("username"), prop.getProperty("password"));	
		assertEquals(ScessMesg, "Login Successfully");
	}
    
    @Test(enabled = true)
 	public void login(){
 		homePage = loginPage.userLogin(prop.getProperty("username"), prop.getProperty("password"));	
 	}

}

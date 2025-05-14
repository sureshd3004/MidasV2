package com.midas.technical.testcases;

import static org.testng.Assert.assertEquals;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.midas.qa.base.TestBase;
import com.midas.technical.pages.VendorRequestTechnical;
import com.midas.technical.pages.LoginPageTechnical;

public class TechnicalLoginPageTest extends TestBase{

	LoginPageTechnical loginPageTechnical;
	VendorRequestTechnical homePageTechnical;

	public TechnicalLoginPageTest(){
		super();
	}

	@BeforeMethod
	public void setUp(){
		initialization();
		loginPageTechnical = new LoginPageTechnical();	
	}

	@Test()
	public void loginTechnicalTestWithUserID(){  
		loginPageTechnical.technicalUserEmaillogin(prop.getProperty("username"), prop.getProperty("password"));			
		assertEquals(loginPageTechnical.verifyNotificationText("Successfully"), "Login Successfully");
	}

	@Test()
	public void loginTechnicalTestWithInvalidUserID(){  						
		loginPageTechnical.technicalUserEmaillogin("kalaiyarasan.r@in.com", prop.getProperty("password"));
		Assert.assertEquals(loginPageTechnical.verifyNotificationText("Invalid"),"Invalid user");
	}

	@Test()
	public void loginTechnicalTestWithInvalidpassword() {  		
		loginPageTechnical.technicalUserEmaillogin(prop.getProperty("username"), "invalid password");
		Assert.assertEquals(loginPageTechnical.verifyNotificationText("Invalid"),"Invalid user");
	}

}

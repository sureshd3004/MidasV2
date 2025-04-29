package com.midas.requestor.testcases;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.midas.qa.base.TestBase;
import com.midas.qa.util.FileUtil;
import com.midas.requestor.pages.HomePageRequestor;
import com.midas.requestor.pages.LoginPageRequestor;


public class RequestorLoginPageTest extends TestBase{
	
	LoginPageRequestor loginPage;
	HomePageRequestor homePage;
	
	public RequestorLoginPageTest(){
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
	
	@Test(dataProvider = "getloginData")
	public void loginFunctionTestWithUserID(String userName,String password){  
		String ScessMesg = loginPage.useridlogin(userName, password);  			
		assertEquals(ScessMesg, "Login Successfully");
	}
    
	@Test(dataProvider = "getloginData")
 	public void login(String userName,String password) {  	
 		homePage = loginPage.userLogin(userName, password);  	
 	}

	@DataProvider(name = "getloginData")
	public Object[][] getloginData() throws IOException{
		String sheetName = "login";		
		String role = prop.getProperty("User");		
		return FileUtil.getTestDataBasedColoumn(sheetName, role);
	}
}

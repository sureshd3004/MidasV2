package com.midas.vendor.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.midas.qa.base.TestBase;

public class RegistrationFormVendor extends TestBase{
	  private WebDriver driver;
	  
		//Initializing the Page Objects:
		public RegistrationFormVendor(){
			 this.driver = getDriver(); 
			PageFactory.initElements(driver, this);
		}
		
		
		
		
}


package com.midas.technical.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.midas.qa.base.TestBase;

public class VendorRequestTechnical extends TestBase {


	@FindBy(xpath = "//img[@id='btnSearch']")
	WebElement searchLink;

	@FindBy(xpath = "//a[contains(text(),'New Contact')]")
	WebElement newContactLink;

	@FindBy(xpath = "//a[contains(text(),'Deals')]")
	WebElement dealsLink;

	@FindBy(xpath = "//a[contains(text(),'Tasks')]")
	WebElement tasksLink;

	@FindBy(xpath="//b[@id='spnUserName']")
	WebElement userName;

	@FindBy(xpath="//img[@id='btnNew']")
	WebElement singleRequestIcon;

	private WebDriver driver;

	// Initializing the Page Objects:
	public VendorRequestTechnical() {
		this.driver = getDriver();
		PageFactory.initElements(driver, this);
	}

	public String verifyHomePageTitle(){
		return driver.getTitle();
	}

	public boolean validateUserName(){
		return userName.getText().equalsIgnoreCase(prop.getProperty("username"));
	}


}

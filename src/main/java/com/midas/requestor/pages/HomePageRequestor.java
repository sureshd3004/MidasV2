package com.midas.requestor.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.midas.qa.base.TestBase;

public class HomePageRequestor extends TestBase {


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

	@FindBy(xpath="//svg-icon[@src=\"assets/icons/midas_logo.svg\"]")
	WebElement Logo;

	private WebDriver driver;

	public boolean validateImage(){
		return Logo.isDisplayed();
	}
	// Initializing the Page Objects:
	public HomePageRequestor() {
		this.driver = getDriver();
		PageFactory.initElements(driver, this);
	}

	public String verifyHomePageTitle(){
		return driver.getTitle();
	}

	public boolean validateUserName() {
		return userName.getText().equalsIgnoreCase(prop.getProperty("role"));
	}


}

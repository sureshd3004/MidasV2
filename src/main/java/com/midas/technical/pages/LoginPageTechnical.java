package com.midas.technical.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.midas.qa.base.TestBase;

public class LoginPageTechnical extends TestBase{

	//Page Factory - OR:
	@FindBy(xpath="//input[@id='username']")
	WebElement username;	

	@FindBy(xpath="//input[@id='password']")
	WebElement password;

	@FindBy(xpath="//button[text()=\"Sign in\"]")
	WebElement loginBtn;

	@FindBy(xpath = "//label[@for=\"remember-me\"]")
	WebElement rememberMe;

	@FindBy(xpath="//button[text()=\" Forgot your password? \"]")
	WebElement forgotPassword;

	@FindBy(xpath="//p-tab[text()=\"User\"]")
	WebElement userLoginOption;
	
	@FindBy(xpath="//p-tab[text()=\"Technical\"]")
	WebElement technicalLoginOption;

	@FindBy(xpath="//img[@alt='Client Logo']")
	WebElement Logo;

	@FindBy(xpath="//img[@id='btnNew']")
	WebElement requestIcon;
	
	@FindBy(xpath = "//*[text()='Login Successfully']")
	WebElement loginSucessMesg;

	  private WebDriver driver;
	  
	//Initializing the Page Objects:
	public LoginPageTechnical(){
		 this.driver = getDriver(); 
		PageFactory.initElements(driver, this);
	}

	//Actions:
	public String validateLoginPageTitle(){
		return driver.getTitle();
	}

	public boolean validateImage(){
		return Logo.isDisplayed();
	}

	public HomePageTechnical getTechnicalHomePage(String un, String pwd){
		username.sendKeys(un);
		password.clear();
		password.sendKeys(pwd);
		loginBtn.click();
   //   return loginSucessMesg.getText();
	
		return new HomePageTechnical();
	}

	public String technicalUseridlogin(String un, String pwd) {
		username.sendKeys(un);
		password.clear();
		password.sendKeys(pwd);
		loginBtn.click();
        return loginSucessMesg.getText();
	}




}

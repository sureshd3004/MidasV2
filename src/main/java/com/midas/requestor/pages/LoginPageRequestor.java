package com.midas.requestor.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.midas.qa.base.TestBase;
import com.midas.qa.util.TestUtil;

public class LoginPageRequestor extends TestBase{

	//Page Factory - OR:
	@FindBy(xpath="//input[@id='email']")
	WebElement username;	

	@FindBy(xpath="//input[@name='password']")
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

	@FindBy(xpath="//*[@src=\"assets/icons/midas_logo.svg\"]")
	WebElement MidasLogo;

	@FindBy(xpath="//*[name()='rect' and contains(@width,'168')]")
	WebElement infoplusLogo;
	
	@FindBy(xpath="//img[@alt='SSR Logo']")
	WebElement clientLogo;
	
	@FindBy(xpath="//img[@id='btnNew']")
	WebElement requestIcon;
	
	@FindBy(xpath = "//*[text()='Login Successfully']")
	WebElement loginSucessMesg;
	
	@FindBy(xpath = "//*[text()='Invalid user']")
	WebElement loginFailMesg;
	
	@FindBy(xpath="//button[contains(@class,'font-semibold focus-visible:outline-none flex items')]")
	WebElement SSOIcon;
	
	@FindBy(xpath="//input[@id='i0116']")
	WebElement SSOEmail;
	
	@FindBy(xpath="//input[@id='idSIButton9']")
	WebElement SSONext;

	@FindBy(xpath="//input[@id='i0118' or type=\"password\" or name=\"passwd\"] ")
	WebElement SSOPassword;
	
	@FindBy(xpath="//input[@id=\"idSIButton9\" or type=\"submit\"]")
	WebElement yesConfirm;
	
	@FindBy(xpath="//button[@type='submit']")
	WebElement SSOPassSudmit;
	
	@FindBy(xpath="//button[@id=\"close-button\"]")
	WebElement yesStayLogedIn;
	
	@FindBy(xpath="//div[@class='relative w-3/5 bg-cover bg-center auth-bg p-6 flex flex-col justify-between rounded-lg']/div[2]/p")
	WebElement clientContent;
	
	@FindBy(xpath="//p[@class='text-xs']")
	WebElement infoplusCOntent;
	
	  private WebDriver driver;
	  
	//Initializing the Page Objects:
	public LoginPageRequestor(){
		 this.driver = getDriver(); 
		PageFactory.initElements(driver, this);
	}

	//Actions:
	public String validateLoginPageTitle(){
		return driver.getTitle();
	}

	public boolean validateMidasLogo(){
		return MidasLogo.isDisplayed();
	}
	public boolean validateClientLogo(){
		return clientLogo.isDisplayed();
	}
	public void requestorSSOLogin(String email, String pwd) {
		
		userLoginOption.click();
		SSOIcon.click();
		TestUtil.switchNewWindow();
		SSOEmail.sendKeys(email);
		SSOEmail.sendKeys(Keys.ENTER);
		TestUtil.waitAndSendkeys(SSOPassword, pwd); 
		SSOPassword.sendKeys(Keys.ENTER);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TestUtil.waitAndClickElement(yesConfirm);
		TestUtil.switchToMainWindow();
	}

	public boolean validateInfoplusLogo() {		
		return infoplusLogo.isDisplayed();
	}
	
	public String verifyNotificationText(String text) {
		return TestUtil.waitAndGetText(driver.findElement(By.xpath("//*[contains(text(),'"+text+"')]")));	
	}

	public String verifyClientContent() {		
		return TestUtil.waitAndGetText(clientContent);
	}

	public String verifyMidasContent() {		
		return TestUtil.waitAndGetText(infoplusCOntent);
	}
   

}

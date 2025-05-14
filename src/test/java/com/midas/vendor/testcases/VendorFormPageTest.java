package com.midas.vendor.testcases;

import java.io.IOException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.midas.qa.base.TestBase;
import com.midas.qa.util.FileUtil;
import com.midas.qa.util.MailUtils;
import com.midas.requestor.pages.VendorRequestPageRequestor;
import com.midas.requestor.pages.LoginPageRequestor;

public class VendorFormPageTest extends TestBase {
	LoginPageRequestor loginPage;
	VendorRequestPageRequestor homePage;

	public VendorFormPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
	}	
	
	@Test()
	public void verifyVendorEmail() throws IOException, InterruptedException{	  	
		String url = MailUtils.extractUrlFromEmail(prop.getProperty("mail"), "Vendor Request Email");
		getDriver().get(url);
		System.out.println(url);
		boolean result = getDriver().findElement(By.tagName("body")).getText().contains("Welcome");
		Assert.assertTrue(result);
	}

	@Test()
	public void OpenVendorFormURL() throws IOException, InterruptedException{	  	
		String url = MailUtils.extractUrlFromEmail(prop.getProperty("mail"), "Vendor Registration Email");
		System.out.println(url);
		if (url == null) {
			System.out.println("got null");
		FileUtil.writeDataToProperties(prop.getProperty("vendorForm"), url);
	}
		getDriver().get(url);
		Assert.assertEquals(getDriver().getTitle(), "Vendor Form");
	}
}

package com.midas.technical.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.midas.qa.base.TestBase;
import com.midas.qa.util.Log;
import com.midas.qa.util.TestUtil;
import com.midas.requestor.pages.VendorRequestPageRequestor;
import com.midas.technical.pages.LoginPageTechnical;

public class TechnicalVendorRequestTest extends TestBase {
	
	LoginPageTechnical loginPageTechnical;
	VendorRequestPageRequestor  TechnicalVendorRequest;
	TestUtil testUtil;
    SoftAssert sassert;
	
	public TechnicalVendorRequestTest() {
		super();
	}



}
